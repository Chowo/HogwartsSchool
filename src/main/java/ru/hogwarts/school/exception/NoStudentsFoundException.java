package ru.hogwarts.school.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.hogwarts.school.service.StudentServiceImpl;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NoStudentsFoundException extends RuntimeException {
    Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);

    public NoStudentsFoundException() {
        super("There are no students");
        logger.warn("There are no students");
    }
}
