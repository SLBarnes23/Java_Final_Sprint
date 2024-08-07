public class Product {
    // Fields to store product information
    private int id; // Unique identifier for the product
    private String name; // Name of the product
    private double price; // Price of the product
    private int quantity; // Quantity available in stock
    private int sellerId; // ID of the seller who owns this product

    // Constructor to initialize a new Product object
    public Product(int id, String name, double price, int quantity, int sellerId) {
        this.id = id; // Set the product ID
        this.name = name; // Set the product name
        this.price = price; // Set the product price
        this.quantity = quantity; // Set the product quantity
        this.sellerId = sellerId; // Set the seller ID
    }

    // Getter for product ID
    public int getId() {
        return id;
    }

    // Setter for product ID
    public void setId(int id) {
        this.id = id;
    }

    // Getter for product name
    public String getName() {
        return name;
    }

    // Setter for product name
    public void setName(String name) {
        this.name = name;
    }

    // Getter for product price
    public double getPrice() {
        return price;
    }

    // Setter for product price
    public void setPrice(double price) {
        this.price = price;
    }

    // Getter for product quantity
    public int getQuantity() {
        return quantity;
    }

    // Setter for product quantity
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    // Getter for seller ID
    public int getSellerId() {
        return sellerId;
    }

    // Setter for seller ID
    public void setSellerId(int sellerId) {
        this.sellerId = sellerId;
    }
}