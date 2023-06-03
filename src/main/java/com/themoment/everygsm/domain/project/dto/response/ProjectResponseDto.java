package com.themoment.everygsm.domain.project.dto.response;

import com.themoment.everygsm.domain.bookMark.entity.BookMark;
import com.themoment.everygsm.domain.project.entity.Project;
import com.themoment.everygsm.domain.project.enums.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
                .heartCount(project.getHeartCount())
                .createdAt(project.getCreatedAt())
                .build();
    }

    public static ProjectResponseDto convertToBookMark(BookMark bookMark) {
        return ProjectResponseDto.builder()
                .projectId(bookMark.getProject().getProjectId())
                .projectName(bookMark.getProject().getProjectName())
                .projectDescription(bookMark.getProject().getProjectDescription())
                .projectUrl(bookMark.getProject().getProjectUrl())
                .projectLogoUri(bookMark.getProject().getProjectLogoUri())
                .projectGithubUrl(bookMark.getProject().getProjectGithubUrl().stream().toList())
                .creatorName(bookMark.getProject().getProjectName())
                .creatorDescription(bookMark.getProject().getProjectDescription())
                .creatorLogoUri(bookMark.getProject().getProjectLogoUri())
                .creatorGithubUrl(bookMark.getProject().getProjectUrl())
                .category(bookMark.getProject().getCategory().stream().toList())
                .heartCount(bookMark.getProject().getHeartCount())
                .createdAt(bookMark.getProject().getCreatedAt())
                .build();
    }

    public static List<ProjectResponseDto> convertToBookMarkList(List<BookMark> bookMarks) {
        Stream<BookMark> stream = bookMarks.stream();
        return stream.map(ProjectResponseDto::convertToBookMark).collect(Collectors.toList());
    }
}
