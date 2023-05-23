package com.themoment.everygsm.global.util;

import com.themoment.everygsm.domain.user.entity.User;
import com.themoment.everygsm.domain.user.repository.UserRepository;
import com.themoment.everygsm.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserUtil {

    private final UserRepository userRepository;

    public User currentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findUserByUserEmail(email)
                .orElseThrow(() -> new CustomException("유저를 찾을수 없습니다.", HttpStatus.NOT_FOUND));
    }
}
