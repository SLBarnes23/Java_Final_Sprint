import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    // Database connection URL
    private static final String URL = "jdbc:postgresql://localhost:5432/E_Commerce";
    // Database username
    private static final String USER = "postgres";
    // Database password
    private static final String PASSWORD = "";

    // Static variable to hold the database connection
    private static Connection connection;

    /**
     * Gets a connection to the database.
     * 
     * @return A Connection object to the database.
     * @throws SQLException if a database access error occurs or the URL is null.
     */
    public static Connection getConnection() throws SQLException {
        // Check if the connection is null or closed
        if (connection == null || connection.isClosed()) {
            // Establish a new connection to the database
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        }
        // Return the established or existing connection
        return connection;
    }
}