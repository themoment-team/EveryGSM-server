package com.themoment.everygsm.domain.user.controller;

import com.themoment.everygsm.domain.user.dto.request.SignInRequestDto;
import com.themoment.everygsm.domain.user.dto.request.SignUpRequestDto;
import com.themoment.everygsm.domain.user.dto.response.NewTokenResponse;
import com.themoment.everygsm.domain.user.dto.response.SignInResponseDto;
import com.themoment.everygsm.domain.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<SignInResponseDto> signIn(@RequestBody @Valid SignInRequestDto signInRequestDto) {
        SignInResponseDto signInResponseDto = userService.signIn(signInRequestDto);
        return new ResponseEntity<>(signInResponseDto, HttpStatus.OK);
    }

    @DeleteMapping("/logout")
    public ResponseEntity<Void> logout(@RequestHeader("Authorization") String accessToken) {
        userService.logout(accessToken);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping
    public ResponseEntity<NewTokenResponse> reIssueToken(@RequestHeader("RefreshToken") String token) {
        NewTokenResponse reIssueToken = userService.tokenReIssue(token);
        return new ResponseEntity<>(reIssueToken, HttpStatus.OK);
    }
}
