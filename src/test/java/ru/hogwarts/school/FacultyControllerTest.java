package ru.hogwarts.school;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.hogwarts.school.controller.FacultyController;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.service.FacultyServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FacultyController.class)
public class FacultyControllerTest {

    @SpyBean
    private FacultyServiceImpl facultyService;

    @MockBean
    private FacultyRepository facultyRepository;

    @MockBean
    private StudentRepository studentRepository;

    @Autowired
    private MockMvc mvc;

    private static Long id = 1L;
    private static String name = "name1";
    private static String color = "red";
    private static JSONObject facultyObject;

    private static Faculty faculty1;
    private static Faculty faculty2;

    private ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    void setup() throws Exception {
        facultyObject = new JSONObject();
        facultyObject.put("name", name);
        facultyObject.put("color", color);

        faculty1 = new Faculty(name, color);
        faculty1.setId(id);

        faculty2 = new Faculty(name, color);
        faculty2.setId(id);
    }

    @Test
    public void getAllFacultiesTest() throws Exception {
        when(facultyRepository.findAll()).thenReturn(List.of(
                new Faculty("name1", "color1"),
                new Faculty("name2", "color2")));
        mvc.perform(MockMvcRequestBuilders.get("/faculty"));
    }

    @Test
    public void createFacultyTest() throws Exception {
        //исходные данные
        when(facultyRepository.save(ArgumentMatchers.any(Faculty.class))).thenReturn(faculty1);

        //тестирование
        mvc.perform(MockMvcRequestBuilders
                .post("/faculty/add")
                .content(facultyObject.toString())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(faculty1.getName()))
                .andExpect(jsonPath("$.color").value(faculty1.getColor()));

        verify(facultyRepository, Mockito.times(1) ).save(any());

    }

    @Test
    public void getFacultyTest() throws Exception {
        //исходные данные
        when(facultyRepository.findById(ArgumentMatchers.any(Long.class))).thenReturn(Optional.of(faculty1));

        //тестирование
        mvc.perform(MockMvcRequestBuilders
                .get("/faculty/" + faculty1.getId() + "/get")
                .content(facultyObject.toString())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(faculty1.getName()))
                .andExpect(jsonPath("$.color").value(faculty1.getColor()));

        verify(facultyRepository, Mockito.times(1) ).findById(any());

    }

    @Test
    public void removeFacultyTest() throws Exception {
        //исходные данные
        when(facultyRepository.findById(ArgumentMatchers.any(Long.class))).thenReturn(Optional.of(faculty1));

        //тестирование
        mvc.perform(MockMvcRequestBuilders
                .delete("/faculty/" + faculty1.getId() + "/remove")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(faculty1.getName()))
                .andExpect(jsonPath("$.color").value(faculty1.getColor()));

    }

    @Test
    public void updateFacultyTest() throws Exception {
        //исходные данные
        when(facultyRepository.existsById(faculty1.getId())).thenReturn(true);
        faculty1.setName("name2");
        JSONObject updatedFaculty = new JSONObject();
        updatedFaculty.put("id", faculty1.getId());
        updatedFaculty.put("name", faculty1.getName());
        updatedFaculty.put("color", faculty1.getColor());
        when(facultyRepository.save(ArgumentMatchers.any(Faculty.class))).thenReturn(faculty1);

        //тестирование
        mvc.perform(MockMvcRequestBuilders
                        .put("/faculty/" + faculty1.getId() + "/update")
                        .content(updatedFaculty.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(faculty1.getName()))
                .andExpect(jsonPath("$.color").value(faculty1.getColor()));

        verify(facultyRepository, Mockito.times(1) ).save(any());
    }

    @Test
    public void findByColorIgnoreCaseTest() throws Exception {
        //исходные данные
        when(facultyRepository.getFacultyByColorIgnoreCase(anyString())).thenReturn(faculty2);

        //тестирование
        mvc.perform(MockMvcRequestBuilders
                        .get("/faculty/get?color=red")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(faculty2.getName()))
                .andExpect(jsonPath("$.color").value(faculty2.getColor()));

    }
    @Test
    public void findByNameIgnoreCaseTest() throws Exception {
        //исходные данные
        when(facultyRepository.getFacultyByNameIgnoreCase(anyString())).thenReturn(faculty2);

        //тестирование
        mvc.perform(MockMvcRequestBuilders
                        .get("/faculty/get?name=name1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(faculty2.getName()))
                .andExpect(jsonPath("$.color").value(faculty2.getColor()));


    }

    @Test
    public void getListOfFacultyStudentsTest() throws Exception {
        Student student1 = new Student("name1", 12);
        Student student2 = new Student("name2", 13);
        student1.setId(1L);
        student1.setFaculty(faculty1);

        student2.setId(2L);
        student2.setFaculty(faculty1);
        List<Student> studentsList = new ArrayList<>();
        studentsList.add(student1);
        studentsList.add(student2);
        faculty1.setListOfFacultyStudents(studentsList);

        //исходные данные
        when(studentRepository.findByFacultyId(ArgumentMatchers.any(Long.class))).thenReturn(studentsList);

        //тестирование
        mvc.perform(MockMvcRequestBuilders
                        .get("/faculty/" + faculty1.getId() + "/get/students")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(mapper.writeValueAsString(faculty1.getListOfFacultyStudents())));

        assertEquals(facultyService.getStudents(anyLong()), faculty1.getListOfFacultyStudents());
        assertEquals(faculty1.getListOfFacultyStudents().size(), 2);

    }

    @Test
    public void getLongestFacultyNameTest() throws Exception {
        Faculty faculty = new Faculty("name1", "color1");
        faculty.setId(1L);
        when(facultyRepository.findAll()).thenReturn(List.of(
                faculty,
                new Faculty(2L,"n", "color2")));

        mvc.perform(MockMvcRequestBuilders
                        .get("/faculty/get/longestFacultyName"));
        verify(facultyRepository, Mockito.times(1) ).findAll();

        assertEquals(facultyService.getLongestFacultyName(), faculty.getName());
    }






}
