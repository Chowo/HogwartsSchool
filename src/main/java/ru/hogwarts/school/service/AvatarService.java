package ru.hogwarts.school.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.model.Avatar;

import java.io.IOException;
import java.util.List;

public interface AvatarService {
    void uploadAvatar(Long studentId, MultipartFile avatarFile) throws IOException;

    Avatar findAvatarByStudentId(Long studentId);

    Avatar findAvatar(Long id);

    List<Avatar> getAllAvatars(int page, int size);

    List<Avatar> getAllAvatars();

    ResponseEntity<Integer> getInteger();
}
