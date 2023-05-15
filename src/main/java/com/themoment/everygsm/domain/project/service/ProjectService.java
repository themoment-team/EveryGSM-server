package com.themoment.everygsm.domain.project.service;

import com.themoment.everygsm.domain.project.dto.request.ProjectModifyDto;
import com.themoment.everygsm.domain.project.dto.request.ProjectRegisterDto;
import com.themoment.everygsm.domain.project.dto.response.ProjectResponseDto;
import com.themoment.everygsm.domain.project.entity.Project;
import com.themoment.everygsm.domain.project.enums.Category;
import com.themoment.everygsm.domain.project.repository.ProjectRepository;
import com.themoment.everygsm.global.annotation.Enum;
import com.themoment.everygsm.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;

    @Transactional
    public void registerProject(ProjectRegisterDto registerDto) {

        Project project = Project.builder()
                .projectName(registerDto.getProjectName())
                .projectDescription(registerDto.getProjectDescription())
                .projectUrl(registerDto.getProjectUrl())
                .projectLogoUri(registerDto.getProjectLogoUri())
                .projectGithubUrl(registerDto.getProjectGithubUrl())
                .createrName(registerDto.getCreaterName())
                .createrDescription(registerDto.getCreaterDescription())
                .createrLogoUri(registerDto.getCreaterLogoUri())
                .createrGithubUrl(registerDto.getCreaterGithubUrl())
                .category(registerDto.getCategory())
                .createdAt(LocalDateTime.now())
                .build();

        projectRepository.save(project);
    }

    public List<ProjectResponseDto> getAllProjects() {

        List<Project> projectList = projectRepository.findAll();

        return projectList.stream().map(ProjectResponseDto::from).toList();
    }

    @Transactional
    public void deleteProject(Long projectId) {

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new CustomException("삭제할 프로젝트를 찾지 못하였습니다.", HttpStatus.NOT_FOUND));

        projectRepository.delete(project);
    }

    @Transactional
    public void modifyProject(Long projectId, ProjectModifyDto requestDto) {

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new CustomException("수정할 프로젝트를 찾지 못하였습니다.", HttpStatus.NOT_FOUND));

        project.update(
                requestDto.getProjectName(),
                requestDto.getProjectDescription(),
                requestDto.getProjectUrl(),
                requestDto.getProjectLogoUri(),
                requestDto.getProjectGithubUrl(),
                requestDto.getCreaterName(),
                requestDto.getCreaterDescription(),
                requestDto.getCreaterLogoUri(),
                requestDto.getCreaterGithubUrl(),
                requestDto.getCategory()
        );

        projectRepository.save(project);
    }

    public List<ProjectResponseDto> getProjectsByCategory(Category category) {

            List<Project> projectList = projectRepository.findAllByCategory(category);

            return projectList.stream().map(ProjectResponseDto::from).toList();
    }
}
