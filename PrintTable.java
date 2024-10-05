import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PrintTable {
    public static void main(String[] args) {
        String url = "jdbc:mysql://127.0.0.1:3306/sahayak";
        String user = "root"; 
        String password = "Luck0409@"; 
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = DriverManager.getConnection(url, user, password);
            statement = connection.createStatement();
            String selectSQL = "SELECT * FROM users";
            resultSet = statement.executeQuery(selectSQL);
            int columnCount = resultSet.getMetaData().getColumnCount();
            for (int i = 1; i <= columnCount; i++) {
                System.out.print(resultSet.getMetaData().getColumnName(i) + "\t");
            }
            System.out.println();
            while (resultSet.next()) {
                for (int i = 1; i <= columnCount; i++) {
                    System.out.print(resultSet.getString(i) + "\t");
                }
                System.out.println();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
