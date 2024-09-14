package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.List;

@RequestMapping("/student")
@RestController
public class StudentController {
    private final StudentService service;

    public StudentController(StudentService service) {
        this.service = service;
    }

    @PostMapping("/add")
    public ResponseEntity<Student> addStudent(@RequestBody Student student) {
        Student addedStudent = service.addStudent(student);
        return ResponseEntity.ok(addedStudent);
    }

    @GetMapping("{id}/get")
    public ResponseEntity<Student> getStudent(@PathVariable Long id) {
        Student student = service.getStudentById(id);
        if (student == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(student);
    }

    @PutMapping("/change")
    public ResponseEntity<Student> changeStudent(@RequestBody Student student) {
        Student changedStudent = service.changeStudent(student);
        if (changedStudent == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(changedStudent);
    }

    @DeleteMapping("{id}/delete")
    public ResponseEntity<Student> expelStudent(@PathVariable Long id) {
        Student expelledStudent = service.deleteStudent(id);
        if (expelledStudent == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(expelledStudent);
    }

    @GetMapping("/get/age")
    public ResponseEntity<List<Student>> getStudentsByAge(@RequestParam int age) {
        List<Student> listOfStudents = service.getStudentsByAge(age);
        if (listOfStudents.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(listOfStudents);
    }
}
