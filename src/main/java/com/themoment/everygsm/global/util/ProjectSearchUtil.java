package com.themoment.everygsm.global.util;

import com.themoment.everygsm.domain.project.entity.Project;
import com.themoment.everygsm.domain.project.enums.Category;
import org.springframework.stereotype.Component;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

@Component
public class ProjectSearchUtil {

    public List<Project> filterProjectsByKeywordAndCategories(List<Project> projects, String keyword, List<Category> categories) throws IllegalAccessException {
        List<Project> filteredProjects = new ArrayList<>();

        for (Project project : projects) {
            if(categories == null && keyword != null) {
                if (isKeywordPresentInEntity(project, keyword)) {
                    filteredProjects.add(project);
                }
            }
            else if(categories != null && keyword == null) {
                if (isCategoriesPresentInEntity(project, categories)) {
                    filteredProjects.add(project);
                }
            }
            else {
                if (categories != null && isCategoriesPresentInEntity(project, categories)) {
                    if (isKeywordPresentInEntity(project, keyword)) {
                        filteredProjects.add(project);
                    }
                }
            }
        }

        return filteredProjects;
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
