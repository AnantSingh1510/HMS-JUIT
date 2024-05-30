package com.juit.hostelmanagement.controllers.AuthenticationControllers;

import com.juit.hostelmanagement.dto.StudentDto;
import com.juit.hostelmanagement.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegisterController {

    @Autowired
    private InMemoryUserDetailsManager userDetailsManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private StudentService studentService;

    @PostMapping("/register")
    public String registerStudent(StudentDto student) {
        student.setPassword(passwordEncoder.encode(student.getPassword()));
        studentService.addStudent(student);
        return "redirect:/login?registrationSuccess";
    }

    @GetMapping("/register")
    public String register() {
        return "register"; // Return the register.html Thymeleaf template
    }


//    @PostMapping("/register")
//    public String registerUser(@RequestParam String username, @RequestParam String password) {
//        userDetailsManager.createUser(User.withUsername(username).password(passwordEncoder.encode(password)).roles("STUDENT").build());
//        return "redirect:/login"; // Redirect to login page after successful registration
//    }
}