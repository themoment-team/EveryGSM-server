package com.themoment.everygsm.global.util;

import com.themoment.everygsm.domain.user.entity.User;
import com.themoment.everygsm.domain.user.repository.UserRepository;
import com.themoment.everygsm.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserUtil {

    private final UserRepository userRepository;

    public User currentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findUserByUserEmail(email)
                .orElseThrow(() -> new CustomException("email:{}에 해당하는 유저를 찾을 수 없습니다.", HttpStatus.NOT_FOUND));
    }
}
