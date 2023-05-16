package com.themoment.everygsm.domain.project.dto.response;

import com.themoment.everygsm.domain.project.entity.Project;
import com.themoment.everygsm.domain.project.enums.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectResponseDto {

    private Long projectId;
    private String projectName;
    private String projectDescription;
    private String projectUrl;
    private String projectLogoUri;
    private List<String> projectGithubUrl;
    private String createrName;
    private String createrDescription;
    private String createrLogoUri;
    private String createrGithubUrl;
    private List<Category> category;
    private LocalDateTime createdAt;

    public static ProjectResponseDto from(Project project) {
        return ProjectResponseDto.builder()
                .projectId(project.getProjectId())
                .projectName(project.getProjectName())
                .projectDescription(project.getProjectDescription())
                .projectUrl(project.getProjectUrl())
                .projectLogoUri(project.getProjectLogoUri())
                .projectGithubUrl(project.getProjectGithubUrl().stream().toList())
                .createrName(project.getCreaterName())
                .createrDescription(project.getCreaterDescription())
                .createrLogoUri(project.getCreaterLogoUri())
                .createrGithubUrl(project.getCreaterGithubUrl())
                .category(project.getCategory().stream().toList())
                .createdAt(project.getCreatedAt())
                .build();
    }
}
