package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.exception.FacultyNotFoundByNameOrColorException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.FacultyService;

import java.util.List;

@RestController
@RequestMapping("/faculty")
public class FacultyController {
    private final FacultyService service;

    public FacultyController(FacultyService service) {
        this.service = service;
    }

    @PostMapping("/add")
    public ResponseEntity<Faculty> addFaculty(@RequestBody Faculty faculty) {
        Faculty additionFaculty = service.addFaculty(faculty);
        return ResponseEntity.ok(additionFaculty);
    }

    @GetMapping("{id}/get")
    public ResponseEntity<Faculty> getFaculty(@PathVariable("id") Long id) {
        Faculty goalFaculty = service.getFacultyById(id);
        if (goalFaculty == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(goalFaculty);
    }

    @PutMapping("{id}/update")
    public ResponseEntity<Faculty> updateFaculty(@PathVariable("id") Long id, @RequestBody Faculty faculty) {
        Faculty changedFaculty = service.updateFaculty(id, faculty);
        if (changedFaculty == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(changedFaculty);
    }

    @DeleteMapping("{id}/remove")
    public ResponseEntity<Faculty> deleteFaculty(@PathVariable("id") Long id) {
        Faculty deletableFaculty = service.deleteFaculty(id);
        return ResponseEntity.ok(deletableFaculty);
    }

    @GetMapping("/get/color")
    public ResponseEntity<List<Faculty>> getListOfFacultiesByColor(@RequestParam String color) {
        List<Faculty> listOfFaculties = (List<Faculty>) service.getListOfFacultiesByColor(color);
        if (listOfFaculties.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(listOfFaculties);
    }

    @GetMapping("/get")
    public ResponseEntity<Faculty> getFacultyByNameOrColorIgnoreCase(@RequestParam(required = false, name = "name") String name,
                                                                     @RequestParam(required = false, name = "color") String color) {
        if (name != null && !name.isBlank()) {
            return ResponseEntity.ok(service.getFacultyByName(name));
        }
        if (color != null && !color.isBlank()) {
            return ResponseEntity.ok(service.getFacultyByColor(color));
        }
        throw new FacultyNotFoundByNameOrColorException(name, color);
    }

    @GetMapping("{id}/get/students")
    public ResponseEntity<List<Student>> getListOfFacultyStudents(@PathVariable("id") Long id) {
        return ResponseEntity.ok(service.getStudents(id));
    }
}
