package com.themoment.everygsm.domain.project.service;

import com.themoment.everygsm.domain.project.dto.response.ProjectResponseDto;
import com.themoment.everygsm.domain.project.entity.Project;
import com.themoment.everygsm.domain.project.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectSortService {

    private final ProjectRepository projectRepository;

    @Transactional(rollbackFor = Exception.class)
    public List<ProjectResponseDto> sortProjectsByHeartWithDesc() {
        List<Project> projectList = projectRepository.findAllByOrderByHeartCountDesc();

        return projectList.stream()
                .map(ProjectResponseDto::from)
                .toList();
    }

    @Transactional(rollbackFor = Exception.class)
    public List<ProjectResponseDto> sortProjectsByHeartWithAsc() {
        List<Project> projectList = projectRepository.findAllByOrderByHeartCountAsc();

        return projectList.stream()
                .map(ProjectResponseDto::from)
                .toList();
    }

    @Transactional(rollbackFor = Exception.class)
    public List<ProjectResponseDto> sortProjectsByCreatedDateWithDesc() {
        List<Project> projectList = projectRepository.findAllByOrderByCreatedAtDesc();

        return projectList.stream()
                .map(ProjectResponseDto::from)
                .toList();
    }

    @Transactional(rollbackFor = Exception.class)
    public List<ProjectResponseDto> sortProjectsByCreatedDateWithAsc() {
        List<Project> projectList = projectRepository.findAllByOrderByCreatedAtAsc();

        return projectList.stream()
                .map(ProjectResponseDto::from)
                .toList();
    }
}
