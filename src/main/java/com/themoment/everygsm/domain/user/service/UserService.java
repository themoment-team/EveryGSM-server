package com.themoment.everygsm.domain.user.service;

import com.themoment.everygsm.domain.user.dto.request.SignInRequestDto;
import com.themoment.everygsm.domain.user.dto.request.SignUpRequestDto;
import com.themoment.everygsm.domain.user.dto.response.SignInResponseDto;
import com.themoment.everygsm.domain.user.dto.response.TokenResponse;
import com.themoment.everygsm.domain.user.entity.RefreshToken;
import com.themoment.everygsm.domain.user.entity.User;
import com.themoment.everygsm.domain.user.enums.Role;
import com.themoment.everygsm.domain.user.repository.RefreshTokenRepository;
import com.themoment.everygsm.domain.user.repository.UserRepository;
import com.themoment.everygsm.global.exception.CustomException;
import com.themoment.everygsm.global.security.jwt.JwtTokenProvider;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final RedisTemplate redisTemplate;
    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional(rollbackFor = Exception.class)
    public void signUp(SignUpRequestDto signUpRequestDto) {
        if(userRepository.existsByUserEmail(signUpRequestDto.getUserEmail())) {
            throw new CustomException("이미 사용하고 있는 이메일 입니다", HttpStatus.CONFLICT);
        }

        User user = User.builder()
                .userEmail(signUpRequestDto.getUserEmail())
                .userPwd(passwordEncoder.encode(signUpRequestDto.getUserPwd()))
                .userName(signUpRequestDto.getUserName())
                .userBelong(signUpRequestDto.getUserBelong())
                .userRole(Role.USER)
                .build();
        userRepository.save(user);
    }

    @Transactional(rollbackFor = Exception.class)
    public SignInResponseDto signIn(SignInRequestDto signInRequestDto, HttpServletResponse response) {
        User user = userRepository.findUserByUserEmail(signInRequestDto.getUserEmail())
                .orElseThrow(() -> new CustomException("사용자 이메일을 찾지 못했습니다", HttpStatus.NOT_FOUND));

        if(!passwordEncoder.matches(signInRequestDto.getUserPwd(), user.getUserPwd())) {
            throw new CustomException("패스워드가 틀렸습니다", HttpStatus.BAD_REQUEST);
        }

        String accessToken = jwtTokenProvider.generatedAccessToken(signInRequestDto.getUserEmail());
        String refreshToken = jwtTokenProvider.generatedRefreshToken(signInRequestDto.getUserEmail());
        RefreshToken entityRedis = new RefreshToken(signInRequestDto.getUserEmail(), refreshToken, jwtTokenProvider.getREFRESH_TOKEN_EXPIRE_TIME());

        refreshTokenRepository.save(entityRedis);

        return SignInResponseDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .expiredAt(jwtTokenProvider.getExpiredAtToken())
                .build();
    }
}
