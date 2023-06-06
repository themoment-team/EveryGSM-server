package com.themoment.everygsm.domain.project.controller;

import com.themoment.everygsm.domain.bookMark.service.BookMarkService;
import com.themoment.everygsm.domain.heart.service.HeartService;
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
    private final HeartService heartService;
    private final BookMarkService bookMarkService;

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

    @GetMapping("/search")
    public ResponseEntity<List<ProjectResponseDto>> searchProjects(@RequestParam(value = "category", required = false) List<Category> categories, @RequestParam(value = "keyword", required = false) String keyword) {
        return new ResponseEntity<>(projectService.searchProjects(categories, keyword), HttpStatus.OK);
    }

    @PostMapping("/{projectId}/heart")
    public ResponseEntity<Void> insertHeart(@PathVariable("projectId") Long projectId) {
        heartService.insertHeart(projectId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{projectId}/heart")
    public ResponseEntity<Void> deleteHeart(@PathVariable("projectId") Long projectId) {
        heartService.deleteHeart(projectId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/{projectId}/bookmark")
    public ResponseEntity<Void> addBookMark(@PathVariable("projectId") Long projectId) {
        bookMarkService.addBookMark(projectId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{projectId}/bookmark")
    public ResponseEntity<Void> deleteBookMark(@PathVariable("projectId") Long projectId) {
        bookMarkService.deleteBookMark(projectId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/{projectId}/approve")
    public ResponseEntity<Void> approveProject(@PathVariable("projectId") Long projectId) {
        projectService.approveProject(projectId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/{projectId}/disapprove")
    public ResponseEntity<Void> disapproveProject(@PathVariable("projectId") Long projectId) {
        projectService.disapproveProject(projectId);
    }
  
    @PostMapping("/{projectId}/visit")
    public ResponseEntity<Void> updateVisitorCount(@PathVariable("projectId") Long projectId) {
        projectService.updateVisitorCount(projectId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
