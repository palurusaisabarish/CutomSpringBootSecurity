package com.example.demo.Contoller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.ExceptionHandling.UsernameAlreadyExistsException;
import com.example.demo.model.User;
import com.example.demo.service.UserService;

@Controller
public class RegistrationController {

    @Autowired
    private UserService userService;

    @GetMapping("/register")
    public String showRegistrationPage() {
        return "register"; // Refers to src/main/resources/templates/register.html
    }

    @PostMapping("/register")
    public String registerUser(
        @RequestParam String username,
        @RequestParam String password,
        RedirectAttributes redirectAttributes // For passing error messages
    ) {
        try {
            User user = new User();
            user.setUsername(username);
            user.setPassword(password);
            userService.saveUser(user);
            return "redirect:/login?success=true";
        } catch (UsernameAlreadyExistsException e) {
            // Pass error to the registration page
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/register";
        }
    }
}