package com.themoment.everygsm.domain.user.controller;

import com.themoment.everygsm.domain.user.dto.request.SignInRequestDto;
import com.themoment.everygsm.domain.user.dto.request.SignUpRequestDto;
import com.themoment.everygsm.domain.user.dto.response.SignInResponseDto;
import com.themoment.everygsm.domain.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<Void> signUp(@RequestBody @Valid SignUpRequestDto signUpRequestDto) {
        userService.signUp(signUpRequestDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/signin")
    public ResponseEntity<SignInResponseDto> signIn(@RequestBody @Valid SignInRequestDto signInRequestDto, HttpServletResponse response) {
        SignInResponseDto signInResponseDto = userService.signIn(signInRequestDto, response);
        return new ResponseEntity<>(signInResponseDto, HttpStatus.OK);
    }
}
