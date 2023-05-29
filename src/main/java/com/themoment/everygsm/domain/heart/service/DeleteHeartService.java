package com.themoment.everygsm.domain.heart.service;

import com.themoment.everygsm.domain.heart.entity.Heart;
import com.themoment.everygsm.domain.heart.repository.HeartRepository;
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
public class DeleteHeartService {

    private final UserUtil userUtil;
    private final ProjectRepository projectRepository;
    private final HeartRepository heartRepository;

    @Transactional(rollbackFor = Exception.class)
    public void execute(Long projectId) {
        User user = userUtil.currentUser();

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new CustomException("존재하지 않는 게시글 입니다.", HttpStatus.NOT_FOUND));

        if (!heartRepository.existsHeartByUserAndProject(user, project)){
            throw new CustomException("좋아요를 누르지 않은 프로젝트 입니다.", HttpStatus.BAD_REQUEST);
        }

        project.updateHeart(project.getHeartCount() - 1);
        projectRepository.save(project);
        heartRepository.deleteHeartByUserAndProject(user, project);
    }
}
