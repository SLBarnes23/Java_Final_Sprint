import java.util.List;

public class UserService {
    // Private field for UserDAO instance
    private UserDAO userDAO;

    // Constructor initializes the UserDAO instance
    public UserService() {
        this.userDAO = new UserDAO();
    }

    // Method to register a user by saving it through the UserDAO
    public boolean registerUser(User user) {
        // Directly save the user, password is already hashed
        return userDAO.save(user);
    }

    // Method to log in a user by delegating to the UserDAO
    public User loginUser(String username, String password) {
        return userDAO.loginUser(username, password); // Delegate to DAO
    }

    // Method to get all users by delegating to the UserDAO
    public List<User> getAllUsers() {
        return userDAO.getAllUsers();
    }

    // Method to delete a user by delegating to the UserDAO
    public boolean deleteUser(int userId) {
        return userDAO.deleteUser(userId);
    }
}
