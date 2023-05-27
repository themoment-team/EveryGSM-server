package com.themoment.everygsm.global.filter;

import com.themoment.everygsm.global.exception.CustomException;
import com.themoment.everygsm.global.security.jwt.JwtTokenProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;

    @Value("${jwt.accessSecret}")
    private String accessSecret;

    @Value("${jwt.refreshSecret}")
    private String refreshSecret;

    public void registerSecurityContext(HttpServletRequest request, String email) {
        UsernamePasswordAuthenticationToken authenticationToken = jwtTokenProvider.authenticationToken(email);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String accessToken = request.getHeader("Authorization");

        if(!Objects.isNull(accessToken)) {
            jwtTokenProvider.extractAllClaims(accessToken, accessSecret);
            if(!jwtTokenProvider.getTokenType(accessToken, accessSecret).equals("ACCESS_TOKEN")) {
                throw new CustomException("토큰이 유효하지 않습니다", HttpStatus.BAD_REQUEST);
            }

            String email = jwtTokenProvider.getUserEmail(accessToken, accessSecret);
            registerSecurityContext(request, email);
        }
        filterChain.doFilter(request, response);
    }
}
