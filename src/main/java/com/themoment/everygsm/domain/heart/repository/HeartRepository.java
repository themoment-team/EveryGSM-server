package com.themoment.everygsm.domain.heart.repository;

import com.themoment.everygsm.domain.heart.entity.Heart;
import com.themoment.everygsm.domain.project.entity.Project;
import com.themoment.everygsm.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HeartRepository extends JpaRepository<Heart, Long> {

    void deleteHeartByUserAndProject(User user, Project project);
    void deleteByProject(Project project);
    void deleteByUser(User user);
    List<Heart> findByUser(User user);
    boolean existsHeartByUserAndProject(User user, Project project);
}
