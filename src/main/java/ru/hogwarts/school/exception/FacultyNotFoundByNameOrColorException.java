package ru.hogwarts.school.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.hogwarts.school.service.AvatarServiceImpl;
import ru.hogwarts.school.service.FacultyServiceImpl;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class FacultyNotFoundByNameOrColorException extends RuntimeException {
    Logger logger = LoggerFactory.getLogger(FacultyServiceImpl.class);

    public FacultyNotFoundByNameOrColorException(String name, String color) {
        super("Faculty with a name: %s or color: %s not found".formatted(name, color));
        logger.warn("Faculty with a name: {} or color: {} not found", name, color);
    }
}
