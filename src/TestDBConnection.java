import java.sql.Connection;
import java.sql.SQLException;

public class TestDBConnection {
    public static void main(String[] args) {
        try {
            // Attempt to establish a connection to the database
            Connection connection = DBConnection.getConnection();

            // Check if the connection was successful
            if (connection != null) {
                System.out.println("Connection successful!"); // Inform the user that the connection was successful
            } else {
                System.out.println("Failed to make connection!"); // Inform the user that the connection failed
            }
        } catch (SQLException e) {
            // Handle any SQL exceptions that occur during the connection attempt
            System.err.println("Error connecting to the database: " + e.getMessage());
            e.printStackTrace(); // Log the exception stack trace for debugging purposes
        }
    }
}