package com.themoment.everygsm.domain.bookMark.service;

import com.themoment.everygsm.domain.bookMark.entity.BookMark;
import com.themoment.everygsm.domain.bookMark.repository.BookMarkRepository;
import com.themoment.everygsm.domain.project.entity.Project;
import com.themoment.everygsm.domain.project.repository.ProjectRepository;
import com.themoment.everygsm.domain.user.entity.User;
import com.themoment.everygsm.global.exception.CustomException;
import com.themoment.everygsm.global.util.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BookMarkService {

    private final UserUtil userUtil;
    private final ProjectRepository projectRepository;
    private final BookMarkRepository bookMarkRepository;

    @Transactional(rollbackFor = Exception.class)
    public void addBookMark(Long projectId) {
        User user = userUtil.currentUser();

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new CustomException("즐겨찾기할 프로젝트를 찾지 못했습니다", HttpStatus.NOT_FOUND));

        if(bookMarkRepository.existsBookMarkByUserAndProject(user, project)) {
            throw new CustomException("이미 즐겨찾기한 프로젝트 입니다.", HttpStatus.BAD_REQUEST);
        }

        BookMark bookMark = BookMark.builder()
                .user(user)
                .project(project)
                .build();

        bookMarkRepository.save(bookMark);
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteBookMark(Long projectId) {
        User user = userUtil.currentUser();

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new CustomException("즐겨찾기할 프로젝트를 찾지 못했습니다", HttpStatus.NOT_FOUND));

        if(!bookMarkRepository.existsBookMarkByUserAndProject(user, project)) {
            throw new CustomException("즐겨찾기 하지 않은 프로젝트 입니다.", HttpStatus.BAD_REQUEST);
        }

        bookMarkRepository.deleteBookMarkByUserAndProject(user, project);
    }
}
