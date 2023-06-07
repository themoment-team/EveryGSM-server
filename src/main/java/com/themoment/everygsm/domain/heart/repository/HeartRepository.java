package com.themoment.everygsm.domain.heart.repository;

import com.themoment.everygsm.domain.heart.entity.Heart;
import com.themoment.everygsm.domain.project.entity.Project;
import com.themoment.everygsm.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HeartRepository extends JpaRepository<Heart, Long> {

    void deleteHeartByUserAndProject(User user, Project project);
    boolean existsHeartByUserAndProject(User user, Project project);
    List<Heart> findByUser(User user);
}
