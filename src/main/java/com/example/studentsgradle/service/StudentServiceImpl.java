package com.example.studentsgradle.service;

import com.example.studentsgradle.entity.Student;
import com.example.studentsgradle.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService{

    private final StudentRepository repository;

    @Autowired
    public StudentServiceImpl(StudentRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Student> getAllStudents() {
        return repository.findAll();
    }

    @Override
    public Student getStudentById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public Student insertOrUpdateStudent(Student student) {
        return repository.save(student);

    }

    @Override
    public Student addStudent(Student student) {
        return repository.save(student);
    }

    @Override
    public void removeStudent(Long id) {
        repository.deleteById(id);
    }
}
