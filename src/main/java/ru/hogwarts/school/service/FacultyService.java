package ru.hogwarts.school.service;

import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.Collection;
import java.util.List;

public interface FacultyService {
    Faculty addFaculty(Faculty faculty);

    Faculty getFacultyById(long id);

    Faculty updateFaculty(long id, Faculty faculty);

    Faculty deleteFaculty(long id);

    Collection<Faculty> getListOfFacultiesByColor(String color);

    Faculty getFacultyByName(String name);

    Faculty getFacultyByColor(String color);

    List<Student> getStudents(long id);


    String getLongestFacultyName();
}
