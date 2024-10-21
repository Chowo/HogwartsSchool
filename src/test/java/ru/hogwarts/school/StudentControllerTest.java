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
import ru.hogwarts.school.controller.StudentController;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.service.StudentServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.hogwarts.school.constants.StudentServiceTestConstants.*;

@WebMvcTest(StudentController.class)
public class StudentControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private StudentRepository studentRepository;

    @SpyBean
    private StudentServiceImpl studentService;


    private static Long id = 1L;
    private static String name1 = "name1";
    private static String name2 = "name2";
    private static int age1 = 11;
    private static int age2 = 13;
    private static JSONObject studentObject;

    private static Student student1;
    private static Student student2;

    private ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    void setup() throws Exception {
        studentObject = new JSONObject();
        studentObject.put("name", name1);
        studentObject.put("age", age1);

        student1 = new Student();

        student1.setName(name1);
        student1.setAge(age1);
        student1.setId(id);

        student2 = new Student();
        student2.setName(name2);
        student2.setAge(age2);
        student2.setId(id);
    }

    @Test
    public void addStudentTest() throws Exception {
        //исходные данные
        when(studentRepository.save(ArgumentMatchers.any(Student.class))).thenReturn(student1);

        //тестирование
        mvc.perform(MockMvcRequestBuilders
                        .post("/student/add")
                        .content(studentObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(student1.getName()))
                .andExpect(jsonPath("$.age").value(student1.getAge()));

        verify(studentRepository, Mockito.times(1)).save(any());

    }

    @Test
    public void getStudentTest() throws Exception {
        //исходные данные
        when(studentRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.of(student1));

        //тестирование
        mvc.perform(MockMvcRequestBuilders
                        .get("/student/" + student1.getId() + "/get")
                        .content(studentObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(student1.getName()))
                .andExpect(jsonPath("$.age").value(student1.getAge()));

        verify(studentRepository, Mockito.times(1)).findById(any());

    }

    @Test
    public void removeStudentTest() throws Exception {
        //исходные данные
        when(studentRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.of(student1));

        //тестирование
        mvc.perform(MockMvcRequestBuilders
                        .delete("/student/" + student1.getId() + "/remove")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(student1.getName()))
                .andExpect(jsonPath("$.age").value(student1.getAge()));

    }

    @Test
    public void updateStudentTest() throws Exception {
        //исходные данные
        when(studentRepository.existsById(student1.getId())).thenReturn(true);
        student1.setName(name2);
        JSONObject updatedStudent = new JSONObject();
        updatedStudent.put("id", student1.getId());
        updatedStudent.put("name", student1.getName());
        updatedStudent.put("age", student1.getAge());
        when(studentRepository.save(any(Student.class))).thenReturn(student1);

        //тестирование
        mvc.perform(MockMvcRequestBuilders
                        .put("/student/" + student1.getId() + "/update")
                        .content(updatedStudent.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(student1.getName()))
                .andExpect(jsonPath("$.age").value(student1.getAge()));

        verify(studentRepository, Mockito.times(1)).save(any());
    }

    @Test
    public void getStudentsByAgeBetweenTest() throws Exception {

        Faculty faculty1 = new Faculty();
        faculty1.setId(1L);
        faculty1.setName("faculty1");
        faculty1.setColor("red");

        student1.setId(1L);
        student1.setFaculty(faculty1);

        student2.setId(2L);
        student2.setFaculty(faculty1);
        List<Student> studentsList = new ArrayList<>();
        studentsList.add(student1);
        studentsList.add(student2);

        when(studentRepository.findByAgeBetween(anyInt(), anyInt())).thenReturn(studentsList);

        mvc.perform(MockMvcRequestBuilders
                        .get("/student/get/age-range?minAge=10&maxAge=14")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(mapper.writeValueAsString(studentsList)));

        assertEquals(studentService.getStudentsByAgeBetween(1, 20), studentsList);
    }

    @Test
    public void getStudentsFacultyTest() throws Exception {
        Faculty faculty1 = new Faculty();
        faculty1.setId(1L);
        faculty1.setName("faculty1");
        faculty1.setColor("red");
        student1.setFaculty(faculty1);
        when(studentRepository.findById(anyLong())).thenReturn(Optional.of(student1));

        mvc.perform(MockMvcRequestBuilders
                        .get("/student/" + student1.getId() + "/get/faculty")
                        .content(studentObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(student1.getFaculty().getName()))
                .andExpect(jsonPath("$.color").value(student1.getFaculty().getColor()));

        verify(studentRepository, Mockito.times(1)).findById(any());
    }

    @Test
    public void getStudentsAmountTest() throws Exception {

        when(studentRepository.getStudentsAmount()).thenReturn(1);

        assertEquals(studentService.getStudentsAmount(), 1);

    }

    @Test
    public void getStudentsAverageAgeTest() throws Exception {

        when(studentRepository.getAverageAge()).thenReturn(12.4);

        assertEquals(studentService.getAverageStudentsAge(), 12.4);

    }

    @Test
    public void getLeastFiveListedStudentsTest() throws Exception {

        Faculty faculty1 = new Faculty();
        faculty1.setId(1L);
        faculty1.setName("faculty1");
        faculty1.setColor("red");

        student1.setId(1L);
        student1.setFaculty(faculty1);

        student2.setId(2L);
        student2.setFaculty(faculty1);
        List<Student> studentsList = new ArrayList<>();
        studentsList.add(student1);
        studentsList.add(student2);

        when(studentRepository.getFiveLastStudents()).thenReturn(studentsList);

        mvc.perform(MockMvcRequestBuilders
                        .get("/student/get/last-five")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(mapper.writeValueAsString(studentsList)));

        assertEquals(studentService.getLeastFiveListedStudents(), studentsList);
    }

    @Test
    public void getStudentsAverageAgeWithStreamTest() throws Exception {

        when(studentRepository.findAll()).thenReturn(STUDENTS2);

        assertEquals(studentService.getAverageAgeWithStream(), 12.0);

    }

    @Test
    public void getStudentsNameStartsWithA() throws Exception {

        List<Student> expectedList = List.of(ALAN, ALEX);

        when(studentRepository.findAll()).thenReturn(STUDENTS3);

        mvc.perform(MockMvcRequestBuilders
                .get("/student/get/all-students-starts-with-A"));

        verify(studentRepository, Mockito.times(1)).findAll();

        assertEquals(studentService.getAllStudentsNameStartsWithA(), expectedList);

    }


}

