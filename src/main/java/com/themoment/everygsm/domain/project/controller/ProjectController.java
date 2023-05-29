package com.themoment.everygsm.domain.project.controller;

import com.themoment.everygsm.domain.heart.service.DeleteHeartService;
import com.themoment.everygsm.domain.heart.service.InsertHeartService;
import com.themoment.everygsm.domain.project.dto.request.ProjectModifyDto;
import com.themoment.everygsm.domain.project.dto.request.ProjectRegisterDto;
import com.themoment.everygsm.domain.project.dto.response.ProjectResponseDto;
import com.themoment.everygsm.domain.project.enums.Category;
import com.themoment.everygsm.domain.project.service.ProjectService;
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
    private final InsertHeartService insertHeartService;
    private final DeleteHeartService deleteHeartService;

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody ProjectRegisterDto registerDto) {
        projectService.registerProject(registerDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
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

    @PostMapping("/heart/{projectId}")
    public ResponseEntity<Void> insertHeart(@PathVariable("projectId") Long projectId) {
        insertHeartService.execute(projectId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/heart/{projectId}")
    public ResponseEntity<Void> deleteHeart(@PathVariable("projectId") Long projectId) {
        deleteHeartService.execute(projectId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
