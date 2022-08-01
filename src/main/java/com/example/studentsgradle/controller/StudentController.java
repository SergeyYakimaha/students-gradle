package com.example.studentsgradle.controller;

import com.example.studentsgradle.entity.Student;
import com.example.studentsgradle.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {

    private final StudentService service;

    @Autowired
    public StudentController(StudentService service) {
        this.service = service;
    }

    @GetMapping
    List<Student> getAllStudents() {
        return service.getAllStudents();
    }

    @GetMapping("/{id}")
    Student getStudentById(@PathVariable Long id) {
        return service.getStudentById(id);
    }

    @PostMapping
    Student insertOrUpdateStudent(@RequestBody Student student) {
        return service.insertOrUpdateStudent(student);
    }

    @DeleteMapping("/{id}")
    void deleteStudent(@PathVariable Long id) {
        service.removeStudent(id);
    }
}
