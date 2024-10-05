import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;
public class InsertValues {
    public static void main(String[] args) {
        String url = "jdbc:mysql://127.0.0.1:3306/sahayak"; 
        String user = "root";
        String password = "Luck0409@";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Scanner sc=new Scanner(System.in);
        try {
            connection = DriverManager.getConnection(url, user, password);
            String insertSQL = "INSERT INTO users (username, password) VALUES (?, ?)";
            preparedStatement = connection.prepareStatement(insertSQL);
            System.out.println("Enter Name: ");
            String name=sc.nextLine();
            System.out.println("Enter Password: ");
            int pass=sc.nextInt();
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2,pass); 
            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println("Inserted " + rowsAffected + " row(s) into the table.");

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
