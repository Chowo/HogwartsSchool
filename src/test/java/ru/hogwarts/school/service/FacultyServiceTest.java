package ru.hogwarts.school.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.hogwarts.school.exception.FacultyNotFoundException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static ru.hogwarts.school.constants.FacultyServiceTestConstants.*;

@ExtendWith(MockitoExtension.class)
class FacultyServiceTest {

    @Mock
    private FacultyRepository repository;

    @InjectMocks
    private FacultyServiceImpl out;


    @Test
    void addFaculty() {
        when(repository.save(any())).thenReturn(FACULTY1);

        Faculty expectedFaculty = new Faculty(1L,"Hufflepuff", "Yellow");
        assertEquals(out.addFaculty(any()), expectedFaculty);
    }

    @Test
    void getFacultyById() {
        when(repository.findById(1L)).thenReturn(Optional.of(FACULTY1));

        Faculty expectedFaculty = new Faculty(1L,"Hufflepuff", "Yellow");
        assertEquals(out.getFacultyById(1L), expectedFaculty);
    }

    @Test
    void changeFaculty() {
        assertThrows(FacultyNotFoundException.class, () -> out.updateFaculty(1L, FACULTY1));
    }

    @Test
    void deleteFaculty() {
        assertThrows(FacultyNotFoundException.class, () -> out.deleteFaculty(1L));
    }

    @Test
    void getListOfFacultiesByColor() {
        when(repository.findAllFacultiesByColor("green")).thenReturn(FACULTIES);

        List<Faculty> expectedListByAge = new ArrayList<>();

        expectedListByAge.add(FACULTY3);

        assertEquals(expectedListByAge, out.getListOfFacultiesByColor("green"));
    }
}