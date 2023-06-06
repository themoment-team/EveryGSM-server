package com.themoment.everygsm.domain.project.service;

import com.themoment.everygsm.domain.project.dto.request.ProjectModifyDto;
import com.themoment.everygsm.domain.project.dto.request.ProjectRegisterDto;
import com.themoment.everygsm.domain.project.dto.response.ProjectResponseDto;
import com.themoment.everygsm.domain.project.entity.Creator;
import com.themoment.everygsm.domain.project.entity.Project;
import com.themoment.everygsm.domain.project.enums.Category;
import com.themoment.everygsm.domain.project.enums.Status;
import com.themoment.everygsm.domain.project.repository.ProjectRepository;
import com.themoment.everygsm.domain.user.entity.User;
import com.themoment.everygsm.global.exception.CustomException;
import com.themoment.everygsm.global.util.UserUtil;
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
    private final UserUtil userUtil;

    @Transactional(rollbackFor = Exception.class)
    public void registerProject(ProjectRegisterDto registerDto) {
        User user = userUtil.currentUser();

        Project project = Project.builder()
                .projectName(registerDto.getProjectName())
                .projectDescription(registerDto.getProjectDescription())
                .projectUrl(registerDto.getProjectUrl())
                .projectLogoUri(registerDto.getProjectLogoUri())
                .projectGithubUrl(registerDto.getProjectGithubUrl())
                .category(registerDto.getCategory())
                .status(Status.PENDING)
                .heartCount(0)
                .user(user)
                .createdAt(LocalDateTime.now())
                .build();

        Creator creator = Creator.builder()
                .creatorName(registerDto.getCreatorName())
                .creatorDescription(registerDto.getCreatorDescription())
                .creatorLogoUri(registerDto.getCreatorLogoUri())
                .creatorGithubUrl(registerDto.getCreatorGithubUrl())
                .project(project)
                .build();

        project.setCreator(creator);

        projectRepository.save(project);
    }

    @Transactional
    public List<ProjectResponseDto> getAllProjects() {

        List<Project> projectList = projectRepository.findByStatus(Status.APPROVED);

        return projectList.stream()
                .map(ProjectResponseDto::from)
                .toList();
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
                requestDto.getCategory(),
                requestDto.getCreatorName(),
                requestDto.getCreatorDescription(),
                requestDto.getCreatorLogoUri(),
                requestDto.getCreatorGithubUrl()
        );

        projectRepository.save(project);
    }

    @Transactional
    public List<ProjectResponseDto> getProjectsByCategory(Category category) {

            List<Project> projectList = projectRepository.findAllByCategory(category);

            return projectList.stream().map(ProjectResponseDto::from).toList();
    }

    @Transactional
    public void approveProject(Long projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new CustomException("프로젝트를 찾을 수 없습니다", HttpStatus.NOT_FOUND));

        project.updateStatus(Status.APPROVED);
    }

    @Transactional
    public void disapproveProject(Long projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new CustomException("프로젝트를 찾을 수 없습니다", HttpStatus.NOT_FOUND));

        projectRepository.delete(project);
    }
}
