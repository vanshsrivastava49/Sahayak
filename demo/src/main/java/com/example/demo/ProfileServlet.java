package com.example.demo;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/profile")
public class ProfileServlet extends HttpServlet {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/sahayak";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "Luck0409@";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String age = request.getParameter("age");
        String gender = request.getParameter("gender");
        String disease = request.getParameter("disease");

        // Basic validation
        if (name == null || name.isEmpty() || age == null || age.isEmpty() || gender == null || gender.isEmpty() || disease == null || disease.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "All fields are required.");
            return;
        }

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS)) {
            String sql = "INSERT INTO profile (name, age, gender, disease) VALUES (?, ?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, name);
                statement.setString(2, age);
                statement.setString(3, gender);
                statement.setString(4, disease);
                statement.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error saving profile details.");
            return;
        }

        // Redirect to the doGet method to display the profile
        response.sendRedirect(request.getContextPath() + "/profile");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        StringBuilder htmlResponse = new StringBuilder();
        htmlResponse.append("<html><head><title>Profile Details</title></head><body>");
        htmlResponse.append("<h1>Profile Details</h1>");
        htmlResponse.append("<table border='1'><tr><th>Name</th><th>Age</th><th>Gender</th><th>Disease</th></tr>");

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS)) {
            String sql = "SELECT * FROM profile"; // Adjust as necessary to limit results
            try (PreparedStatement statement = connection.prepareStatement(sql);
                 ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    htmlResponse.append("<tr>");
                    htmlResponse.append("<td>").append(resultSet.getString("name")).append("</td>");
                    htmlResponse.append("<td>").append(resultSet.getString("age")).append("</td>");
                    htmlResponse.append("<td>").append(resultSet.getString("gender")).append("</td>");
                    htmlResponse.append("<td>").append(resultSet.getString("disease")).append("</td>");
                    htmlResponse.append("</tr>");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error fetching profile details.");
            return;
        }

        htmlResponse.append("</table>");
        htmlResponse.append("<a href='index.html'>Go back</a>");
        htmlResponse.append("</body></html>");

        response.setContentType("text/html");
        try (PrintWriter out = response.getWriter()) {
            out.println(htmlResponse.toString());
        }
    }
}
