import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.mindrot.jbcrypt.BCrypt;

public class UserDAO {
    private Connection connection;

    public UserDAO() {
        try {
            this.connection = DBConnection.getConnection();
        } catch (SQLException e) {
            e.printStackTrace(); // Proper error handling: logs the exception stack trace
        }
    }

    public boolean registerUser(User user) {
        String query = "INSERT INTO Users (username, password, email, role) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, user.getUsername());

            // Hash the password before storing it
            String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
            stmt.setString(2, hashedPassword);

            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getRole());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace(); // Proper error handling: logs the exception stack trace
            return false;
        }
    }

    public User loginUser(String username, String password) {
        String query = "SELECT * FROM Users WHERE username = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("id");
                String storedPassword = rs.getString("password");
                String email = rs.getString("email");
                String role = rs.getString("role");

                // Verify the password
                if (BCrypt.checkpw(password, storedPassword)) {
                    // Create the correct type of User based on the role
                    switch (role) {
                        case "buyer":
                            return new Buyer(id, username, storedPassword, email, role);
                        case "seller":
                            return new Seller(id, username, storedPassword, email, role);
                        case "admin":
                            return new Admin(id, username, storedPassword, email, role);
                        default:
                            return new User(id, username, storedPassword, email, role);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Proper error handling: logs the exception stack trace
        }
        return null;
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM Users";
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                users.add(new User(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("email"),
                        rs.getString("role")));
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Proper error handling: logs the exception stack trace
        }
        return users;
    }

    public boolean deleteUser(int userId) {
        String query = "DELETE FROM Users WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, userId);
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace(); // Proper error handling: logs the exception stack trace
            return false;
        }
    }
}