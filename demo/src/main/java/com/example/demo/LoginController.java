package com.example.demo;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.Connection;
import org.mindrot.jbcrypt.BCrypt;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@WebServlet("/login")
public class LoginController {
    private static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/sahayak";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "Luck0409@";
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS)) {
            String sql = "SELECT * FROM users WHERE username = ?";
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setString(1, username);
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    // User exists, now check password (assuming passwords are hashed)
                    String storedPassword = rs.getString("password"); // Get the hashed password
                    if (BCrypt.checkpw(password, storedPassword)) {
                        // Password matches, create session
                        HttpSession session = request.getSession();
                        session.setAttribute("username", username);
                        response.sendRedirect("welcome.jsp"); // Redirect to welcome page
                    } else {
                        // Invalid password
                        response.sendRedirect("register.html?error=Invalid credentials");
                    }
                } else {
                    // Username not found
                    response.sendRedirect("register.html?error=Invalid credentials");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("register.html?error=Database error");
        }
    }
}

