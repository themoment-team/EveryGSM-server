package com.themoment.everygsm.domain.project.controller;

import com.themoment.everygsm.domain.project.dto.request.ProjectModifyDto;
import com.themoment.everygsm.domain.project.dto.request.ProjectRegisterDto;
import com.themoment.everygsm.domain.project.dto.response.ProjectResponseDto;
import com.themoment.everygsm.domain.project.enums.Category;
import com.themoment.everygsm.domain.project.service.ProjectService;
import com.themoment.everygsm.global.annotation.Enum;
import com.themoment.everygsm.global.exception.CustomException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/project")
public class ProjectController {

    private final ProjectService projectService;

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody ProjectRegisterDto registerDto) {
        projectService.registerProject(registerDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<ProjectResponseDto>> getAllProjects() {
        return new ResponseEntity<>(projectService.getAllProjects(), HttpStatus.OK);
    }

    @DeleteMapping("/{projectId}")
    public ResponseEntity<Void> deleteProject(@PathVariable Long projectId) {
        projectService.deleteProject(projectId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/{projectId}")
    public ResponseEntity<Void> modifyProject(@PathVariable Long projectId, @RequestBody ProjectModifyDto requestDto) {
        projectService.modifyProject(projectId, requestDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<ProjectResponseDto>> getProjectsByCategory(@Valid @PathVariable Category category) {
        return new ResponseEntity<>(projectService.getProjectsByCategory(category), HttpStatus.OK);
    }
}
