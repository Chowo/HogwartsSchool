package ru.hogwarts.school.service;

import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.List;

public interface StudentService {
    Student addStudent(Student student);

    Student getStudentById(long id);

    List<Student> getStudentsByAgeBetween(int minAge, int maxAge);

    Student updateStudent(long id, Student student);

    Student deleteStudent(long id);

    List<Student> getStudentsByAge(int age);

    Faculty getStudentsFaculty(long id);
}
