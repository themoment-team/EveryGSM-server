package com.themoment.everygsm.domain.user.entity;

import com.themoment.everygsm.domain.user.enums.Belong;
import com.themoment.everygsm.domain.user.enums.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "user_email")
    @Pattern(regexp = "^[A-Za-z0-9_\\.\\-]+@gsm.hs.kr")
    private String userEmail;

    @Column(name = "user_pwd", length = 500)
    private String userPwd;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_belong")
    private Belong userBelong;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_role")
    private Role userRole;
}
