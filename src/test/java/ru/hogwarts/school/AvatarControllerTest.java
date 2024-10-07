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

import static org.junit.jupiter.api.Assertions.assertEquals;
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

    private static Long ID1 = 1L;
    private static Long ID2 = 2L;
    private static String filePath = "/avatar";
    private static long fileSize1 = 15647;
    private static long fileSize2 = 8927;
    private static String mediaType = "image/jpeg";
    private byte[] data1 = {64, 12};
    private byte[] data2 = {87, 122};
    private ObjectMapper mapper = new ObjectMapper();
    private static JSONObject avatarObject;

    private static Avatar avatar1;
    private static Avatar avatar2;

    private static Student student1;
    private static Student student2;

    @BeforeEach
    void setup() throws Exception {
        avatar1 = new Avatar();
        avatar2 = new Avatar();
        avatar1.setId(ID1);
        avatar2.setId(ID2);
        avatar1.setFilePath(filePath);
        avatar2.setFilePath(filePath);
        avatar1.setFileSize(fileSize1);
        avatar2.setFileSize(fileSize2);
        avatar1.setMediaType(mediaType);
        avatar2.setMediaType(mediaType);
        avatar1.setData(data1);
        avatar1.setData(data2);

        student1 = new Student();

        student1.setName("name1");
        student1.setAge(11);
        student1.setId(1L);

        student2 = new Student();
        student2.setName("name2");
        student2.setAge(12);
        student2.setId(2L);

        Faculty faculty = new Faculty("name", "color");
        faculty.setId(1L);
        student1.setFaculty(faculty);
        student2.setFaculty(faculty);

        avatar1.setStudent(student1);
        avatar2.setStudent(student2);
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

}
