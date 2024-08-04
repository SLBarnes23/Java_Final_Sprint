import java.util.List;

public class UserService {
    private UserDAO userDAO;

    public UserService() {
        this.userDAO = new UserDAO();
    }

    public boolean registerUser(User user) {
        // Directly save the user, password is already hashed
        return userDAO.save(user);
    }

    public User loginUser(String username, String password) {
        return userDAO.loginUser(username, password); // Delegate to DAO
    }

    public List<User> getAllUsers() {
        return userDAO.getAllUsers();
    }

    public boolean deleteUser(int userId) {
        return userDAO.deleteUser(userId);
    }
}