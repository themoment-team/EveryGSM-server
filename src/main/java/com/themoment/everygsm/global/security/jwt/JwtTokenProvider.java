package com.themoment.everygsm.global.security.jwt;

import com.themoment.everygsm.global.exception.CustomException;
import com.themoment.everygsm.global.security.auth.AuthDetailsService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.ZonedDateTime;
import java.util.Date;

@Getter
@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    @Value("${jwt.accessSecret}")
    private String accessSecret;

    @Value("${jwt.refreshSecret}")
    private String refreshSecret;

    private final long ACCESS_TOKEN_EXPIRE_TIME = 60 * 120 * 1000;
    private final long REFRESH_TOKEN_EXPIRE_TIME = ACCESS_TOKEN_EXPIRE_TIME * 12;
    private final AuthDetailsService authDetailsService;

    @AllArgsConstructor
    public enum TokenType {
        ACCESS_TOKEN("accessToken"),
        REFRESH_TOKEN("refreshToken");
        String value;
    }

    public String getTokenType(String token, String secret) {
        Claims claims = extractAllClaims(token, secret);
        if (claims != null) {
            return claims.get(TokenClaimName.TOKEN_TYPE.value, String.class);
        } else {
            throw new CustomException("토큰이 유효하지 않습니다.", HttpStatus.BAD_REQUEST);
        }
    }

    @AllArgsConstructor
    private enum TokenClaimName {
        USER_EMAIL("email"),
        TOKEN_TYPE("tokenType");
        String value;
    }

    public Key getSignKey(String secretKey) {
        byte[] bytes = secretKey.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(bytes);
    }

    private String generateToken(String userEmail, TokenType tokenType, String secret, long expiredTime) {
        final Claims claims = Jwts.claims();
        claims.put(TokenClaimName.USER_EMAIL.value, userEmail);
        claims.put(TokenClaimName.TOKEN_TYPE.value, tokenType);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+expiredTime))
                .signWith(getSignKey(secret), SignatureAlgorithm.HS256)
                .compact();
    }

    public String generatedAccessToken(String userId) {
        return generateToken(userId, TokenType.ACCESS_TOKEN, accessSecret, ACCESS_TOKEN_EXPIRE_TIME);
    }

    public String generatedRefreshToken(String userId) {
        return generateToken(userId, TokenType.REFRESH_TOKEN, refreshSecret, REFRESH_TOKEN_EXPIRE_TIME);
    }

    private Claims getTokenBody(String token, String secret) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(getSignKey(secret))
                    .build()
                    .parseClaimsJwt(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            throw new CustomException("토큰이 만료되었습니다.", HttpStatus.UNAUTHORIZED);
        } catch (JwtException e) {
            throw new CustomException("토큰이 유효하지 않습니다.", HttpStatus.UNAUTHORIZED);
        }
    }

    public ZonedDateTime getExpiredAtToken() {
        return ZonedDateTime.now().plusSeconds(ACCESS_TOKEN_EXPIRE_TIME);
    }

    public long getExpiredAtoTokenToLong() {
        return ACCESS_TOKEN_EXPIRE_TIME/1000L;
    }

    public boolean isExpiredToken(String token, String secret) {
        final Date expiration = getTokenBody(token, secret).getExpiration();
        return expiration.before(new Date());
    }

    public boolean isValidToken(String token, String secret) {
        return isExpiredToken(token, secret);
    }

    public String getUserEmail(String token, String secret) {
        return extractAllClaims(token, secret).get(TokenClaimName.USER_EMAIL.value, String.class);
    }

    public Claims extractAllClaims(String token, String secret) {
        token = token.replace("Bearer ", "");
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(getSignKey(secret))
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            throw new CustomException("만료된 토큰 입니다.", HttpStatus.BAD_REQUEST);
        } catch (JwtException e) {
            throw new CustomException("토큰이 유효하지 않습니다.", HttpStatus.BAD_REQUEST);
        }
    }

    public UsernamePasswordAuthenticationToken authenticationToken(String email) {
        UserDetails userDetails = authDetailsService.loadUserByUsername(email);
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}
