-- Create the Users table
CREATE TABLE Users (
    id SERIAL PRIMARY KEY,               -- Unique identifier for each user, auto-incremented
    username VARCHAR(50) UNIQUE NOT NULL,-- Username, must be unique and not null
    password VARCHAR(255) NOT NULL,      -- Password, stored as a hashed value, not null
    email VARCHAR(100) UNIQUE NOT NULL,  -- Email, must be unique and not null
    role VARCHAR(20) NOT NULL            -- Role of the user (e.g., admin, buyer, seller), not null
);

-- Create the Products table
CREATE TABLE Products (
    id SERIAL PRIMARY KEY,               -- Unique identifier for each product, auto-incremented
    name VARCHAR(100) NOT NULL,          -- Name of the product, not null
    price NUMERIC(10, 2) NOT NULL,       -- Price of the product with two decimal places, not null
    quantity INT NOT NULL,               -- Quantity of the product available, not null
    seller_id INT NOT NULL,              -- Foreign key referencing the seller's id from the Users table, not null
    FOREIGN KEY (seller_id) REFERENCES Users(id) -- Define the foreign key constraint
);
