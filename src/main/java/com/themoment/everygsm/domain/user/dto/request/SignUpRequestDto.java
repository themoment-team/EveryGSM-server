package com.themoment.everygsm.domain.user.dto.request;

import com.themoment.everygsm.domain.user.enums.Belong;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequestDto {
    @NotBlank(message = "이름은 필수 입력값입니다")
    private String userName;

    @NotBlank(message = "이메일은 필수 입력값입니다")
    private String userEmail;

    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+\\-={}\\[\\]\\|:;\"'<>,.?/])(?!.*\\s).{8,20}$", message = "영문자, 숫자 그리고 특수문자(!@#$%^&*()_+-={}[]|:;\"'<>,.?/)를 조합하여 8~20자 사이로 입력해주세요.")
    @NotBlank(message = "비밀번호는 필수 입력값입니다")
    private String userPwd;

    @Enumerated(EnumType.STRING)
    private Belong userBelong;
}
