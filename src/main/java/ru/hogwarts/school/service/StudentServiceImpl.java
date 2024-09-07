package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StudentServiceImpl implements StudentService {
    private Map<Long, Student> students = new HashMap<>();
    private Long generateId = 1L;

    @Override
    public Student addStudent(Student student) {
        student.setId(generateId++);
        students.put(student.getId(), student);
        return student;
    }

    @Override
    public Student getStudentById(Long id) {
        return students.get(id);
    }

    @Override
    public Student changeStudent(Long id, Student student) {
        student.setId(id);
        students.put(id, student);
        return student;
    }

    @Override
    public Student deleteStudent(Long id) {
        return students.remove(id);
    }

    @Override
    public List<Student> getStudentsByAge(int age) {
        List<Student> list = new ArrayList<>();
        for (Student student : students.values()) {
            if (student.getAge() == age) {
                list.add(student);
            }
        }
        return list;
    }
}
