package com.example.demo;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@WebServlet("/process-payment")
public class CheckoutServlet extends HttpServlet {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/sahayak";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "Luck0409@";
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String service = request.getParameter("service");
        String amount = request.getParameter("amount");
        String upiId = request.getParameter("upiId");
        String fullname = request.getParameter("fullname");
        String contact = request.getParameter("contact");

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS)) {
            String sql = "INSERT INTO payments (service, amount, upiId, fullname, contact) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, service);
                statement.setString(2, amount);
                statement.setString(3, upiId);
                statement.setString(4, fullname);
                statement.setString(5, contact);
                statement.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<h2>Payment is processed successfully!</h2>");
        out.println("<a href='index.html'>Home Page</a>");
    }
}

