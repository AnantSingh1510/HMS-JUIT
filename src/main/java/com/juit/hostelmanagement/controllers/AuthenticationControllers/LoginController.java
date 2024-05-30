package com.juit.hostelmanagement.controllers.AuthenticationControllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String login() {
        return "login"; // Return the login.html Thymeleaf template
    }

    @GetMapping("/403")
    public String accessDenied() {
        return "403"; // Return the 403.html Thymeleaf template
    }
}