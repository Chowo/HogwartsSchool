package ru.hogwarts.school;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.hogwarts.school.controller.FacultyController;
import ru.hogwarts.school.controller.StudentController;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.service.FacultyService;
import ru.hogwarts.school.service.StudentService;

import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StudentControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private StudentController studentController;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private FacultyRepository facultyRepository;
    @Autowired
    private FacultyController facultyController;
    @Autowired
    private FacultyService facultyService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private TestRestTemplate restTemplate;

    private static String NAME = "Hagrid";
    private static int AGE = 13;

    private static Student student = new Student(NAME, AGE);

    @Test
    public void contextLoads() {
        assertThat(studentController).isNotNull();

    }

    @Test
    public void addStudentTest() {
        Student newStudent = restTemplate.postForObject("http://localhost:" + port + "/student/add", student, Student.class);

        assertThat(newStudent.getName()).isEqualTo(NAME);
        assertThat(newStudent.getAge()).isEqualTo(AGE);
        assertThat(newStudent.getId()).isNotNull();
        assertThat(newStudent).isNotNull();

        studentRepository.deleteById(newStudent.getId());
    }

    @Test
    public void findStudentTest() {

        Student newStudent = restTemplate.postForObject("http://localhost:" + port + "/student/add", student, Student.class);
        Student goalStudent = restTemplate.getForObject("http://localhost:" + port + "/student/" + newStudent.getId() + "/get", Student.class);

        assertThat(goalStudent.getName()).isEqualTo(NAME);
        assertThat(goalStudent.getAge()).isEqualTo(AGE);
        assertThat(goalStudent.getId()).isNotNull();
        assertThat(goalStudent).isNotNull();

        studentRepository.deleteById(goalStudent.getId());
    }

    @Test
    public void updateStudentTest() {

        Student newStudent = restTemplate.postForObject("http://localhost:" + port + "/student/add", this.student, Student.class);
        newStudent.setName("Not Hagrid");

        ResponseEntity<Student> updatedStudent = restTemplate.exchange("http://localhost:" + port + "/student/" + newStudent.getId() + "/update", HttpMethod.PUT, new HttpEntity<>(newStudent), Student.class);
        assertThat(Objects.requireNonNull(updatedStudent.getBody()).getName()).isEqualTo("Not Hagrid");
        assertThat(updatedStudent.getBody().getAge()).isEqualTo(AGE);

        studentRepository.deleteById(updatedStudent.getBody().getId());

    }

    @Test
    public void deleteStudentTest() {
        Student newStudent = restTemplate.postForObject("http://localhost:" + port + "/student/add", this.student, Student.class);

        ResponseEntity<Student> deletedStudent = restTemplate.exchange("http://localhost:" + port + "/student/" + newStudent.getId() + "/remove", HttpMethod.DELETE, null, Student.class);

        assertThat(Objects.requireNonNull(deletedStudent.getBody()).getName()).isEqualTo(NAME);
        assertThat(deletedStudent.getBody().getAge()).isEqualTo(AGE);

        Student deletedPosition = restTemplate.getForObject("http://localhost:" + port + "/student/" + newStudent.getId() + "/get", Student.class);

        assertThat(deletedPosition.getName()).isEqualTo(null);
        assertThat(deletedPosition.getAge()).isEqualTo(0);
        assertThat(deletedPosition.getId()).isEqualTo(null);

        ResponseEntity<Student> deleteAftermath = restTemplate.exchange("http://localhost:" + port + "/student/" + newStudent.getId() + "/get", HttpMethod.GET, null, Student.class);

        assertThat(deleteAftermath.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    public void getStudentsByAgeTest() {
        Student anotherStudent = new Student("Moe", 999);
        Student newStudent = restTemplate.postForObject("http://localhost:" + port + "/student/add", anotherStudent, Student.class);

        ResponseEntity<List<Student>> students = restTemplate.exchange("http://localhost:" + port + "/student/get/age?age=999", HttpMethod.GET, null, new ParameterizedTypeReference<List<Student>>() {
        });

        List<Student> studentList = students.getBody();
        assertThat(studentList.size()).isEqualTo(1);
        assertThat(studentList.get(0).getName()).isEqualTo("Moe");
        assertThat(studentList.get(0).getAge()).isEqualTo(999);

        studentRepository.deleteById(newStudent.getId());
    }

    @Test
    public void getStudentsByAgeBetweenTest() {
        Student anotherStudent1 = new Student("Moe", 999);
        Student anotherStudent2 = new Student("Joy", 888);
        Student newStudent1 = restTemplate.postForObject("http://localhost:" + port + "/student/add", anotherStudent1, Student.class);
        Student newStudent2 = restTemplate.postForObject("http://localhost:" + port + "/student/add", anotherStudent2, Student.class);

        ResponseEntity<List<Student>> students = restTemplate.exchange("http://localhost:" + port + "/student/get/age-range?minAge=800&maxAge=1000", HttpMethod.GET, null, new ParameterizedTypeReference<List<Student>>() {
        });

        List<Student> studentList = students.getBody();
        assertThat(studentList.size()).isEqualTo(2);
        assertThat(studentList.get(0).getName()).isEqualTo("Moe");
        assertThat(studentList.get(0).getAge()).isEqualTo(999);

        studentRepository.deleteById(newStudent1.getId());
        studentRepository.deleteById(newStudent2.getId());
    }

    @Test
    public void getStudentsFacultyTest() {
        Faculty faculty = restTemplate.postForObject("http://localhost:" + port + "/faculty/add", new Faculty("name1", "color"), Faculty.class);
        Student anotherStudent1 = new Student("Moe", 999);
        anotherStudent1.setFaculty(faculty);
        Student newStudent1 = restTemplate.postForObject("http://localhost:" + port + "/student/add", anotherStudent1, Student.class);
        ResponseEntity<Faculty> studentsFaculty = restTemplate.exchange("http://localhost:" + port + "/student/" + newStudent1.getId() + "/get/faculty", HttpMethod.GET, null, Faculty.class);
        System.out.println(studentsFaculty);
        assertThat(studentsFaculty).isNotNull();
        assertThat(Objects.requireNonNull(studentsFaculty.getBody()).getName()).isEqualTo(faculty.getName());
        assertThat(studentsFaculty.getBody().getColor()).isEqualTo(faculty.getColor());

        studentRepository.deleteById(newStudent1.getId());
        facultyRepository.deleteById(faculty.getId());
    }


}
