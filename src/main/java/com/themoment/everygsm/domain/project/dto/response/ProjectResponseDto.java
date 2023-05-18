package com.themoment.everygsm.domain.project.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.themoment.everygsm.domain.project.entity.Creator;
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
    private String creatorName;
    private String creatorDescription;
    private String creatorLogoUri;
    private String creatorGithubUrl;
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
                .creatorName(project.getProjectName())
                .creatorDescription(project.getProjectDescription())
                .creatorLogoUri(project.getProjectLogoUri())
                .creatorGithubUrl(project.getProjectUrl())
                .category(project.getCategory().stream().toList())
                .createdAt(project.getCreatedAt())
                .build();
    }
}
