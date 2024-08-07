import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.mindrot.jbcrypt.BCrypt;

public class UserDAO {
    private Connection connection;

    // Constructor to initialize the database connection
    public UserDAO() {
        try {
            this.connection = DBConnection.getConnection();
        } catch (SQLException e) {
            System.err.println("Error establishing database connection: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Method to save a new user to the database
    public boolean save(User user) {
        String query = "INSERT INTO Users (username, password, email, role) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword()); // Ensure the password is hashed before saving
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getRole());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Error during user save: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    // Method to register a user, essentially an alias for the save method
    public boolean registerUser(User user) {
        return save(user); // Don't hash the password here; it should be done in UserService
    }

    // Method to log in a user by checking username and password
    public User loginUser(String username, String password) {
        User user = getUserByUsername(username);
        if (user != null) {
            System.out.println("User found: " + user.getUsername());

            // Trim the password and check if it matches the stored hashed password
            String trimmedPassword = password.trim();
            boolean passwordMatch = BCrypt.checkpw(trimmedPassword, user.getPassword());
            System.out.println("Password match: " + passwordMatch);

            if (passwordMatch) {
                // Return different user types based on the role
                switch (user.getRole()) {
                    case "buyer":
                        return new Buyer(user.getId(), user.getUsername(), user.getPassword(), user.getEmail(),
                                user.getRole());
                    case "seller":
                        return new Seller(user.getId(), user.getUsername(), user.getPassword(), user.getEmail(),
                                user.getRole());
                    case "admin":
                        return new Admin(user.getId(), user.getUsername(), user.getPassword(), user.getEmail(),
                                user.getRole());
                    default:
                        return user;
                }
            } else {
                System.out.println("Login failed. Please check your username and/or password.");
            }
        } else {
            System.out.println("User not found");
        }
        return null;
    }

    // Method to get a user by username
    public User getUserByUsername(String username) {
        String query = "SELECT * FROM Users WHERE username = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new User(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("email"),
                        rs.getString("role"));
            } else {
                System.err.println("User not found: " + username);
            }
        } catch (SQLException e) {
            System.err.println("Error during user retrieval: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    // Method to get all users from the database
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
            System.err.println("Error fetching all users: " + e.getMessage());
            e.printStackTrace();
        }
        return users;
    }

    // Method to delete a user and their products
    public boolean deleteUser(int userId) {
        try {
            // First, delete the user's products
            String deleteProductsQuery = "DELETE FROM products WHERE seller_id = ?";
            try (PreparedStatement stmt = connection.prepareStatement(deleteProductsQuery)) {
                stmt.setInt(1, userId);
                stmt.executeUpdate();
            }

            // Then, delete the user
            String deleteUserQuery = "DELETE FROM users WHERE id = ?";
            try (PreparedStatement stmt = connection.prepareStatement(deleteUserQuery)) {
                stmt.setInt(1, userId);
                int affectedRows = stmt.executeUpdate();
                return affectedRows > 0;
            }
        } catch (SQLException e) {
            System.err.println("Error deleting user: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}
