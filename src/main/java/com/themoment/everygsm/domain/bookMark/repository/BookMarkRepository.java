package com.themoment.everygsm.domain.bookMark.repository;

import com.themoment.everygsm.domain.bookMark.entity.BookMark;
import com.themoment.everygsm.domain.project.entity.Project;
import com.themoment.everygsm.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookMarkRepository extends JpaRepository<BookMark, Long> {
    void deleteBookMarkByUserAndProject(User user, Project project);
    boolean existsBookMarkByUserAndProject(User user, Project project);
    List<BookMark> findAllByUser(User user);
}
