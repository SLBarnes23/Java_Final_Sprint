import org.mindrot.jbcrypt.BCrypt;

public class User {
    private int id;
    private String username;
    private String password;
    private String email;
    private String role;

    // Public constructor
    public User(int id, String username, String password, String email, String role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
    }

    // Static factory method for creating a new user with a hashed password
    public static User createUser(int id, String username, String password, String email, String role) {
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        return new User(id, username, hashedPassword, email, role);
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}