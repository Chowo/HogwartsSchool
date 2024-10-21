package ru.hogwarts.school.service;

import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.exception.AvatarNotFoundException;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.AvatarRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

@Service
@Transactional
public class AvatarServiceImpl implements AvatarService {
    private final AvatarRepository avatarRepository;
    private final StudentService studentService;

    @Value(value = "${path.to.avatars.folder}")
    private String avatarsDir;


    Logger logger = LoggerFactory.getLogger(AvatarServiceImpl.class);

    public AvatarServiceImpl(AvatarRepository avatarRepository, StudentService studentService) {
        this.avatarRepository = avatarRepository;
        this.studentService = studentService;
    }

    @Override
    public void uploadAvatar(Long studentId, MultipartFile avatarFile) throws IOException {
        logger.info("Was invoked method for avatar upload");
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
        logger.info("Was invoked method for searching avatar by student id");

        return avatarRepository.findAvatarByStudentId(studentId).orElse(new Avatar());
    }

    @Override
    public Avatar findAvatar(Long id) {
        logger.info("Was invoked method for searching avatar by id");


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

    @Override
    public List<Avatar> getAllAvatars(int page, int size) {
        logger.info("Was invoked method for getting all avatars on page {} with size {}", page, size);

        PageRequest pageRequest = PageRequest.of(page - 1, size);
        return avatarRepository.findAll(pageRequest).getContent();
    }

    @Override
    public List<Avatar> getAllAvatars() {
        logger.info("Was invoked method for getting all avatars");

        return avatarRepository.findAll();
    }

    @Override
    public ResponseEntity<Integer> getInteger() {
        int sum = Stream.iterate(1, a -> a + 1)
                .limit(1_000_000)
                .parallel()
                .reduce(0, (a, b) -> a + b);
        return ResponseEntity.ok(sum);
    }


}
