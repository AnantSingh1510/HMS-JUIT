package com.juit.hostelmanagement.controllers;

import com.juit.hostelmanagement.dto.StudentDto;
import com.juit.hostelmanagement.models.Room;
import com.juit.hostelmanagement.models.Student;
import com.juit.hostelmanagement.services.RoomService;
import com.juit.hostelmanagement.services.StudentService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/student")
@PreAuthorize("hasRole('ROLE_WARDEN') or hasRole('ROLE_ADMIN')")
public class StudentController {
    @Autowired
    private StudentService studentService;

    @Autowired
    private RoomService roomService;

    @PostMapping
    public ResponseEntity<Student> addStudent(@Valid @RequestBody StudentDto studentDto){
        Student SavedStudent = studentService.addStudent(studentDto);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Room> findStudentRoom(@RequestParam Long roll){
        Room room = studentService.getRoomByStudentRollNumber(roll);

        return ResponseEntity.ok(room);
    }
}
