package com.themoment.everygsm.domain.project.controller;

import com.themoment.everygsm.domain.project.dto.response.ProjectResponseDto;
import com.themoment.everygsm.domain.project.service.ProjectSortService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/project/sort")
public class ProjectSortController {

    private final ProjectSortService projectSortService;

    @GetMapping("/heart")
    public ResponseEntity<List<ProjectResponseDto>> getSortedProjectsByHeart(@RequestParam String strategy) {
        return new ResponseEntity<>(projectSortService.sortProjectsByHeart(strategy), HttpStatus.OK);
    }

    @GetMapping("/created")
    public ResponseEntity<List<ProjectResponseDto>> getSortedProjectsByCreated(@RequestParam String strategy) {
        return new ResponseEntity<>(projectSortService.sortProjectsByCreated(strategy), HttpStatus.OK);
    }
}
