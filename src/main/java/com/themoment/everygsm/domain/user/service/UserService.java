package com.themoment.everygsm.domain.user.service;

import com.themoment.everygsm.domain.user.dto.request.SignUpRequestDto;
import com.themoment.everygsm.domain.user.entity.User;
import com.themoment.everygsm.domain.user.enums.Role;
import com.themoment.everygsm.domain.user.repository.UserRepository;
import com.themoment.everygsm.global.exception.CustomException;
import com.themoment.everygsm.global.security.jwt.JwtTokenProvider;
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


}
