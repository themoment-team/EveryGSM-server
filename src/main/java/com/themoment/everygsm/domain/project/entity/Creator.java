package com.themoment.everygsm.domain.project.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Creator {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long creatorId;

    @OneToOne
    @JoinColumn(name = "project_id")
    private Project project;

    @Column(name = "creator_name")
    private String creatorName;

    @Column(name = "creator_description")
    private String creatorDescription;

    @Column(name = "creator_logo_uri")
    private String creatorLogoUri;

    @Column(name = "creator_github_url")
    private String creatorGithubUrl;

    public void update(String creatorName, String creatorDescription, String creatorLogoUri, String creatorGithubUrl) {
        this.creatorName = creatorName;
        this.creatorDescription = creatorDescription;
        this.creatorLogoUri = creatorLogoUri;
        this.creatorGithubUrl = creatorGithubUrl;
    }
}
