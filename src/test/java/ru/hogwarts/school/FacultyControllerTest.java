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

import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FacultyControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private StudentController studentController;

    @Autowired
    private FacultyRepository facultyRepository;
    @Autowired
    private FacultyController facultyController;
    @Autowired
    private FacultyService facultyService;

    @Autowired
    private TestRestTemplate restTemplate;

    private static String NAME = "NAME1";
    private static String COLOR = "COLOR1";

    private static Faculty FACULTY = new Faculty(NAME, COLOR);

    @Test
    public void contextLoads() {
        assertThat(facultyController).isNotNull();

    }

    @Test
    public void addFacultyTest() {
        Faculty newFaculty = restTemplate.postForObject("http://localhost:" + port + "/faculty/add", FACULTY, Faculty.class);

        assertThat(newFaculty.getName()).isEqualTo(NAME);
        assertThat(newFaculty.getColor()).isEqualTo(COLOR);
        assertThat(newFaculty.getId()).isNotNull();
        assertThat(newFaculty).isNotNull();

        facultyRepository.deleteById(newFaculty.getId());
    }

    @Test
    public void findFacultyTest() {

        Faculty newFaculty = restTemplate.postForObject("http://localhost:" + port + "/faculty/add", FACULTY, Faculty.class);
        Faculty goalFaculty = restTemplate.getForObject("http://localhost:" + port + "/faculty/" + newFaculty.getId() + "/get", Faculty.class);

        assertThat(goalFaculty.getName()).isEqualTo(NAME);
        assertThat(goalFaculty.getColor()).isEqualTo(COLOR);
        assertThat(goalFaculty.getId()).isNotNull();
        assertThat(goalFaculty).isNotNull();

        facultyRepository.deleteById(goalFaculty.getId());
    }

    @Test
    public void updateFacultyTest() {

        Faculty newFaculty = restTemplate.postForObject("http://localhost:" + port + "/faculty/add", FACULTY, Faculty.class);
        newFaculty.setName("New Name");

        ResponseEntity<Faculty> updatedFaculty = restTemplate.exchange("http://localhost:" + port + "/faculty/" + newFaculty.getId() + "/update", HttpMethod.PUT, new HttpEntity<>(newFaculty), Faculty.class);
        assertThat(Objects.requireNonNull(updatedFaculty.getBody()).getName()).isEqualTo("New Name");
        assertThat(updatedFaculty.getBody().getColor()).isEqualTo(COLOR);

        facultyRepository.deleteById(updatedFaculty.getBody().getId());

    }

    @Test
    public void deleteFacultyTest() {
        Faculty newFaculty = restTemplate.postForObject("http://localhost:" + port + "/faculty/add", FACULTY, Faculty.class);

        ResponseEntity<Faculty> deletedFaculty = restTemplate.exchange("http://localhost:" + port + "/faculty/" + newFaculty.getId() + "/remove", HttpMethod.DELETE, null, Faculty.class);

        assertThat(Objects.requireNonNull(deletedFaculty.getBody()).getName()).isEqualTo(NAME);
        assertThat(deletedFaculty.getBody().getColor()).isEqualTo(COLOR);

        Faculty deletedPosition = restTemplate.getForObject("http://localhost:" + port + "/faculty/" + newFaculty.getId() + "/get", Faculty.class);

        assertThat(deletedPosition.getName()).isEqualTo(null);
        assertThat(deletedPosition.getColor()).isEqualTo(null);
        assertThat(deletedPosition.getId()).isEqualTo(null);

        ResponseEntity<Faculty> deleteAftermath = restTemplate.exchange("http://localhost:" + port + "/faculty/" + newFaculty.getId() + "/get", HttpMethod.GET, null, Faculty.class);

        assertThat(deleteAftermath.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);

    }

    @Test
    public void getFacultyByColorTest() {
        Faculty anotherFaculty = new Faculty("New Name", "black");
        Faculty newFaculty = restTemplate.postForObject("http://localhost:" + port + "/faculty/add", anotherFaculty, Faculty.class);

        ResponseEntity<List<Faculty>> faculties = restTemplate.exchange("http://localhost:" + port + "/faculty/get/color?color=black", HttpMethod.GET, null, new ParameterizedTypeReference<List<Faculty>>() {
        });

        List<Faculty> facultiesList = faculties.getBody();
        assertThat(facultiesList.size()).isEqualTo(1);
        assertThat(facultiesList.get(0).getName()).isEqualTo("New Name");
        assertThat(facultiesList.get(0).getColor()).isEqualTo("black");

        facultyRepository.deleteById(newFaculty.getId());
    }

    @Test
    public void getFacultyByNameOrColorIgnoreCaseTest() {
        Faculty anotherFaculty = new Faculty("New Name", "black");
        Faculty newFaculty = restTemplate.postForObject("http://localhost:" + port + "/faculty/add", anotherFaculty, Faculty.class);

        ResponseEntity<Faculty> returnedFacultyByName = restTemplate.exchange("http://localhost:" + port + "/faculty/get?name=new name", HttpMethod.GET, null, Faculty.class);
        ResponseEntity<Faculty> returnedFacultyByColor = restTemplate.exchange("http://localhost:" + port + "/faculty/get?color=BLACK", HttpMethod.GET, null, Faculty.class);

        assertThat(returnedFacultyByName.getBody().getName()).isEqualTo(returnedFacultyByColor.getBody().getName());
        assertThat(returnedFacultyByName.getBody().getColor()).isEqualTo(returnedFacultyByColor.getBody().getColor());
        assertThat(returnedFacultyByName.getBody().getId()).isEqualTo(returnedFacultyByColor.getBody().getId());

        facultyRepository.deleteById(newFaculty.getId());
    }

    @Test
    public void getListOfFacultyStudentsTest() {
        Faculty anotherFaculty = new Faculty("New Name", "black");

        Faculty newFaculty = restTemplate.postForObject("http://localhost:" + port + "/faculty/add", anotherFaculty, Faculty.class);

        Student anotherStudent1 = new Student("Moe", 999);
        Student anotherStudent2 = new Student("Joy", 888);

        anotherStudent1.setFaculty(newFaculty);
        anotherStudent2.setFaculty(newFaculty);

        Student newStudent1 = restTemplate.postForObject("http://localhost:" + port + "/student/add", anotherStudent1, Student.class);
        Student newStudent2 = restTemplate.postForObject("http://localhost:" + port + "/student/add", anotherStudent2, Student.class);


        ResponseEntity<List<Student>> students = restTemplate.exchange("http://localhost:" + port + "/faculty/" + newFaculty.getId() + "/get/students", HttpMethod.GET, null, new ParameterizedTypeReference<List<Student>>() {
        });

        List<Student> listOfStudents = students.getBody();

        assertThat(listOfStudents).isNotNull();
        assertThat(listOfStudents.size()).isEqualTo(2);
        assertThat(listOfStudents).contains(newStudent1);
        assertThat(listOfStudents).contains(newStudent2);

        studentRepository.deleteById(newStudent1.getId());
        studentRepository.deleteById(newStudent2.getId());
        facultyRepository.deleteById(newFaculty.getId());
    }

}
