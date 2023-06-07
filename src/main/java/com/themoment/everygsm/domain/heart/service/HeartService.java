package com.themoment.everygsm.domain.heart.service;

import com.themoment.everygsm.domain.heart.entity.Heart;
import com.themoment.everygsm.domain.heart.repository.HeartRepository;
import com.themoment.everygsm.domain.project.entity.Project;
import com.themoment.everygsm.domain.project.repository.ProjectRepository;
import com.themoment.everygsm.domain.user.entity.User;
import com.themoment.everygsm.global.exception.CustomException;
import com.themoment.everygsm.global.redis.Entity.LockObject;
import com.themoment.everygsm.global.redis.repository.RedisLockRepository;
import com.themoment.everygsm.global.util.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class HeartService {

    private final UserUtil userUtil;
    private final ProjectRepository projectRepository;
    private final HeartRepository heartRepository;
    private final RedisLockRepository redisLockRepository;

    @Transactional(rollbackFor = Exception.class)
    public void insertHeart(Long projectId) {
        User user = userUtil.currentUser();

        while (!redisLockRepository.lock(projectId, user.getUserId())) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

                Project project = projectRepository.findById(projectId)
                        .orElseThrow(() -> new CustomException("좋아요 누를 프로젝를 찾지 못했습니다.", HttpStatus.NOT_FOUND));

                if (heartRepository.existsHeartByUserAndProject(user, project)){
                    throw new CustomException("이미 좋아요를 누른 프로젝트 입니다.", HttpStatus.BAD_REQUEST);
                }
                Heart heart = Heart.builder()
                        .user(user)
                        .project(project)
                        .build();

            try {
                project.updateHeart(project.getHeartCount() + 1);
                heartRepository.save(heart);
            } finally {
                redisLockRepository.unlock(LockObject.builder()
                        .projectId(projectId)
                        .userId(user.getUserId())
                        .build());
            }
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteHeart(Long projectId){
        User user = userUtil.currentUser();

        while (!redisLockRepository.lock(projectId, user.getUserId())) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

            Project project = projectRepository.findById(projectId)
                    .orElseThrow(() -> new CustomException("좋아요 누를 프로젝를 찾지 못했습니다.", HttpStatus.NOT_FOUND));

            if (!heartRepository.existsHeartByUserAndProject(user, project)) {
                throw new CustomException("좋아요를 누르지 않은 프로젝트 입니다.", HttpStatus.BAD_REQUEST);
            }

        try {
            project.updateHeart(project.getHeartCount() - 1);
            heartRepository.deleteHeartByUserAndProject(user, project);
        } finally {
            redisLockRepository.unlock(LockObject.builder()
                    .projectId(projectId)
                    .userId(user.getUserId())
                    .build());
        }
    }
}
