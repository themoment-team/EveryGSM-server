package com.themoment.everygsm.domain.project.entity;

import com.themoment.everygsm.domain.project.enums.Category;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DialectOverride;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@DynamicUpdate
@NoArgsConstructor
@AllArgsConstructor
public class Project {

    @Id
    @Column(name = "project_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @OneToOne(mappedBy = "project", cascade = CascadeType.ALL)
    private Creator creator;

    @Enumerated(EnumType.STRING)
    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "category", joinColumns = @JoinColumn(name = "project_id"))
    private List<Category> category;

    @ColumnDefault("0")
    @Column(name = "heart_count", nullable = false)
    private Integer heartCount;

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    public void update(String projectName, String projectDescription, String projectUrl, String projectLogoUri,
                       List<String> projectGithubUrl, List<Category> category, String creatorName, String creatorDescription,
                       String creatorLogoUri, String creatorGithubUrl) {
        this.projectName = projectName;
        this.projectDescription = projectDescription;
        this.projectUrl = projectUrl;
        this.projectLogoUri = projectLogoUri;
        this.projectGithubUrl = projectGithubUrl;
        this.category = category;
        creator.update(creatorName, creatorDescription, creatorLogoUri, creatorGithubUrl);
    }

    public void updateHeart(Integer heartCount) {
        this.heartCount = heartCount;
    }
}
