import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {
    private Connection connection; // Connection to the database

    // Constructor to initialize the ProductDAO object and establish a database
    // connection
    public ProductDAO() {
        try {
            // Obtain a connection from the DBConnection utility class
            this.connection = DBConnection.getConnection();
        } catch (SQLException e) {
            // Handle potential errors during connection establishment
            System.err.println("Error establishing database connection: " + e.getMessage());
            e.printStackTrace(); // Print the stack trace for debugging
        }
    }

    // Method to add a new product to the database
    public boolean addProduct(Product product) {
        String query = "INSERT INTO Products (name, price, quantity, seller_id) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            // Set the parameters for the prepared statement
            stmt.setString(1, product.getName());
            stmt.setDouble(2, product.getPrice());
            stmt.setInt(3, product.getQuantity());
            stmt.setInt(4, product.getSellerId());
            // Execute the update and return true if successful
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            // Handle potential errors during the insertion
            System.err.println("Error adding product: " + e.getMessage());
            e.printStackTrace(); // Print the stack trace for debugging
            return false;
        }
    }

    // Method to update an existing product in the database
    public boolean updateProduct(Product product) {
        String query = "UPDATE Products SET name = ?, price = ?, quantity = ? WHERE id = ? AND seller_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            // Set the parameters for the prepared statement
            stmt.setString(1, product.getName());
            stmt.setDouble(2, product.getPrice());
            stmt.setInt(3, product.getQuantity());
            stmt.setInt(4, product.getId());
            stmt.setInt(5, product.getSellerId());
            // Execute the update and return true if successful
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            // Handle potential errors during the update
            System.err.println("Error updating product: " + e.getMessage());
            e.printStackTrace(); // Print the stack trace for debugging
            return false;
        }
    }

    // Method to delete a product from the database
    public boolean deleteProduct(int productId, int sellerId) {
        String query = "DELETE FROM Products WHERE id = ? AND seller_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            // Set the parameters for the prepared statement
            stmt.setInt(1, productId);
            stmt.setInt(2, sellerId);
            // Execute the update and return true if successful
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            // Handle potential errors during the deletion
            System.err.println("Error deleting product: " + e.getMessage());
            e.printStackTrace(); // Print the stack trace for debugging
            return false;
        }
    }

    // Method to retrieve products belonging to a specific seller
    public List<Product> getProductsBySeller(int sellerId) {
        List<Product> products = new ArrayList<>();
        String query = "SELECT * FROM Products WHERE seller_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            // Set the parameter for the prepared statement
            stmt.setInt(1, sellerId);
            // Execute the query and process the result set
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                // Create and add Product objects to the list
                products.add(new Product(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getDouble("price"),
                        rs.getInt("quantity"),
                        rs.getInt("seller_id")));
            }
        } catch (SQLException e) {
            // Handle potential errors during the retrieval
            System.err.println("Error fetching products by seller: " + e.getMessage());
            e.printStackTrace(); // Print the stack trace for debugging
        }
        return products; // Return the list of products
    }

    // Method to retrieve all products from the database
    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        String query = "SELECT * FROM Products";
        try (Statement stmt = connection.createStatement()) {
            // Execute the query and process the result set
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                // Create and add Product objects to the list
                products.add(new Product(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getDouble("price"),
                        rs.getInt("quantity"),
                        rs.getInt("seller_id")));
            }
        } catch (SQLException e) {
            // Handle potential errors during the retrieval
            System.err.println("Error fetching all products: " + e.getMessage());
            e.printStackTrace(); // Print the stack trace for debugging
        }
        return products; // Return the list of products
    }

    // Method to search for products by name (case-insensitive)
    public List<Product> searchProductsByName(String name) {
        List<Product> products = new ArrayList<>();
        String query = "SELECT * FROM Products WHERE name ILIKE ?"; // ILIKE for case-insensitive search
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            // Set the parameter for the prepared statement
            stmt.setString(1, "%" + name + "%");
            // Execute the query and process the result set
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                // Create and add Product objects to the list
                products.add(new Product(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getDouble("price"),
                        rs.getInt("quantity"),
                        rs.getInt("seller_id")));
            }
        } catch (SQLException e) {
            // Handle potential errors during the search
            System.err.println("Error searching for products: " + e.getMessage());
            e.printStackTrace(); // Print the stack trace for debugging
        }
        return products; // Return the list of products
    }
}