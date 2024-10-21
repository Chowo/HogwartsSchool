package ru.hogwarts.school;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.hogwarts.school.controller.AvatarController;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.AvatarRepository;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.service.AvatarServiceImpl;
import ru.hogwarts.school.service.StudentServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AvatarController.class)
public class AvatarControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private AvatarRepository avatarRepository;

    @MockBean
    private StudentRepository studentRepository;

    @SpyBean
    private AvatarServiceImpl avatarService;

    @SpyBean
    private StudentServiceImpl studentService;

    private ObjectMapper mapper = new ObjectMapper();

    private static Avatar avatar1;
    private static Avatar avatar2;


    @BeforeEach
    void setup() throws Exception {
        avatar1 = generateRandomAvatar();
        avatar2 = generateRandomAvatar();

    }

    @Test
    public void getAllAvatarsTest() throws Exception {

        List<Avatar> avatarList = new ArrayList<>();
        avatarList.add(avatar1);
        avatarList.add(avatar2);


        when(avatarRepository.findAll()).thenReturn(avatarList);

        mvc.perform(MockMvcRequestBuilders
                        .get("/students-avatars/get-all")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(mapper.writeValueAsString(avatarList)));

        assertEquals(avatarRepository.findAll(), avatarList);
    }

    @Test
    public void getInteger() {
        assertNotNull(avatarService.getInteger());
    }

    private Avatar generateRandomAvatar() {
        Random random = new Random();
        Faculty faculty = new Faculty("FacultyName", "FacultyColor");
        faculty.setId(random.nextLong(1, 99));
        Student student = new Student("Student Name", random.nextInt(11, 18));
        student.setId(random.nextLong(1,99));
        student.setFaculty(faculty);
        Avatar avatar = new Avatar();
        avatar.setStudent(student);
        byte[] data = new byte[random.nextInt(1, 9)];
        for (int i = 0; i < data.length; i++) {
            data[i] = (byte) random.nextInt(1, 127);
        }
        avatar.setData(data);
        avatar.setFileSize(random.nextInt(24567));
        avatar.setMediaType("image/jpeg");
        avatar.setFilePath("/avata");
        avatar.setId(random.nextLong(1, 99));
        return avatar;
    }

}
