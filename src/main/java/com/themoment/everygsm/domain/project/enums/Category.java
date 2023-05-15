package com.themoment.everygsm.domain.project.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Category {
    JavaScript("JavaScript"),
    TypeScript("TypeScript"),
    Java("Java"),
    Python("Python"),
    php("php"),
    Kotlin("Kotlin"),
    Swift("Swift"),
    Express("Express"),
    NestJs("NestJs"),
    Spring("Spring"),
    Django("Django"),
    React("React"),
    NextJs("NextJs"),
    Flask("Flask"),
    FastAPI("FastAPI"),
    Angular("Angular"),
    Vue("Vue");
    private final String name;
}
