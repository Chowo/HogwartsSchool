package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FacultyServiceImpl implements FacultyService {
    private final Map<Long, Faculty> faculties = new HashMap<>();
    private Long generateFacultyId = 1L;

    @Override
    public Faculty addFaculty(Faculty faculty) {
        faculty.setId(generateFacultyId++);
        faculties.put(faculty.getId(), faculty);
        return faculty;
    }

    @Override
    public Faculty getFacultyById(Long id) {
        return faculties.get(id);
    }

    @Override
    public Faculty changeFaculty(Long id, Faculty faculty) {
        faculty.setId(id);
        faculties.put(id, faculty);
        return faculty;
    }

    @Override
    public Faculty deleteFaculty(Long id) {
        return faculties.remove(id);
    }

    @Override
    public List<Faculty> getListOfFacultiesByColor(String color) {
        List<Faculty> listOfFaculties = new ArrayList<>();
        for (Faculty faculty : faculties.values()) {
            if (faculty.getColor().equals(color)) {
                listOfFaculties.add(faculty);
            }
        }
        return listOfFaculties;
    }
}
