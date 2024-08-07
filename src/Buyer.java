// The Buyer class is a subclass of User, inheriting its properties and methods
public class Buyer extends User {

    // Constructor for the Buyer class
    // This constructor takes the same parameters as the User class constructor
    // and passes them to the superclass (User) constructor using the super() call
    public Buyer(int id, String username, String password, String email, String role) {
        super(id, username, password, email, role); // Call to the superclass (User) constructor
    }

    
}
