package com.themoment.everygsm.domain.project.repository;

import com.themoment.everygsm.domain.project.entity.Project;
import com.themoment.everygsm.domain.project.enums.Category;
import com.themoment.everygsm.domain.project.enums.Status;
import com.themoment.everygsm.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    List<Project> findAllByUser(User user);
    List<Project> findAllByOrderByCreatedAtDesc();
    List<Project> findAllByOrderByHeartCountDesc();
    List<Project> findAllByOrderByCreatedAtAsc();
    List<Project> findAllByOrderByHeartCountAsc();
    List<Project> findByStatus(Status status);
}
