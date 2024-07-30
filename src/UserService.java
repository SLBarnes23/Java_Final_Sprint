import java.util.List;

public class UserService {
    private UserDAO userDAO;

    public UserService() {
        this.userDAO = new UserDAO();
    }

    public boolean registerUser(User user) {
        return userDAO.registerUser(user);
    }

    public User loginUser(String username, String password) {
        return userDAO.loginUser(username, password);
    }

    public List<User> getAllUsers() {
        return userDAO.getAllUsers();
    }
}
