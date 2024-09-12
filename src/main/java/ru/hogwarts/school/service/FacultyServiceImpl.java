package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class FacultyServiceImpl implements FacultyService {

    private FacultyRepository repository;

    public FacultyServiceImpl(FacultyRepository repository) {
        this.repository = repository;
    }

    @Override
    public Faculty addFaculty(Faculty faculty) {
        return repository.save(faculty);
    }

    @Override
    public Faculty getFacultyById(Long id) {
        return repository.findById(id).get();
    }

    @Override
    public Faculty changeFaculty(Faculty faculty) {
        return repository.save(faculty);
    }

    @Override
    public Faculty deleteFaculty(Long id) {
        Faculty tmp = getFacultyById(id);
        repository.deleteById(id);
        return tmp;
    }

    @Override
    public Collection<Faculty> getListOfFacultiesByColor(String color) {

        return repository.findAllFacultiesByColor(color);
    }
}
