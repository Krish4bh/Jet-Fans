CREATE TABLE users (
	user_id INT PRIMARY KEY AUTO_INCREMENT, 
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL, 
    password VARCHAR(255) NOT NULL, 
    contact_details VARCHAR(25) NOT NULL
);


CREATE TABLE products (
	product_id INT AUTO_INCREMENT PRIMARY KEY, 
    product_name VARCHAR(150) NOT NULL, 
    price DECIMAL(10, 2) NOT NULL, 
    description VARCHAR(1000), 
    user_id INT, 
    FOREIGN KEY(user_id) REFERENCES users(user_id)
);


CREATE TABLE cart (
	cart_id INT AUTO_INCREMENT PRIMARY KEY,
    total_price DECIMAL(10, 2) NOT NULL, 
    product_names VARCHAR(200) NOT NULL, 
    user_id INT,
    FOREIGN KEY(user_id) REFERENCES users(user_id)
);


CREATE TABLE orders (
	order_id INT AUTO_INCREMENT PRIMARY KEY,
    order_date DATETIME DEFAULT NOW() NOT NULL, 
    order_price DECIMAL(10, 2) NOT NULL, 
    quantity INT NOT NULL, 
    shipping_address VARCHAR(255) NOT NULL, 
    user_id INT, 
    product_id INT,
    FOREIGN KEY(user_id) REFERENCES users(user_id),
    FOREIGN KEY(product_id) REFERENCES products(product_id)
);


CREATE TABLE addresses (
	address_id INT AUTO_INCREMENT PRIMARY KEY, 
    permanent_address VARCHAR(250) NOT NULL, 
    pincode INT NOT NULL, 
    state VARCHAR(20) NOT NULL, 
    city VARCHAR(20) NOT NULL, 
    landline INT, 
    user_id INT,
    FOREIGN KEY(user_id) REFERENCES users(user_id)
);


CREATE TABLE payments (
	payment_id INT AUTO_INCREMENT PRIMARY KEY, 
    transaction_id VARCHAR(50) UNIQUE, 
    amount DECIMAL(10, 2) NOT NULL, 
    payment_date DATETIME DEFAULT NOW() NOT NULL, 
    payment_method VARCHAR(50) NOT NULL,
    user_id INT,
    FOREIGN KEY(user_id) REFERENCES users(user_id)
);


CREATE TABLE feedbacks (
	feedback_id INT AUTO_INCREMENT PRIMARY KEY, 
    rating INT, 
    comment TEXT, 
    user_id INT, 
    product_id INT, 
    FOREIGN KEY(user_id) REFERENCES users(user_id), 
    FOREIGN KEY(product_id) REFERENCES products(product_id), 
    CHECK (rating BETWEEN 1 AND 5)
);
