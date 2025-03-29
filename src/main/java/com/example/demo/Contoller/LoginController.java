package com.example.demo.Contoller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

	@GetMapping("/login")
    public String showLoginPage() {
        return "login"; // Refers to src/main/resources/templates/login.html
    }
}
