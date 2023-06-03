package com.themoment.everygsm.domain.project.controller;

import com.themoment.everygsm.domain.project.dto.response.ProjectResponseDto;
import com.themoment.everygsm.domain.project.service.ProjectSortService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/project/sort")
public class ProjectSortController {

    private final ProjectSortService projectSortService;

    @GetMapping("/heart/asc")
    public ResponseEntity<List<ProjectResponseDto>> getProjectsByHeartSortAsc() {
        return new ResponseEntity<>(projectSortService.sortProjectsByHeartWithAsc(), HttpStatus.OK);
    }

    @GetMapping("/heart/desc")
    public ResponseEntity<List<ProjectResponseDto>> getProjectsByHeartSortDesc() {
        return new ResponseEntity<>(projectSortService.sortProjectsByHeartWithDesc(), HttpStatus.OK);
    }

    @GetMapping("/created/asc")
    public ResponseEntity<List<ProjectResponseDto>> getProjectsByCreatedAtSortAsc() {
        return new ResponseEntity<>(projectSortService.sortProjectsByCreatedDateWithAsc(), HttpStatus.OK);
    }

    @GetMapping("/created/desc")
    public ResponseEntity<List<ProjectResponseDto>> getProjectsByCreatedAtSortDesc() {
        return new ResponseEntity<>(projectSortService.sortProjectsByCreatedDateWithDesc(), HttpStatus.OK);
    }
}
