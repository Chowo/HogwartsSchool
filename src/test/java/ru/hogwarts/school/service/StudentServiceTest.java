package ru.hogwarts.school.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static ru.hogwarts.school.constants.StudentServiceTestConstants.*;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

    @Mock
    private StudentRepository repository;

    @InjectMocks
    private StudentServiceImpl out;


    @Test
    void addStudent() {
        when(repository.save(any())).thenReturn(HARRY);

        Student expectedStudent = new Student("Harry", 11);
        assertEquals(out.addStudent(any()), expectedStudent);
    }

    @Test
    void getStudentById() {
        when(repository.findById(any())).thenReturn(Optional.of(HARRY));
        Student expectedStudent = new Student("Harry", 11);
        assertEquals(out.getStudentById(1L), expectedStudent);

    }

    @Test
    void changeStudent() {
        when(repository.save(any())).thenReturn(FRED);

        Student expectedStudent = new Student("Fred", 13);
        assertEquals(out.changeStudent(any()), expectedStudent);
    }

    @Test
    void deleteStudent() {
        assertThrows(NoSuchElementException.class, () -> out.deleteStudent(1L));
    }

    @Test
    void getStudentsByAge() {
        when(repository.findAll()).thenReturn(STUDENTS);

        List<Student> expectedListByAge = new ArrayList<>();

        expectedListByAge.add(FRED);
        expectedListByAge.add(GEORGE);

        assertEquals(expectedListByAge, out.getStudentsByAge(13));
    }
}