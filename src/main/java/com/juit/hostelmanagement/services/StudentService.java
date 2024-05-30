package com.juit.hostelmanagement.services;

import com.juit.hostelmanagement.models.Role;
import com.juit.hostelmanagement.models.Room;
import com.juit.hostelmanagement.models.Student;
import com.juit.hostelmanagement.dto.StudentDto;
import com.juit.hostelmanagement.repositories.RoleRepository;
import com.juit.hostelmanagement.repositories.StudentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private RoomService roomService;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private ModelMapper modelMapper;

    public Student addStudent(StudentDto studentDto){
        Student student = new Student();
//        student.setName(studentDto.getName());
//        student.setCgpa(studentDto.getCgpa());
//        student.setPhoneNo(studentDto.getPhoneNo());
//        student.setRollNumber(studentDto.getRollNumber());
//        student.setParentNo(studentDto.getParentNo());
//        student.setPassword(studentDto.getPassword());

        modelMapper.map(studentDto, student); //Mapping using the ModelMapper to reduce boilerplate code and increase readability

        Role studentRole = roleRepository.findByName("STUDENT");

        if (studentRole == null){
            studentRole = new Role();
            studentRole.setName("STUDENT");
            roleRepository.save(studentRole);
        }

        Set<Role> roles = new HashSet<>();
        roles.add(studentRole);
        student.setRoles(roles);

        student.setRoom(null);

        return studentRepository.save(student);
    }

    public Student findStudent(Long rollNumber){
        return studentRepository.findByRollNumber(rollNumber).orElseThrow(() -> new RuntimeException("Student with the roll number not found!"));
    }

    public List<Student> findAllStudents(){
        return studentRepository.findAll();
    }

    public Student findStudentById(Long id){
        return studentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid student id: " + id));
    }

    public Room getRoomByStudentRollNumber(Long rollNumber) {
        Student student = studentRepository.findByRollNumber(rollNumber)
                .orElse(null);

        assert student != null;
        return student.getRoom();
    }
}
