package com.themoment.everygsm.domain.project.entity;

import com.themoment.everygsm.domain.project.enums.Category;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Project {

    @Id
    @Column(name = "project_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY.IDENTITY)
    private Long projectId;

    @Column(name = "project_name")
    private String projectName;

    @Column(name = "project_description")
    private String projectDescription;

    @Column(name = "project_url")
    private String projectUrl;

    @Column(name = "project_logo_uri")
    private String projectLogoUri;

    @Column(name = "project_github_url")
    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "project_github_url", joinColumns = @JoinColumn(name = "project_id"))
    private List<String> projectGithubUrl;

    @Column(name = "creater_name")
    private String createrName;

    @Column(name = "creater_description")
    private String createrDescription;

    @Column(name = "creater_logo_uri")
    private String createrLogoUri;

    @Column(name = "creater_github_url")
    private String createrGithubUrl;

    @Enumerated(EnumType.STRING)
    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "categories", joinColumns = @JoinColumn(name = "project_id"))
    private List<Category> categories;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
