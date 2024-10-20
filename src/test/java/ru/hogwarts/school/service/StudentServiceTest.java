package ru.hogwarts.school.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.hogwarts.school.exception.StudentNotFoundException;
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

        Student expectedStudent = new Student(1L, "Harry", 11);
        assertEquals(out.addStudent(any()), expectedStudent);
    }

    @Test
    void getStudentById() {
        when(repository.findById(any())).thenReturn(Optional.of(HARRY));
        Student expectedStudent = new Student(1L, "Harry", 11);

        assertEquals(out.getStudentById(1L), expectedStudent);

    }

    @Test
    void changeStudent() {
        assertThrows(StudentNotFoundException.class, () -> out.updateStudent(2L, FRED));
    }

    @Test
    void deleteStudent() {
        assertThrows(StudentNotFoundException.class, () -> out.deleteStudent(1L));
    }

    @Test
    void getStudentsByAge() {
        when(repository.findStudentsByAge(13)).thenReturn(STUDENTS);

        List<Student> expectedListByAge = new ArrayList<>();

        expectedListByAge.add(FRED);
        expectedListByAge.add(GEORGE);

        assertEquals(expectedListByAge, out.getStudentsByAge(13));
    }
}