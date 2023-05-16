package com.themoment.everygsm.domain.project.dto.request;

import com.themoment.everygsm.domain.project.enums.Category;
import com.themoment.everygsm.global.annotation.Enum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProjectRegisterDto {

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
}
