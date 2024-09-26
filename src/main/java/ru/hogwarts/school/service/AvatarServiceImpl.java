package ru.hogwarts.school.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.exception.AvatarNotFoundException;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.AvatarRepository;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

@Service
@Transactional
public class AvatarServiceImpl implements AvatarService {
    private final AvatarRepository avatarRepository;
    private final StudentService studentService;

    @Value(value = "${path.to.avatars.folder}")
    private String avatarsDir;


    public AvatarServiceImpl(AvatarRepository avatarRepository, StudentService studentService) {
        this.avatarRepository = avatarRepository;
        this.studentService = studentService;
    }

    @Override
    public void uploadAvatar(Long studentId, MultipartFile avatarFile) throws IOException {
        createDirectory();
        Student student = studentService.getStudentById(studentId);
        Path filePath = Path.of(avatarsDir, student + "." + getExtensions(avatarFile.getOriginalFilename()));
        avatarFile.transferTo(filePath);

        Avatar avatar = findAvatarByStudentId(studentId);
        avatar.setStudent(student);
        avatar.setFilePath(filePath.toString());
        avatar.setFileSize(avatarFile.getSize());
        avatar.setMediaType(avatarFile.getContentType());
        avatar.setData(avatarFile.getBytes());
        avatarRepository.save(avatar);
    }

    @Override
    public Avatar findAvatarByStudentId(Long studentId) {
        return avatarRepository.findAvatarByStudentId(studentId).orElse(new Avatar());
    }

    @Override
    public Avatar findAvatar(Long id) {
        return avatarRepository.findById(id).orElseThrow(() -> new AvatarNotFoundException(id));
    }

    private String getExtensions(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    private void createDirectory() throws IOException {
        Path path = Path.of(avatarsDir);
        if (Files.notExists(path)) {
            Files.createDirectory(path);
        }
    }


}
