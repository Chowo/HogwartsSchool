package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StudentServiceImpl implements StudentService {
    private StudentRepository repository;

    public StudentServiceImpl(StudentRepository repository) {
        this.repository = repository;
    }


    @Override
    public Student addStudent(Student student) {
        return repository.save(student);
    }

    @Override
    public Student getStudentById(Long id) {
        return repository.findById(id).get();
    }

    @Override
    public Student changeStudent(Student student) {
        return repository.save(student);
    }

    @Override
    public Student deleteStudent(Long id) {
        Student tmp = getStudentById(id);
        repository.deleteById(id);
        return tmp;
    }

    @Override
    public List<Student> getStudentsByAge(int age) {
        List<Student> listOfAllStudents = repository.findAll();
        List<Student> goalAgeList = new ArrayList<>();
        for (Student student : listOfAllStudents) {
            if (student.getAge() == age) {
                goalAgeList.add(student);
            }
        }
        return goalAgeList;
    }
}
