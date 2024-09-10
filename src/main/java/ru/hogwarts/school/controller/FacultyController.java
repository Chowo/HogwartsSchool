package ru.hogwarts.school.controller;

import jakarta.annotation.PostConstruct;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.FacultyService;
import ru.hogwarts.school.service.FacultyServiceImpl;

import java.util.List;

@RequestMapping("/faculty")
@RestController
public class FacultyController {
    private final FacultyService service;

    public FacultyController(FacultyService service) {
        this.service = service;
    }

    @PostConstruct
    public void init() {
        service.addFaculty(new Faculty("test1", "red"));
        service.addFaculty(new Faculty("test2", "blue"));
        service.addFaculty(new Faculty("test3", "green"));
        service.addFaculty(new Faculty("test4", "red"));
    }

    @PostMapping("/add")
    public ResponseEntity<Faculty> addFaculty(@RequestBody Faculty faculty) {
        Faculty additionFaculty = service.addFaculty(faculty);
        return ResponseEntity.ok(additionFaculty);
    }

    @GetMapping("{id}/get")
    public ResponseEntity<Faculty> getFaculty(@PathVariable Long id) {
        Faculty goalFaculty = service.getFacultyById(id);
        if (goalFaculty == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(goalFaculty);
    }

    @PutMapping("/update")
    public ResponseEntity<Faculty> changeFaculty(@RequestBody Faculty faculty) {
        Faculty changedFaculty = service.changeFaculty(faculty);
        if (changedFaculty == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(changedFaculty);
    }

    @DeleteMapping("{id}/remove")
    public ResponseEntity<Faculty> deleteFaculty(@PathVariable Long id) {
        Faculty deletableFaculty = service.deleteFaculty(id);
        if (deletableFaculty == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(deletableFaculty);
    }

    @GetMapping("/color")
    public ResponseEntity<List<Faculty>> getListOfFacultiesByColor(String color) {
        List<Faculty> listOfFaculties = service.getListOfFacultiesByColor(color);
        if (listOfFaculties.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(listOfFaculties);
    }
}
