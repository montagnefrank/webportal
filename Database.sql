DROP TABLE IF EXISTS movement;
DROP TABLE IF EXISTS account;
DROP TABLE IF EXISTS customer;

CREATE TABLE customer (
    clientid BIGINT AUTO_INCREMENT PRIMARY KEY, 
    name VARCHAR(100) NOT NULL,                    
    gender VARCHAR(10),                            
    age INT,                                       
    identification VARCHAR(50) UNIQUE NOT NULL,    
    address VARCHAR(255),                          
    telephone VARCHAR(20),                         
    password VARCHAR(255) NOT NULL,               
    state VARCHAR(50),                             
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,  
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP  
);
 
CREATE TABLE account (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,         
    account_number VARCHAR(50) NOT NULL UNIQUE,    
    account_type VARCHAR(50) NOT NULL,             
    initial_balance DECIMAL(15, 2) NOT NULL,        
    status VARCHAR(50),                            
    customer_id BIGINT NOT NULL,                   
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,  
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP, 
    CONSTRAINT fk_customer FOREIGN KEY (customer_id) REFERENCES customer(clientid)
);
 
CREATE TABLE movement (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,          
    account_id BIGINT NOT NULL,       
    type VARCHAR(50) NOT NULL,                    
    amount DECIMAL(15, 2) NOT NULL,              
    balance DECIMAL(15, 2) NOT NULL,             
    date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,  
    CONSTRAINT fk_account FOREIGN KEY (account_id) REFERENCES account(id)  
);
 
INSERT INTO customer (name, gender, age, identification, address, telephone, password, state) VALUES
('Frank Montagne', 'Male', 30, 'ID12345', '1234 Elm St, Springfield, IL', '123-456-7890', 'password123', 'TRUE'),
('Jane Smith', 'Female', 28, 'ID67890', '5678 Oak St, Springfield, IL', '987-654-3210', 'securepassword', 'FALSE');
 
INSERT INTO account (account_number, account_type, initial_balance, status, customer_id) VALUES
('ACC12345', 'AHORRO', 1000.00, 'TRUE', 1),
('ACC12346', 'CORRIENTE', 1500.00, 'FALSE', 2);
 
INSERT INTO movement (account_id, type, amount, balance, date) VALUES
(1, 'D', 200.00, 1200.00, '2025-01-06 12:00:00'),
(1, 'C', 50.00, 1150.00, '2025-01-06 13:00:00'),
(2, 'D', 300.00, 1800.00, '2025-01-06 14:00:00');
