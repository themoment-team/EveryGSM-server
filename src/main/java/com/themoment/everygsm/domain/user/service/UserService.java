package com.themoment.everygsm.domain.user.service;

import com.themoment.everygsm.domain.bookMark.repository.BookMarkRepository;
import com.themoment.everygsm.domain.heart.repository.HeartRepository;
import com.themoment.everygsm.domain.email.entity.EmailAuth;
import com.themoment.everygsm.domain.email.repository.EmailAuthRepository;
import com.themoment.everygsm.domain.project.dto.response.ProjectResponseDto;
import com.themoment.everygsm.domain.project.entity.Project;
import com.themoment.everygsm.domain.project.repository.ProjectRepository;
import com.themoment.everygsm.domain.user.dto.request.SignInRequestDto;
import com.themoment.everygsm.domain.user.dto.request.SignUpRequestDto;
import com.themoment.everygsm.domain.user.dto.response.TokenResponse;
import com.themoment.everygsm.domain.user.entity.BlackList;
import com.themoment.everygsm.domain.user.entity.RefreshToken;
import com.themoment.everygsm.domain.user.entity.User;
import com.themoment.everygsm.domain.user.enums.Role;
import com.themoment.everygsm.domain.user.repository.BlackListRepository;
import com.themoment.everygsm.domain.user.repository.RefreshTokenRepository;
import com.themoment.everygsm.domain.user.repository.UserRepository;
import com.themoment.everygsm.global.exception.CustomException;
import com.themoment.everygsm.global.security.jwt.JwtTokenProvider;
import com.themoment.everygsm.global.util.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final RedisTemplate redisTemplate;
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserUtil userUtil;
    private final BlackListRepository blackListRepository;
    private final ProjectRepository projectRepository;
    private final BookMarkRepository bookMarkRepository;
    private final HeartRepository heartRepository;
    private final EmailAuthRepository emailAuthRepository;

    @Transactional(rollbackFor = Exception.class)
    public void signUp(SignUpRequestDto signUpRequestDto) {

        verifyEmail(signUpRequestDto.getUserEmail());

        User user = User.builder()
                .userEmail(signUpRequestDto.getUserEmail())
                .userPwd(passwordEncoder.encode(signUpRequestDto.getUserPwd()))
                .userName(signUpRequestDto.getUserName())
                .userBelong(signUpRequestDto.getUserBelong())
                .userRole(Role.ROLE_USER)
                .build();
        userRepository.save(user);
    }

    private void verifyEmail(String email) {
        if(userRepository.existsByUserEmail(email))
            throw new CustomException("이미 존재하는 이메일입니다.", HttpStatus.BAD_REQUEST);

        EmailAuth emailAuth = emailAuthRepository.findById(email)
                .orElseThrow(() -> new CustomException("인증되지 않은 이메일입니다.", HttpStatus.BAD_REQUEST));

        if(!emailAuth.getAuthentication()){
            throw new CustomException("인증되지 않은 이메일입니다.", HttpStatus.BAD_REQUEST);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public TokenResponse signIn(SignInRequestDto signInRequestDto) {
        User user = userRepository.findUserByUserEmail(signInRequestDto.getUserEmail())
                .orElseThrow(() -> new CustomException("사용자 이메일을 찾지 못했습니다.", HttpStatus.NOT_FOUND));

        if(!passwordEncoder.matches(signInRequestDto.getUserPwd(), user.getUserPwd())) {
            throw new CustomException("비밀번호가 틀렸습니다.", HttpStatus.BAD_REQUEST);
        }

        String accessToken = jwtTokenProvider.generatedAccessToken(signInRequestDto.getUserEmail());
        String refreshToken = jwtTokenProvider.generatedRefreshToken(signInRequestDto.getUserEmail());
        RefreshToken entityRedis = new RefreshToken(signInRequestDto.getUserEmail(), refreshToken, jwtTokenProvider.getREFRESH_TOKEN_EXPIRE_TIME());

        refreshTokenRepository.save(entityRedis);

        return TokenResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .expiredAt(jwtTokenProvider.getExpiredAtToken())
                .build();
    }

    @Transactional(rollbackFor = Exception.class)
    public void logout(String accessToken) {
        User user = userUtil.currentUser();
        RefreshToken refreshToken = refreshTokenRepository.findById(user.getUserEmail())
                .orElseThrow(() -> new CustomException("리프레시 토큰을 찾을 수 없습니다.", HttpStatus.NOT_FOUND));
        refreshTokenRepository.delete(refreshToken);
        saveBlackList(accessToken, user.getUserEmail());
    }

    private void saveBlackList(String accessToken, String userEmail) {
        if (redisTemplate.opsForValue().get(accessToken) != null) {
                throw new CustomException("블랙리스트에 이미 존재합니다.", HttpStatus.CONFLICT);
        }

        BlackList blackList = BlackList.builder()
                .email(userEmail)
                .accessToken(accessToken)
                .build();
        blackListRepository.save(blackList);
    }

    @Transactional(rollbackFor = Exception.class)
    public TokenResponse tokenReIssue(String refreshToken) {
        String email = jwtTokenProvider.getUserEmail(refreshToken, jwtTokenProvider.getRefreshSecret());
        RefreshToken token = refreshTokenRepository.findById(email)
                .orElseThrow(() -> new CustomException("리프레시 토큰을 찾을 수 없습니다.", HttpStatus.NOT_FOUND));

        if(!token.getToken().equals(refreshToken)) {
            throw new CustomException("토큰이 유효하지 않습니다.", HttpStatus.BAD_REQUEST);
        }

        String accessToken = jwtTokenProvider.generatedAccessToken(email);
        String refToken = jwtTokenProvider.generatedRefreshToken(email);
        ZonedDateTime expiredAt = jwtTokenProvider.getExpiredAtToken();
        token.updateRefreshToken(refToken);
        refreshTokenRepository.save(token);

        return TokenResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refToken)
                .expiredAt(expiredAt)
                .build();
    }

    @Transactional
    public List<ProjectResponseDto> getMyProjects() {
        User user = userUtil.currentUser();

        List<Project> projectList = projectRepository.findAllByUser(user);
        return projectList.stream()
                .map(ProjectResponseDto::from)
                .toList();
    }

    @Transactional(rollbackFor = Exception.class)
    public List<ProjectResponseDto> getBookMarkProjects() {
        User user = userUtil.currentUser();

        return bookMarkRepository.findAllByUser(user).stream()
                .map((bookMark) -> ProjectResponseDto.from(bookMark.getProject()))
                .toList();
    }

    @Transactional(rollbackFor = Exception.class)
    public List<ProjectResponseDto> getHeartProjects() {
        User user = userUtil.currentUser();

        return heartRepository.findByUser(user).stream()
                .map((heart -> ProjectResponseDto.from(heart.getProject())))
                .toList();
    }
}
