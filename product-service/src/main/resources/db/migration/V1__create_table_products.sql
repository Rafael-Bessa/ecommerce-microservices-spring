CREATE TABLE products (
    products_id          BIGINT AUTO_INCREMENT,
    products_name        VARCHAR(100) NOT NULL,
    products_description VARCHAR(255) NOT NULL,
    products_price       DECIMAL(10, 2) NOT NULL,
    products_quantity    INT NOT NULL,
    CONSTRAINT pk_products PRIMARY KEY (products_id)
);