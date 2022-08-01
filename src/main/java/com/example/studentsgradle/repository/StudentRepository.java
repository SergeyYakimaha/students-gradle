package com.example.studentsgradle.repository;

import com.example.studentsgradle.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
}