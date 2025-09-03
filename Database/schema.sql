CREATE TABLE users (
	user_id INT PRIMARY KEY AUTO_INCREMENT, 
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL, 
    password VARCHAR(255) NOT NULL, 
    contact_details VARCHAR(25) NOT NULL
);

CREATE TABLE products (
	products_id INT AUTO_INCREMENT PRIMARY KEY, 
    product_name VARCHAR(150) NOT NULL, 
    price DECIMAL(10, 2) NOT NULL, 
    description VARCHAR(1000), 
    user_id INT, 
    FOREIGN KEY(user_id) REFERENCES users(user_id)
);


SELECT * FROM users;