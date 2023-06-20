package com.themoment.everygsm.domain.project.service;

import com.themoment.everygsm.domain.project.dto.response.ProjectResponseDto;
import com.themoment.everygsm.domain.project.entity.Project;
import com.themoment.everygsm.domain.project.repository.ProjectRepository;
import com.themoment.everygsm.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProjectSortService {

    private final ProjectRepository projectRepository;

    @Transactional
    public List<ProjectResponseDto> sortProjectsByHeart(String strategy) {
        return switch (strategy) {
            case "asc" -> projectRepository.findAllByOrderByHeartCountAsc().stream()
                    .map(ProjectResponseDto::from)
                    .toList();
            case "desc" -> projectRepository.findAllByOrderByHeartCountDesc().stream()
                    .map(ProjectResponseDto::from)
                    .toList();
            default -> throw new CustomException("잘못된 정렬방식 입니다.", HttpStatus.BAD_REQUEST);
        };
    }

    @Transactional
    public List<ProjectResponseDto> sortProjectsByCreated(String strategy) {
        return switch (strategy) {
            case "asc" -> projectRepository.findAllByOrderByCreatedAtAsc().stream()
                    .map(ProjectResponseDto::from)
                    .toList();
            case "desc" -> projectRepository.findAllByOrderByCreatedAtDesc().stream()
                    .map(ProjectResponseDto::from)
                    .toList();
            default -> throw new CustomException("잘못된 정렬방식 입니다.", HttpStatus.BAD_REQUEST);
        };
    }
}
