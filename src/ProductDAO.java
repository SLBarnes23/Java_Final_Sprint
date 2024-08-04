import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {
    private Connection connection;

    public ProductDAO() {
        try {
            this.connection = DBConnection.getConnection();
        } catch (SQLException e) {
            System.err.println("Error establishing database connection: " + e.getMessage());
            e.printStackTrace(); // Proper error handling: logs the exception stack trace
        }
    }

    public boolean addProduct(Product product) {
        String query = "INSERT INTO Products (name, price, quantity, seller_id) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, product.getName());
            stmt.setDouble(2, product.getPrice());
            stmt.setInt(3, product.getQuantity());
            stmt.setInt(4, product.getSellerId());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Error adding product: " + e.getMessage());
            e.printStackTrace(); // Proper error handling: logs the exception stack trace
            return false;
        }
    }

    public boolean updateProduct(Product product) {
        String query = "UPDATE Products SET name = ?, price = ?, quantity = ? WHERE id = ? AND seller_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, product.getName());
            stmt.setDouble(2, product.getPrice());
            stmt.setInt(3, product.getQuantity());
            stmt.setInt(4, product.getId());
            stmt.setInt(5, product.getSellerId());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Error updating product: " + e.getMessage());
            e.printStackTrace(); // Proper error handling: logs the exception stack trace
            return false;
        }
    }

    public boolean deleteProduct(int productId, int sellerId) {
        String query = "DELETE FROM Products WHERE id = ? AND seller_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, productId);
            stmt.setInt(2, sellerId);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Error deleting product: " + e.getMessage());
            e.printStackTrace(); // Proper error handling: logs the exception stack trace
            return false;
        }
    }

    public List<Product> getProductsBySeller(int sellerId) {
        List<Product> products = new ArrayList<>();
        String query = "SELECT * FROM Products WHERE seller_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, sellerId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                products.add(new Product(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getDouble("price"),
                        rs.getInt("quantity"),
                        rs.getInt("seller_id")));
            }
        } catch (SQLException e) {
            System.err.println("Error fetching products by seller: " + e.getMessage());
            e.printStackTrace(); // Proper error handling: logs the exception stack trace
        }
        return products;
    }

    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        String query = "SELECT * FROM Products";
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                products.add(new Product(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getDouble("price"),
                        rs.getInt("quantity"),
                        rs.getInt("seller_id")));
            }
        } catch (SQLException e) {
            System.err.println("Error fetching all products: " + e.getMessage());
            e.printStackTrace(); // Proper error handling: logs the exception stack trace
        }
        return products;
    }

    // New method for searching products by name
    public List<Product> searchProductsByName(String name) {
        List<Product> products = new ArrayList<>();
        String query = "SELECT * FROM Products WHERE name ILIKE ?"; // Using ILIKE for case-insensitive search
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, "%" + name + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                products.add(new Product(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getDouble("price"),
                        rs.getInt("quantity"),
                        rs.getInt("seller_id")));
            }
        } catch (SQLException e) {
            System.err.println("Error searching for products: " + e.getMessage());
            e.printStackTrace(); // Proper error handling: logs the exception stack trace
        }
        return products;
    }
}