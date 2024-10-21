package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.exception.FacultyNotFoundException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;

@Service
public class FacultyServiceImpl implements FacultyService {

    private FacultyRepository repository;
    private StudentRepository studentRepository;

    Logger logger = LoggerFactory.getLogger(FacultyServiceImpl.class);


    public FacultyServiceImpl(FacultyRepository repository, StudentRepository studentRepository) {
        this.repository = repository;
        this.studentRepository = studentRepository;
    }

    @Override
    public Faculty addFaculty(Faculty faculty) {
        logger.info("Was invoked method for adding new faculty");

        return repository.save(faculty);
    }

    @Override
    public Faculty getFacultyById(long id) {
        logger.info("Was invoked method for getting faculty by id");

        return repository.findById(id).orElseThrow(() -> new FacultyNotFoundException(id));
    }

    @Override
    public Faculty updateFaculty(long id, Faculty faculty) {
        logger.info("Was invoked method for updating faculty");

        if (!repository.existsById(id)) {
            throw new FacultyNotFoundException(id);
        }

        faculty.setId(id);
        return repository.save(faculty);
    }

    @Override
    public Faculty deleteFaculty(long id) {
        logger.info("Was invoked method for deleting faculty");

        Faculty tmp = getFacultyById(id);
        repository.deleteById(id);
        return tmp;
    }

    @Override
    public Collection<Faculty> getListOfFacultiesByColor(String color) {
        logger.info("Was invoked method for getting faculty by color");

        return repository.findAllFacultiesByColor(color);
    }

    @Override
    public Faculty getFacultyByName(String name) {
        logger.info("Was invoked method for getting faculty by name ignore case");

        return repository.getFacultyByNameIgnoreCase(name);
    }

    @Override
    public Faculty getFacultyByColor(String color) {
        logger.info("Was invoked method for getting faculty by color ignore case");

        return repository.getFacultyByColorIgnoreCase(color);
    }

    @Override
    public List<Student> getStudents(long id) {
        logger.info("Was invoked method for getting list of students by faculty id");

        return (List<Student>) studentRepository.findByFacultyId(id);
    }

    @Override
    public String getLongestFacultyName() {
        List<Faculty> allFaculties = repository.findAll();
        return allFaculties
                .stream()
                .map(e -> e.getName())
                .max(Comparator.comparingInt(e -> e.length()))
                .orElseThrow();
    }


}
