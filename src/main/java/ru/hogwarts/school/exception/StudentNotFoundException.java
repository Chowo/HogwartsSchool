package ru.hogwarts.school.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.hogwarts.school.service.FacultyServiceImpl;
import ru.hogwarts.school.service.StudentServiceImpl;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class StudentNotFoundException extends RuntimeException {
    Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);

    public StudentNotFoundException(Long id) {
        super("Student with ID %s not found".formatted(id));
        logger.warn("Student with ID {} not found", id);
    }

}
