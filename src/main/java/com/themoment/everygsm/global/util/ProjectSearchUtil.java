package com.themoment.everygsm.global.util;

import com.themoment.everygsm.domain.project.entity.Project;
import com.themoment.everygsm.domain.project.enums.Category;
import com.themoment.everygsm.global.exception.CustomException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProjectSearchUtil {

    // 아래 메서드 수정해야함
    public List<Project> filterProjectsByKeywordAndCategories(List<Project> projects, String keyword, List<Category> categories) {
        return projects.stream()
                .filter(project -> {
                    try {
                        boolean isCategoryMatched = categories == null || isCategoriesPresentInEntity(project, categories);
                        boolean isKeywordMatched = keyword == null || isKeywordPresentInEntity(project, keyword);
                        return isCategoryMatched && isKeywordMatched;
                    } catch (IllegalAccessException e) {
                        throw new CustomException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
                    }
                })
                .collect(Collectors.toList());
    }


    private boolean isKeywordPresentInEntity(Project project, String keyword) throws IllegalAccessException {
        Class<?> entityClass = project.getClass();
        Field[] fields = entityClass.getDeclaredFields();

        for (Field field : fields) {
            field.setAccessible(true);
            Object fieldValue = field.get(project);

            if (fieldValue != null && fieldValue.toString().contains(keyword)) {
                return true;
            }
        }

        return false;
    }

    private boolean isCategoriesPresentInEntity(Project project, List<Category> categories) throws IllegalArgumentException {
        List<Category> projectCategories = project.getCategory();
        return projectCategories.stream()
                .anyMatch(categories::contains);
    }
}
