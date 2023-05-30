package com.themoment.everygsm.domain.project.repository;

import com.themoment.everygsm.domain.project.entity.Project;
import com.themoment.everygsm.domain.project.enums.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Long> {

    List<Project> findAllByCategory(Category category);
    List<Project> findAllByOrderByCreatedAtDesc();
    List<Project> findAllByOrderByHeartCountDesc();
    List<Project> findAllByOrderByCreatedAtAsc();
    List<Project> findAllByOrderByHeartCountAsc();
}
