package ru.hogwarts.school.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.hogwarts.school.model.Student;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StudentServiceTest {
    StudentServiceImpl service = new StudentServiceImpl();

    @BeforeEach
    void add_students (){
        service.addStudent(new Student( "Peter", 12));
        service.addStudent(new Student( "Harry", 11));
        service.addStudent(new Student( "Hermione", 11));
        service.addStudent(new Student( "George", 13));
        service.addStudent(new Student( "Fred", 13));
    }

    @Test
    void addStudent() {
        Student newStudent = new Student("Ronald", 11);
        Student additionalStudent = service.addStudent(newStudent);

        assertEquals(newStudent, additionalStudent);
    }

    @Test
    void getStudentById() {
        Student newStudent = new Student("Ronald", 11);
        service.addStudent(newStudent);
        assertEquals(newStudent, service.getStudentById(6L));
    }

    @Test
    void changeStudent() {
        Student newStudent = new Student("Draco", 11);
        service.changeStudent(3L, newStudent);
        assertEquals(newStudent, service.getStudentById(3L));
    }

    @Test
    void deleteStudent() {
        Student deletedStudent = service.deleteStudent(1L);
        assertNull(service.getStudentById(1L));
    }

    @Test
    void getStudentsByAge() {
        List<Student> expectedListByAge = new ArrayList<>();
        expectedListByAge.add(service.getStudentById(2L));
        expectedListByAge.add(service.getStudentById(3L));
        assertEquals(expectedListByAge, service.getStudentsByAge(11));
    }
}