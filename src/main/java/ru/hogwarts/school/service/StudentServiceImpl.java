package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.exception.StudentNotFoundException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.Collection;
import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {
    private final StudentRepository repository;

    public StudentServiceImpl(StudentRepository repository) {
        this.repository = repository;
    }


    @Override
    public Student addStudent(Student student) {
        return repository.save(student);
    }

    @Override
    public Student getStudentById(long id) {
        return repository.findById(id).orElseThrow(() -> new StudentNotFoundException(id));
    }

    @Override
    public Student updateStudent(long id, Student student) {
        if (!repository.existsById(id)) {
            throw new StudentNotFoundException(id);
        }
        student.setId(id);
        return repository.save(student);
    }

    @Override
    public Student deleteStudent(long id) {
        Student tmp = getStudentById(id);
        repository.deleteById(id);
        return tmp;
    }

    @Override
    public List<Student> getStudentsByAge(int age) {

        return (List<Student>) repository.findStudentsByAge(age);
    }

    @Override
    public List<Student> getStudentsByAgeBetween(int minAge, int maxAge) {
        return (List<Student>) repository.findByAgeBetween(minAge, maxAge);
    }

    @Override
    public Faculty getStudentsFaculty(long id) {
        return repository.findById(id).orElseThrow(() -> new StudentNotFoundException(id)).getFaculty();
    }

    @Override
    public int getStudentsAmount() {
        return repository.getStudentsAmount();
    }

    @Override
    public double getAverageStudentsAge() {
        return repository.getAverageAge();
    }

    @Override
    public Collection<Student> getLeastFiveListedStudents() {
        return repository.getFiveLastStudents();
    }

}
