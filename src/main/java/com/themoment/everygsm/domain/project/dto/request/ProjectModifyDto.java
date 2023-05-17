package com.themoment.everygsm.domain.project.dto.request;

import com.themoment.everygsm.domain.project.enums.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProjectModifyDto {

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
}
