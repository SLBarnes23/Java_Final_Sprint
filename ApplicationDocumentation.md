# Application Documentation Report

## 1. User Documentation

### 1.1. Overview

This application is a user and product management system designed for a marketplace scenario. It supports user roles such as buyers, sellers, and administrators, allowing for user registration, login, and access to role-specific functionalities.

### 1.2. Classes and Their Working

- **`Main`**: Entry point of the application, providing a console-based user interface for registration, login, and menu navigation. Handles user input and delegates functionality to other classes.

- **`User`**: Represents a user in the system with attributes like ID, username, password (hashed), email, and role. Includes methods for creating a user with a hashed password.

- **`UserService`**: Provides user-related operations such as registration, login, listing all users, and deleting users. Interacts with `UserDAO` for database operations.

- **`UserDAO`**: Data Access Object for user-related database operations. Manages saving, retrieving, updating, and deleting users from the database.

- **`Product`**: Represents a product in the marketplace with attributes such as ID, name, price, quantity, and seller ID.

- **`ProductService`**: Provides product-related operations such as adding, updating, deleting, and retrieving products. Interacts with `ProductDAO` for database operations.

- **`ProductDAO`**: Data Access Object for product-related database operations. Manages saving, retrieving, updating, and deleting products from the database.

- **`Buyer`, `Seller`, `Admin`**: Classes extending `User` to represent different user roles, providing role-specific functionalities.

### 1.3. Getting Started

1. **Running the Application**:
   - Compile the Java files using `javac`.
   - Run the `Main` class using `java`.

2. **Accessing the Application**:
   - The application runs in a console environment. Follow the on-screen prompts to register, login, and perform various operations.

### 1.4. Class Diagram

![Class Diagram](path/to/class_diagram.png) *(Replace with the actual path to your class diagram image)*

## 2. Development Documentation

### 2.1. Javadocs

The Javadocs provide detailed descriptions of the classes and methods in the project. They are generated from the source code comments using the `javadoc` tool.

### 2.2. Source Code Directory Structure
JAVA_FINAL_SPRINT
│
├──.vscode/
    │
    ├── Main.java
├──lib/
    │
    ├── jBCrypt-0.4.jar
    ├── postgressql.42.7.3.jar
│
├──src/   
    │
    ├── Main.java
    ├── Admin.java
    ├── Buyer.java
    ├── Seller.java
    ├── User.java
    ├── UserService.java
    ├── UserDAO.java
    ├── Product.java
    ├── ProductService.java
    ├── ProductDAO.java
    ├── DBConnection.java
    ├── TestDBConnection.java
│
├──bin/ 
    │
    ├── Main.class
    ├── Admin.class
    ├── Buyer.class
    ├── Seller.class
    ├── User.class
    ├── UserService.class
    ├── UserDOA.class
    ├── Product.class
    ├── ProductService.class
    ├── ProductDOA.class
    ├── DBConnection.class
    ├── TestDBConnection.class
│
├──ApplicationDocumentation.md
├──LICENSE
├──README.md
├──schema.sql

### 2.3. Build Process

1. **Compile the Project**:
   - Navigate to the `src` directory.
   - Run `javac *.java` to compile all Java files.

2. **Run the Application**:
   - Use `java Main` to start the application.

### 2.4. Compiler Time Dependencies

- `org.mindrot.jbcrypt.BCrypt`: For password hashing.
- `postgresql-42.7.3.jar`: For database connection.

### 2.5. Development Standards

- **Code Formatting**: Follow Java naming conventions and use consistent indentation.
- **Comments**: Use Javadoc comments for all public classes and methods.

### 2.6. Setting Up the Database for Development

1. **Database Schema**:
   - Create tables for `users` and `products`.
   - Define foreign key constraints as needed.

2. **Sample SQL Commands**:
   ```sql
   CREATE TABLE users (
       id SERIAL PRIMARY KEY,
       username VARCHAR(50) UNIQUE NOT NULL,
       password TEXT NOT NULL,
       email VARCHAR(100),
       role VARCHAR(20) NOT NULL
   );

   CREATE TABLE products (
       id SERIAL PRIMARY KEY,
       name VARCHAR(100) NOT NULL,
       price DECIMAL(10, 2) NOT NULL,
       quantity INT NOT NULL,
       seller_id INT REFERENCES users(id)
   );

## 2.7. Getting the Source Code

- **Repository URL**: [GitHub Repository URL](https://github.com/username/repository)
- **Clone the Repository**:
  ```bash
  git clone https://github.com/username/repository.git

# 3. Deployment Documentation

 ## 3.1. Installation Manual
 - Prerequisites
 - Ensure you have Java Development Kit (JDK) installed.
 - Set up a PostgreSQL or compatible database.

 ### Setting Up the Database
 Execute the Provided SQL Schema:

Use a database client or command line tool to execute the SQL commands to create the necessary tables.
 **Example SQL Commands**:
   ```sql
   CREATE TABLE users (
       id SERIAL PRIMARY KEY,
       username VARCHAR(50) UNIQUE NOT NULL,
       password TEXT NOT NULL,
       email VARCHAR(100),
       role VARCHAR(20) NOT NULL
   );

   CREATE TABLE products (
       id SERIAL PRIMARY KEY,
       name VARCHAR(100) NOT NULL,
       price DECIMAL(10, 2) NOT NULL,
       quantity INT NOT NULL,
       seller_id INT REFERENCES users(id)
   );
### Configure Database Connection Settings:
- Modify the DBConnection class to include the correct database URL, username, and password.
### Running the Application
- Navigate to the Project Directory:
- Use a terminal or command prompt to change to the project directory where the source files are located.

### Compile the Java Files:

- Run the following command to compile all Java files:
bash
`javac src/*.java`

### Run the Main Class:

- Start the application by running the Main class:
bash
`java src.Main`

## Configuration Files

### DBConnection Class:
- Ensure that the DBConnection class is configured with the correct database URL, username, and password.

### Troubleshooting
- Ensure Database Server is Running:

- Verify that the PostgreSQL server or compatible database is running and accessible.
Use TestDBConnection 
bash
`java src.TestDBConnection`

### Verify Database Schema:
- Check that the database schema matches the application's requirements and that all necessary tables and relationships are correctly set up.

