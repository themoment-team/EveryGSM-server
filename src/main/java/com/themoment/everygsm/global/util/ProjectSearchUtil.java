package com.themoment.everygsm.global.util;

import com.themoment.everygsm.domain.project.entity.Project;
import org.springframework.stereotype.Component;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

@Component
public class ProjectSearchUtil {

    public List<Project> filterProjectsByKeyword(List<Project> projects, String keyword) throws IllegalAccessException {
        List<Project> filteredProjects = new ArrayList<>();

        for (Project project : projects) {
            if (isKeywordPresentInEntity(project, keyword)) {
                filteredProjects.add(project);
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
}
