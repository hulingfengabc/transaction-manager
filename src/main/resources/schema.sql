DROP TABLE transaction IF EXISTS;

CREATE TABLE transaction (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    type VARCHAR(20) NOT NULL,
    amount DECIMAL(15,2) NOT NULL CHECK (amount >= 0),
    description VARCHAR(255),
    date TIMESTAMP NOT NULL
);