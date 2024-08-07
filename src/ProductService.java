import java.util.List;

public class ProductService {
    private ProductDAO productDAO; // DAO object to interact with the database

    // Constructor to initialize the ProductService and ProductDAO objects
    public ProductService() {
        this.productDAO = new ProductDAO();
    }

    // Method to add a new product by delegating to ProductDAO
    public boolean addProduct(Product product) {
        return productDAO.addProduct(product); // Call DAO method and return the result
    }

    // Method to update an existing product by delegating to ProductDAO
    public boolean updateProduct(Product product) {
        return productDAO.updateProduct(product); // Call DAO method and return the result
    }

    // Method to delete a product by delegating to ProductDAO
    public boolean deleteProduct(int productId, int sellerId) {
        return productDAO.deleteProduct(productId, sellerId); // Call DAO method and return the result
    }

    // Method to retrieve products by a specific seller by delegating to ProductDAO
    public List<Product> getProductsBySeller(int sellerId) {
        return productDAO.getProductsBySeller(sellerId); // Call DAO method and return the list of products
    }

    // Method to retrieve all products by delegating to ProductDAO
    public List<Product> getAllProducts() {
        return productDAO.getAllProducts(); // Call DAO method and return the list of all products
    }

    // New method to search for products by name by delegating to ProductDAO
    public List<Product> searchProductsByName(String name) {
        return productDAO.searchProductsByName(name); // Call DAO method and return the list of matching products
    }
}