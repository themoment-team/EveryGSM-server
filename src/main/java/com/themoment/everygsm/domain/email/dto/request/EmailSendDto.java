package com.themoment.everygsm.domain.email.dto.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class EmailSendDto {

    @Email
    @NotBlank(message = "이메일은 필수입니다.")
    private final String email;

    @JsonCreator
    public EmailSendDto(@JsonProperty("email") String email) {
        this.email = email;
    }
}
