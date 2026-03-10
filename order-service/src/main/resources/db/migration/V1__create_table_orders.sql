CREATE TABLE orders (
    order_id          BIGINT IDENTITY(1,1),
    order_product_id  BIGINT        NOT NULL,
    order_quantity    INT           NOT NULL,
    order_status      VARCHAR(20)   NOT NULL,
    order_total_price DECIMAL(10,2) NOT NULL,
    order_created_at  DATETIME2     NOT NULL DEFAULT GETDATE(),
    CONSTRAINT pk_orders PRIMARY KEY (order_id)
);
