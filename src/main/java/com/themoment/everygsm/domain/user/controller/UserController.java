package com.themoment.everygsm.domain.user.controller;

import com.themoment.everygsm.domain.bookMark.entity.BookMark;
import com.themoment.everygsm.domain.project.dto.response.ProjectResponseDto;
import com.themoment.everygsm.domain.user.dto.request.SignInRequestDto;
import com.themoment.everygsm.domain.user.dto.request.SignUpRequestDto;
import com.themoment.everygsm.domain.user.dto.response.TokenResponse;
import com.themoment.everygsm.domain.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<Void> signUp(@RequestBody @Valid SignUpRequestDto signUpRequestDto) {
        userService.signUp(signUpRequestDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/signin")
    public ResponseEntity<TokenResponse> signIn(@RequestBody @Valid SignInRequestDto signInRequestDto) {
        TokenResponse tokenResponse = userService.signIn(signInRequestDto);
        return new ResponseEntity<>(tokenResponse, HttpStatus.OK);
    }

    @DeleteMapping("/logout")
    public ResponseEntity<Void> logout(@RequestHeader("Authorization") String accessToken) {
        userService.logout(accessToken);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping
    public ResponseEntity<TokenResponse> reIssueToken(@RequestHeader("RefreshToken") String token) {
        TokenResponse reIssueToken = userService.tokenReIssue(token);
        return new ResponseEntity<>(reIssueToken, HttpStatus.OK);
    }

    @GetMapping("/projects")
    public ResponseEntity<List<ProjectResponseDto>> getMyProjects() {
        List<ProjectResponseDto> myProjects = userService.getMyProjects();
        return new ResponseEntity<>(myProjects, HttpStatus.OK);
    }

    @GetMapping("/bookmark/projects")
    public ResponseEntity<List<ProjectResponseDto>> getBookMarkProjects() {
        List<ProjectResponseDto> bookMarkProjects = userService.getBookMarkProjects();
        return new ResponseEntity<>(bookMarkProjects, HttpStatus.OK);
    }
}
