package com.themoment.everygsm.domain.bookMark.entity;

import com.themoment.everygsm.domain.project.entity.Project;
import com.themoment.everygsm.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookMark {

    @Id
    @Column(name = "bookMark_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookMarkId;

    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @JoinColumn(name = "project_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Project project;
}
