package com.example.demo;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CheckoutController {
    private final JdbcTemplate jdbcTemplate;
    public CheckoutController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @PostMapping("/process-payment")
    public String processPayment(
            @RequestParam("service") String service,
            @RequestParam("amount") String amount,
            @RequestParam("upiId") String upiId,
            @RequestParam("fullname") String fullname,
            @RequestParam("contact") String contact, 
            Model model) {
        String sql = "INSERT INTO payments (service, amount, upiId, fullname, contact) VALUES (?, ?, ?, ?, ?)";
        try {
            jdbcTemplate.update(sql, service, amount, upiId, fullname, contact);
            model.addAttribute("message", "Payment processed successfully!");
        } catch (Exception e) {
            model.addAttribute("message", "Error processing payment: " + e.getMessage());
            e.printStackTrace();
        }
        return "result";
    }
}
