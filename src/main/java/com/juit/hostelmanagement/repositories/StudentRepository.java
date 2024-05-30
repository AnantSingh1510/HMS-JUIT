package com.juit.hostelmanagement.repositories;

import com.juit.hostelmanagement.models.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {
    Optional<Student> findByRollNumber(Long rollNumber);
}
