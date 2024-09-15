package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.exception.FacultyNotFoundException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.Collection;

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
    public Faculty getFacultyById(long id) {
        return repository.findById(id).orElseThrow(() -> new FacultyNotFoundException(id));
    }

    @Override
    public Faculty updateFaculty(long id, Faculty faculty) {
        if (!repository.existsById(id)) {
            throw new FacultyNotFoundException(id);
        }
        faculty.setId(id);
        return repository.save(faculty);
    }

    @Override
    public Faculty deleteFaculty(long id) {
        Faculty tmp = getFacultyById(id);
        repository.deleteById(id);
        return tmp;
    }

    @Override
    public Collection<Faculty> getListOfFacultiesByColor(String color) {

        return repository.findAllFacultiesByColor(color);
    }
}
