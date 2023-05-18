package com.themoment.everygsm.domain.user.entity;

import com.themoment.everygsm.domain.user.enums.Belong;
import com.themoment.everygsm.domain.user.enums.Role;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public class User {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "user_email")
    private String userEmail;

    @Column(name = "user_pwd")
    private String userPwd;

    @Column(name = "user_belong")
    private Belong userBelong;

    @Column(name = "user_role")
    private Role userRole;
}
