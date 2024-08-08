import java.util.Scanner;
import java.util.List;

public class Main {
    // Initialize services for user and product operations
    private static UserService userService = new UserService();
    private static ProductService productService = new ProductService();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Main loop to display the main menu and handle user input
        while (true) {
            // Display menu options
            System.out.println("");
            System.out.println("===== Welcome to the E-Commerce Application =====");
            System.out.println("");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.println("");
            System.out.println("=================================================");
            System.out.println("");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            // Handle user choice
            switch (choice) {
                case 1:
                    register(scanner); // Handle user registration
                    break;
                case 2:
                    login(scanner); // Handle user login
                    break;
                case 3:
                    System.exit(0); // Exit the application
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    // Method to handle user registration
    private static void register(Scanner scanner) {
        try {
            // Prompt for user details
            System.out.println("");
            System.out.println("================= Register User =================");
            System.out.println("");
            System.out.print("Enter username: ");
            String username = scanner.nextLine();
            System.out.print("Enter password: ");
            String password = scanner.nextLine();
            System.out.print("Enter email: ");
            String email = scanner.nextLine();
            System.out.print("Enter role (buyer/seller/admin): ");
            String roleInput = scanner.nextLine().trim().toLowerCase();
            System.out.println("");
            System.out.println("=================================================");
            System.out.println("");
            // Validate user role
            String role;
            switch (roleInput) {
                case "buyer":
                case "seller":
                case "admin":
                    role = roleInput;
                    break;
                default:
                    System.out.println("Invalid role. Please enter 'buyer', 'seller', or 'admin'.");
                    System.out.println("");
                    return;
            }

            // Create a user with hashed password using a factory method
            User user = User.createUser(0, username, password, email, role);
            if (userService.registerUser(user)) {
                System.out.println("Registration successful!");
                System.out.println("");
            } else {
                System.out.println("Registration failed.");
                System.out.println("");
            }
        } catch (Exception e) {
            // Handle registration errors
            System.err.println("Error during registration: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Method to handle user login
    private static void login(Scanner scanner) {
        // Prompt for login details
        System.out.println("");
        System.out.println("================== User Login ===================");
        System.out.println("");
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        System.out.println("");
        System.out.println("=================================================");
        System.out.println("");
        // Attempt to log in and retrieve user
        User user = userService.loginUser(username, password);
        if (user != null) {
            System.out.println("Login successful! Welcome, " + user.getUsername());
            System.out.println("");
            navigateRoleMenu(scanner, user); // Navigate to role-specific menu
        } else {
            System.out.println("Login failed. Please check your username and/or password.");
            System.out.println("");
        }
    }

    // Method to navigate to role-specific menu based on user's role
    private static void navigateRoleMenu(Scanner scanner, User user) {
        switch (user.getRole()) {
            case "buyer":
                if (user instanceof Buyer) {
                    showBuyerMenu(scanner, (Buyer) user);
                } else {
                    System.out.println("User is not a buyer.");
                }
                break;
            case "seller":
                if (user instanceof Seller) {
                    showSellerMenu(scanner, (Seller) user);
                } else {
                    System.out.println("User is not a seller.");
                }
                break;
            case "admin":
                if (user instanceof Admin) {
                    showAdminMenu(scanner, (Admin) user);
                } else {
                    System.out.println("User is not an admin.");
                }
                break;
            default:
                System.out.println("Unknown role.");
        }
    }

    // Method to display the menu and handle buyer-specific operations
    private static void showBuyerMenu(Scanner scanner, Buyer buyer) {
        while (true) {
            // Display buyer menu options
            System.out.println("");
            System.out.println("=================== Buyer Menu: =================");
            System.out.println("");
            System.out.println("1. View All Products");
            System.out.println("2. Search Products by Name");
            System.out.println("3. Logout");
            System.out.println("");
            System.out.println("=================================================");
            System.out.println("");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            // Handle buyer menu choices
            switch (choice) {
                case 1:
                    System.out.println("");
                    System.out.println("================== All Products: ================");
                    System.out.println("");
                    viewProducts(); // View all products
                    break;
                case 2:
                    System.out.println("");
                    System.out.println("================ Product Search: ================");
                    System.out.println("");
                    searchProductsByName(scanner); // Search products by name
                    System.out.println("");
                    break;
                case 3:
                    return; // Logout and return to main menu
                default:
                    System.out.println("Invalid choice. Please try again.");
                    System.out.println("");
            }
        }
    }

    // Method to display the menu and handle seller-specific operations
    private static void showSellerMenu(Scanner scanner, Seller seller) {
        while (true) {
            // Display seller menu options
            System.out.println("");
            System.out.println("================== Seller Menu: =================");
            System.out.println("");
            System.out.println("1. Add Product");
            System.out.println("2. Update Product");
            System.out.println("3. Delete Product");
            System.out.println("4. View My Products");
            System.out.println("5. Logout");
            System.out.println("");
            System.out.println("=================================================");
            System.out.println("");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            // Handle seller menu choices
            switch (choice) {
                case 1:
                    addProduct(scanner, seller); // Add a new product
                    break;
                case 2:
                    updateProduct(scanner, seller); // Update an existing product
                    break;
                case 3:
                    deleteProduct(scanner, seller); // Delete a product
                    break;
                case 4:
                    viewMyProducts(seller); // View products added by the seller
                    break;
                case 5:
                    return; // Logout and return to main menu
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    // Method to add a new product
    private static void addProduct(Scanner scanner, Seller seller) {
        // Prompt for product details
        System.out.println("");
        System.out.println("================== Add Product: =================");
        System.out.println("");
        System.out.print("Enter product name: ");
        String name = scanner.nextLine();
        System.out.print("Enter product price: ");
        double price = scanner.nextDouble();
        System.out.print("Enter product quantity: ");
        int quantity = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        System.out.println("");
        System.out.println("=================================================");
        System.out.println("");

        // Create and add a new product
        Product product = new Product(0, name, price, quantity, seller.getId());
        if (productService.addProduct(product)) {
            System.out.println("Product added successfully!");
        } else {
            System.out.println("Failed to add product.");
        }
    }

    // Method to update an existing product
    private static void updateProduct(Scanner scanner, Seller seller) {
        // Prompt for product details
        System.out.println("");
        System.out.println("================= Update Product: ===============");
        System.out.println("");
        System.out.print("Enter product ID to update: ");
        int productId = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        System.out.print("Enter new product name: ");
        String name = scanner.nextLine();
        System.out.print("Enter new product price: ");
        double price = scanner.nextDouble();
        System.out.print("Enter new product quantity: ");
        int quantity = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        System.out.println("");
        System.out.println("=================================================");
        System.out.println("");

        // Create and update the product
        Product product = new Product(productId, name, price, quantity, seller.getId());
        if (productService.updateProduct(product)) {
            System.out.println("Product updated successfully!");
        } else {
            System.out.println("Failed to update product.");
        }
    }

    // Method to delete a product
    private static void deleteProduct(Scanner scanner, Seller seller) {
        System.out.println("");
        System.out.println("================= Delete Product: ===============");
        System.out.println("");
        System.out.print("Enter product ID to delete: ");
        int productId = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        // Attempt to delete the product
        if (productService.deleteProduct(productId, seller.getId())) {
            System.out.println("");
            System.out.println("=================================================");
            System.out.println("");
            System.out.println("Product deleted successfully!");

        } else {
            System.out.println("");
            System.out.println("=================================================");
            System.out.println("");
            System.out.println("Failed to delete product.");
        }
    }

    // Method to view products added by the seller
    private static void viewMyProducts(Seller seller) {
        List<Product> products = productService.getProductsBySeller(seller.getId());
        if (products.isEmpty()) {
            System.out.println("");
            System.out.println("=================================================");
            System.out.println("");
            System.out.println("No products found.");

        } else {
            System.out.println("");
            System.out.println("================ View My Products: ==============");
            System.out.println("");
            for (Product product : products) {
                System.out.println(product.getId() + ": " + product.getName() + " - $" + product.getPrice() + " - "
                        + product.getQuantity() + " units");
            }
        }
    }

    // Method to display the menu and handle admin-specific operations
    private static void showAdminMenu(Scanner scanner, Admin admin) {
        while (true) {
            // Display admin menu options
            System.out.println("");
            System.out.println("================== Admin Menu: ==================");
            System.out.println("");
            System.out.println("1. View All Users");
            System.out.println("2. Delete User");
            System.out.println("3. View All Products");
            System.out.println("4. Logout");
            System.out.println("");
            System.out.println("=================================================");
            System.out.println("");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            // Handle admin menu choices
            switch (choice) {
                case 1:
                    viewAllUsers(); // View all registered users
                    break;
                case 2:
                    deleteUser(scanner); // Delete a user
                    break;
                case 3:
                    viewAllProducts(); // View all products
                    break;
                case 4:
                    return; // Logout and return to main menu
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    // Method to view all registered users
    private static void viewAllUsers() {
        List<User> users = userService.getAllUsers();
        if (users.isEmpty()) {
            System.out.println("No users found.");
        } else {
            System.out.println("");
            System.out.println("=================== All Users: ==================");
            System.out.println("");
            for (User user : users) {
                System.out.println(
                        user.getId() + ": " + user.getUsername() + " - " + user.getEmail() + " - " + user.getRole());
            }
        }
    }

    // Method to delete a user
    private static void deleteUser(Scanner scanner) {
        System.out.println("");
        System.out.println("================== Delete User: =================");
        System.out.println("");
        System.out.print("Enter user ID to delete: ");
        int userId = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        // Attempt to delete the user
        if (userService.deleteUser(userId)) {
            System.out.println("");
            System.out.println("=================================================");
            System.out.println("");
            System.out.println("User deleted successfully!");
        } else {
            System.out.println("");
            System.out.println("=================================================");
            System.out.println("");
            System.out.println("Failed to delete user.");
        }
    }

    // Method to view all products
    private static void viewAllProducts() {
        List<Product> products = productService.getAllProducts();
        if (products.isEmpty()) {
            System.out.println("No products found.");
        } else {
            System.out.println("");
            System.out.println("=============== View All Products: ==============");
            System.out.println("");
            for (Product product : products) {
                System.out.println(product.getId() + ": " + product.getName() + " - $" + product.getPrice() + " - "
                        + product.getQuantity() + " units - Seller ID: " + product.getSellerId());
            }
        }
    }

    // Method to view all products (for buyers)
    private static void viewProducts() {
        // Implement buyer-specific view products functionality
        List<Product> products = productService.getAllProducts(); // Assuming buyers can view all products
        if (products.isEmpty()) {
            System.out.println("No products found.");
        } else {
            for (Product product : products) {
                System.out.println(product.getId() + ": " + product.getName() + " - $" + product.getPrice() + " - "
                        + product.getQuantity() + " units - Seller ID: " + product.getSellerId());
            }
        }
    }

    // Method to search products by name
    private static void searchProductsByName(Scanner scanner) {
        System.out.print("Enter product name to search: ");
        String name = scanner.nextLine();
        List<Product> products = productService.searchProductsByName(name);
        if (products.isEmpty()) {
            System.out.println("No products found with the name \"" + name + "\".");
        } else {
            System.out.println("");
            System.out.println("================ Search Results: ================");
            System.out.println("");
            for (Product product : products) {
                System.out.println(product.getId() + ": " + product.getName() + " - $" + product.getPrice() + " - "
                        + product.getQuantity() + " units - Seller ID: " + product.getSellerId());
            }
        }
    }
}