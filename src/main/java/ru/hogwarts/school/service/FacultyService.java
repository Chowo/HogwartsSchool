package ru.hogwarts.school.service;

import ru.hogwarts.school.model.Faculty;

import java.util.Collection;

public interface FacultyService {
    Faculty addFaculty(Faculty faculty);

    Faculty getFacultyById(long id);

    Faculty updateFaculty(long id, Faculty faculty);

    Faculty deleteFaculty(long id);

    Collection<Faculty> getListOfFacultiesByColor(String color);
}
