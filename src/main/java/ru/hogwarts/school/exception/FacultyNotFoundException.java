package ru.hogwarts.school.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.hogwarts.school.service.FacultyServiceImpl;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class FacultyNotFoundException extends RuntimeException {
    Logger logger = LoggerFactory.getLogger(FacultyServiceImpl.class);

    public FacultyNotFoundException(Long id) {
        super("Faculty with ID - %s not found".formatted(id));
        logger.warn("Faculty with ID - {} not found", id);
    }
}
