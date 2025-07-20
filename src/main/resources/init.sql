DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS orders;
DROP TABLE IF EXISTS products;
DROP TABLE IF EXISTS customers;

-- Customers table
CREATE TABLE customers (
    id INT PRIMARY KEY,
    name VARCHAR(100),
    email VARCHAR(100),
    city VARCHAR(50)
);

-- Products table
CREATE TABLE products (
    id INT PRIMARY KEY,
    name VARCHAR(100),
    price DECIMAL(8,2)
);

-- Orders table
CREATE TABLE orders (
    id INT PRIMARY KEY,
    customer_id INT,
    product_id INT,
    quantity INT,
    order_date DATE,
    FOREIGN KEY (customer_id) REFERENCES customers(id),
    FOREIGN KEY (product_id) REFERENCES products(id)
);

-- Insert sample customers
INSERT INTO customers VALUES
(1, 'John Smith', 'john@email.com', 'New York'),
(2, 'Jane Doe', 'jane@email.com', 'Los Angeles'),
(3, 'Bob Wilson', 'bob@email.com', 'Chicago'),
(4, 'Alice Brown', 'alice@email.com', 'Houston'),
(5, 'Charlie Davis', 'charlie@email.com', 'Phoenix');

-- Insert sample products
INSERT INTO products VALUES
(1, 'Laptop', 999.99),
(2, 'Mouse', 25.99),
(3, 'Keyboard', 75.99),
(4, 'Monitor', 299.99),
(5, 'Headphones', 149.99);

-- Insert sample orders
INSERT INTO orders VALUES
(1, 1, 1, 1, '2024-01-15'),  -- John bought 1 Laptop
(2, 2, 2, 2, '2024-01-16'),  -- Jane bought 2 Mouse
(3, 1, 3, 1, '2024-01-17'),  -- John bought 1 Keyboard
(4, 3, 1, 1, '2024-01-18'),  -- Bob bought 1 Laptop
(5, 4, 4, 1, '2024-01-19'),  -- Alice bought 1 Monitor
(6, 2, 5, 1, '2024-01-20'),  -- Jane bought 1 Headphones
(7, 5, 2, 3, '2024-01-21'),  -- Charlie bought 3 Mouse
(8, 1, 4, 1, '2024-01-22'),  -- John bought 1 Monitor
(9, 3, 3, 1, '2024-01-23'),  -- Bob bought 1 Keyboard
(10, 4, 2, 1, '2024-01-24'); -- Alice bought 1 Mouse