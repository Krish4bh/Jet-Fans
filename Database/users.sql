CREATE TABLE users (
	user_id INT PRIMARY KEY AUTO_INCREMENT, 
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL, 
    password VARCHAR(255) NOT NULL, 
    contact_details VARCHAR(25) NOT NULL
);

INSERT INTO users(name, email, password, contact_details)
VALUES ("user", "user@mail.com", "123456789", "999999999");


SELECT * FROM users;