package com.juit.hostelmanagement.controllers.GuiControllers;

import com.juit.hostelmanagement.models.Room;
import com.juit.hostelmanagement.models.Student;
import com.juit.hostelmanagement.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class StudentGuiController {
    @Autowired
    private StudentService studentService;

    @GetMapping("/student/home")
    public String studentHomePage(Principal principal, Model model) {
        String username = principal.getName();
        Student student = studentService.findStudent(Long.valueOf(username));
        model.addAttribute("student", student);
        return "studentHome";

    }
}
