package com.example.studentsgradle.service;

import com.example.studentsgradle.entity.Student;

import java.util.List;

public interface StudentService {

    List<Student> getAllStudents();

    Student getStudentById(Long id);

    Student insertOrUpdateStudent(Student student);

    Student addStudent(Student student);

    void removeStudent(Long id);

}
