import org.mindrot.jbcrypt.BCrypt;

public class User {
    // Private fields for user details
    private int id;
    private String username;
    private String password;
    private String email;
    private String role;

    // Public constructor to initialize a User object with given parameters
    public User(int id, String username, String password, String email, String role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
    }

    // Static factory method for creating a new user with a hashed password
    public static User createUser(int id, String username, String password, String email, String role) {
        // Hash the provided password using BCrypt
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        // Return a new User object with the hashed password
        return new User(id, username, hashedPassword, email, role);
    }

    // Getter for id
    public int getId() {
        return id;
    }

    // Setter for id
    public void setId(int id) {
        this.id = id;
    }

    // Getter for username
    public String getUsername() {
        return username;
    }

    // Setter for username
    public void setUsername(String username) {
        this.username = username;
    }

    // Getter for password
    public String getPassword() {
        return password;
    }

    // Setter for password
    public void setPassword(String password) {
        this.password = password;
    }

    // Getter for email
    public String getEmail() {
        return email;
    }

    // Setter for email
    public void setEmail(String email) {
        this.email = email;
    }

    // Getter for role
    public String getRole() {
        return role;
    }

    // Setter for role
    public void setRole(String role) {
        this.role = role;
    }
}
