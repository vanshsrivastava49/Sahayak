package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @GetMapping("/login")
    public String showLogin() {
        return "login";
    }
    @PostMapping("/login")
    public String loginUser(@RequestParam String username, @RequestParam String password, Model model) {
        if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
            model.addAttribute("message", "Username and password cannot be empty.");
            return "result";
        }
        String sql = "SELECT COUNT(*) FROM users WHERE username = ? AND password = ?";
        int count = jdbcTemplate.queryForObject(sql, Integer.class, username, password);
        if (count == 0) {
            model.addAttribute("message", "Invalid username or password.");
            return "result";
        }
        model.addAttribute("message", "Login successful!");
        return "result";
    }
}
