import java.util.Scanner;
import java.util.List;

public class Main {
    private static UserService userService = new UserService();
    private static ProductService productService = new ProductService();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    register(scanner);
                    break;
                case 2:
                    login(scanner);
                    break;
                case 3:
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void register(Scanner scanner) {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        System.out.print("Enter email: ");
        String email = scanner.nextLine();
        System.out.print("Enter role (buyer/seller/admin): ");
        String role = scanner.nextLine();

        User user = new User(0, username, password, email, role);
        if (userService.registerUser(user)) {
            System.out.println("Registration successful!");
        } else {
            System.out.println("Registration failed.");
        }
    }

    private static void login(Scanner scanner) {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        User user = userService.loginUser(username, password);
        if (user != null) {
            System.out.println("Login successful! Welcome, " + user.getUsername());
            navigateRoleMenu(scanner, user);
        } else {
            System.out.println("Login failed.");
        }
    }

    private static void navigateRoleMenu(Scanner scanner, User user) {
        switch (user.getRole()) {
            case "buyer":
                showBuyerMenu(scanner, (Buyer) user);
                break;
            case "seller":
                showSellerMenu(scanner, (Seller) user);
                break;
            case "admin":
                showAdminMenu(scanner, (Admin) user);
                break;
            default:
                System.out.println("Unknown role.");
        }
    }

    private static void showBuyerMenu(Scanner scanner, Buyer buyer) {
        // Implement buyer-specific menu and actions
        System.out.println("Buyer menu:");
        // Example options
        System.out.println("1. View Products");
        System.out.println("2. Logout");
        System.out.print("Choose an option: ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        switch (choice) {
            case 1:
                viewProducts();
                break;
            case 2:
                return; // Logout and return to main menu
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }

    private static void showSellerMenu(Scanner scanner, Seller seller) {
        while (true) {
            System.out.println("1. Add Product");
            System.out.println("2. Update Product");
            System.out.println("3. Delete Product");
            System.out.println("4. View My Products");
            System.out.println("5. Logout");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    addProduct(scanner, seller);
                    break;
                case 2:
                    updateProduct(scanner, seller);
                    break;
                case 3:
                    deleteProduct(scanner, seller);
                    break;
                case 4:
                    viewMyProducts(seller);
                    break;
                case 5:
                    return; // Logout and return to main menu
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void addProduct(Scanner scanner, Seller seller) {
        System.out.print("Enter product name: ");
        String name = scanner.nextLine();
        System.out.print("Enter product price: ");
        double price = scanner.nextDouble();
        System.out.print("Enter product quantity: ");
        int quantity = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        Product product = new Product(0, name, price, quantity, seller.getId());
        if (productService.addProduct(product)) {
            System.out.println("Product added successfully!");
        } else {
            System.out.println("Failed to add product.");
        }
    }

    private static void updateProduct(Scanner scanner, Seller seller) {
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

        Product product = new Product(productId, name, price, quantity, seller.getId());
        if (productService.updateProduct(product)) {
            System.out.println("Product updated successfully!");
        } else {
            System.out.println("Failed to update product.");
        }
    }

    private static void deleteProduct(Scanner scanner, Seller seller) {
        System.out.print("Enter product ID to delete: ");
        int productId = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        if (productService.deleteProduct(productId, seller.getId())) {
            System.out.println("Product deleted successfully!");
        } else {
            System.out.println("Failed to delete product.");
        }
    }

    private static void viewMyProducts(Seller seller) {
        List<Product> products = productService.getProductsBySeller(seller.getId());
        if (products.isEmpty()) {
            System.out.println("No products found.");
        } else {
            for (Product product : products) {
                System.out.println(product.getId() + ": " + product.getName() + " - $" + product.getPrice() + " - "
                        + product.getQuantity() + " units");
            }
        }
    }

    private static void showAdminMenu(Scanner scanner, Admin admin) {
        while (true) {
            System.out.println("1. View All Users");
            System.out.println("2. Delete User");
            System.out.println("3. View All Products");
            System.out.println("4. Logout");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    viewAllUsers();
                    break;
                case 2:
                    deleteUser(scanner);
                    break;
                case 3:
                    viewAllProducts();
                    break;
                case 4:
                    return; // Logout and return to main menu
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void viewAllUsers() {
        List<User> users = userService.getAllUsers();
        if (users.isEmpty()) {
            System.out.println("No users found.");
        } else {
            for (User user : users) {
                System.out.println(
                        user.getId() + ": " + user.getUsername() + " - " + user.getEmail() + " - " + user.getRole());
            }
        }
    }

    private static void deleteUser(Scanner scanner) {
        System.out.print("Enter user ID to delete: ");
        int userId = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        if (userService.deleteUser(userId)) {
            System.out.println("User deleted successfully!");
        } else {
            System.out.println("Failed to delete user.");
        }
    }

    private static void viewAllProducts() {
        List<Product> products = productService.getAllProducts();
        if (products.isEmpty()) {
            System.out.println("No products found.");
        } else {
            for (Product product : products) {
                System.out.println(product.getId() + ": " + product.getName() + " - $" + product.getPrice() + " - "
                        + product.getQuantity() + " units - Seller ID: " + product.getSellerId());
            }
        }
    }

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
}