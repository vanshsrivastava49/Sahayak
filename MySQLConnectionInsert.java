import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class MySQLConnectionInsert {
    public static void main(String[] args) {
        String url = "jdbc:mysql://127.0.0.1:3306/sahayak"; // Update to your database URL
        String user = "root"; // Your MySQL username
        String password = "Luck0409@"; // Your MySQL passwordS

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Scanner sc = new Scanner(System.in);

        try {
            // Establish the connection
            connection = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to the database successfully!");

            // Prepare the SQL statement
            String insertSQL = "INSERT INTO users (username, password) VALUES (?, ?)";
            preparedStatement = connection.prepareStatement(insertSQL);

            // Prompt for user input
            System.out.print("Enter Name: ");
            String name = sc.nextLine();
            System.out.print("Enter Password: ");
            String pass = sc.nextLine(); // Read password as a String

            // Set the parameters
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, pass); // Pass is now a String

            // Execute the insert statement
            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println("Inserted " + rowsAffected + " row(s) into the table.");

        } catch (SQLException e) {
            System.err.println("SQL Error: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        } finally {
            try {
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
                sc.close(); // Close the Scanner
            } catch (SQLException e) {
                System.err.println("Error closing resources: " + e.getMessage());
            }
        }
    }
}
