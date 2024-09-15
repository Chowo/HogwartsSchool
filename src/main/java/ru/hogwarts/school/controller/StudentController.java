package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.List;

@RestController
@RequestMapping("/student")
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
    public ResponseEntity<Student> getStudent(@PathVariable("id") Long id) {
        Student student = service.getStudentById(id);
        if (student == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(student);
    }

    @PutMapping("{id}/update")
    public ResponseEntity<Student> updateStudent(@PathVariable("id") Long id, @RequestBody Student student) {
        Student changedStudent = service.updateStudent(id, student);
        if (changedStudent == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(changedStudent);
    }

    @DeleteMapping("{id}/remove")
    public ResponseEntity<Student> expelStudent(@PathVariable("id") Long id) {
        Student expelledStudent = service.deleteStudent(id);
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

    @GetMapping("/get/age-range")
    public ResponseEntity<List<Student>> getStudentsByAgeBetween(@RequestParam int minAge, @RequestParam int maxAge) {
        List<Student> listOfStudents = service.getStudentsByAgeBetween(minAge, maxAge);
        if (listOfStudents.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(listOfStudents);
    }

    @GetMapping("{id}/get/faculty")
    public Faculty getStudentsFaculty(@PathVariable("id") Long id) {

        return ResponseEntity.ok(service.getStudentsFaculty(id)).getBody();
    }


}
