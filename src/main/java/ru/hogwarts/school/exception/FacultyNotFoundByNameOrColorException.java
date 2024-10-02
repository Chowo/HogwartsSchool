package ru.hogwarts.school.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class FacultyNotFoundByNameOrColorException extends RuntimeException {
    public FacultyNotFoundByNameOrColorException(String name, String color) {
        super("Faculty with a name: %s or color: %s not found".formatted(name, color));
    }
}
