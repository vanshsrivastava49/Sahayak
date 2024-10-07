package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RegisterController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping("/")
    public String showForm() {
        return "index";
    }
    @GetMapping("/login")
    public String showRegister() {
        return "login";
    }
    @GetMapping("/index")
    public String showHome() {
        return "index";
    }
    @GetMapping("/register")
    public String showLogin() {
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@RequestParam String username, @RequestParam String password, Model model) {
        if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
            model.addAttribute("message", "Username and password cannot be empty.");
            return "result";
        }
        String sql = "INSERT INTO users (username, password) VALUES (?, ?)";
        jdbcTemplate.update(sql, username, password);
        model.addAttribute("message", "User registered successfully!");
        return "result";
    }
    @GetMapping("/profile")
    public String showProfileForm() {
        return "profile";
    }

    @PostMapping("/profile")
    public String userProfile(@RequestParam String name, @RequestParam String age,@RequestParam String gender, @RequestParam String disease, Model model) {
        if (name == null || name.isEmpty() || age == null || age.isEmpty()) {
            model.addAttribute("message", "Name and age cannot be empty.");
            return "result";
        }
        String sql = "INSERT INTO profile (name, age, gender, disease) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, name, age, gender, disease);
        model.addAttribute("message", "Profile details saved successfully!");
        return "result";
    }
}
