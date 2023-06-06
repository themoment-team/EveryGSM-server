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
    private String creatorName;
    private String creatorDescription;
    private String creatorLogoUri;
    private String creatorGithubUrl;
    private List<Category> category;
    private Integer heartCount;
    private Integer visitorCount;
    private LocalDateTime createdAt;

    public static ProjectResponseDto from(Project project) {

        return ProjectResponseDto.builder()
                .projectId(project.getProjectId())
                .projectName(project.getProjectName())
                .projectDescription(project.getProjectDescription())
                .projectUrl(project.getProjectUrl())
                .projectLogoUri(project.getProjectLogoUri())
                .projectGithubUrl(project.getProjectGithubUrl().stream().toList())
                .creatorName(project.getCreator().getCreatorName())
                .creatorDescription(project.getCreator().getCreatorDescription())
                .creatorLogoUri(project.getCreator().getCreatorLogoUri())
                .creatorGithubUrl(project.getCreator().getCreatorGithubUrl())
                .category(project.getCategory().stream().toList())
                .heartCount(project.getHeartCount())
                .visitorCount(project.getVisitorCount())
                .createdAt(project.getCreatedAt())
                .build();
    }
}
