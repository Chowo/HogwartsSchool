package ru.hogwarts.school.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.hogwarts.school.model.Faculty;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
class FacultyServiceTest {

    FacultyServiceImpl service = new FacultyServiceImpl();

    @BeforeEach
    void add_faculties() {
        service.addFaculty(new Faculty( "Hufflepuff", "orange"));
        service.addFaculty(new Faculty( "Slytherin", "green"));

        service.addFaculty(new Faculty( "Somenew", "green"));
    }

    @Test
    void addFaculty() {
        Faculty newFaculty = new Faculty("Gryffindor", "red");
        service.addFaculty(newFaculty);
        assertEquals(newFaculty, service.getFacultyById(5L));
    }

    @Test
    void getFacultyById() {
        Faculty expectedFaculty = new Faculty( "Ravenclaw", "blue");
        service.addFaculty(expectedFaculty);
        assertEquals(expectedFaculty, service.getFacultyById(4L));
    }

    @Test
    void changeFaculty() {
        Faculty newFaculty = new Faculty( "Gryffindor", "red");
        service.changeFaculty(4L, newFaculty);
        assertEquals(newFaculty, service.getFacultyById(4L));
    }

    @Test
    void deleteFaculty() {
        Faculty deletedFaculty = service.deleteFaculty(4L);
        assertNull(service.getFacultyById(4L));
    }

    @Test
    void getListOfFacultiesByColor() {
        List<Faculty> expectedList = new ArrayList<>();
        expectedList.add(new Faculty( "Slytherin", "green"));
        expectedList.get(0).setId(2L);
        expectedList.add(new Faculty("Somenew", "green"));
        expectedList.get(1).setId(3L);
        assertEquals(expectedList, service.getListOfFacultiesByColor("green"));
    }
}