package ru.hogwarts.school.service;

import ru.hogwarts.school.model.Faculty;

import java.util.List;

public interface FacultyService {
    Faculty addFaculty(Faculty faculty);

    Faculty getFacultyById(Long id);

    Faculty changeFaculty(Faculty faculty);

    Faculty deleteFaculty(Long id);

    List<Faculty> getListOfFacultiesByColor(String color);
}
