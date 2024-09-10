package ru.hogwarts.school.service;

import ru.hogwarts.school.model.Student;

import java.util.List;

public interface StudentService {
    Student addStudent(Student student);

    Student getStudentById(Long id);

    Student changeStudent(Student student);

    Student deleteStudent(Long id);

    List<Student> getStudentsByAge(int age);
}
