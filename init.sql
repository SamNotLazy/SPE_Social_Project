-- init.sql example
CREATE TABLE IF NOT EXISTS my_table (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL
);
INSERT INTO my_table (name) VALUES ('Sample Data');
