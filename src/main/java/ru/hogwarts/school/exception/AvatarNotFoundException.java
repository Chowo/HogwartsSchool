package ru.hogwarts.school.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.hogwarts.school.service.AvatarServiceImpl;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class AvatarNotFoundException extends RuntimeException {
    Logger logger = LoggerFactory.getLogger(AvatarServiceImpl.class);


    public AvatarNotFoundException(Long id) {
        super("Avatar with id: %s not found".formatted(id));
        logger.warn("Avatar with id {} not found", id);
    }
}
