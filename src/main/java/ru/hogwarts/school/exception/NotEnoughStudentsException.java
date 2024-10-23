package ru.hogwarts.school.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.hogwarts.school.service.StudentServiceImpl;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotEnoughStudentsException extends RuntimeException {
    Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);

    public NotEnoughStudentsException() {
        super("There is not enough students in repository");
        logger.warn("There is not enough students in repository");
    }
}
