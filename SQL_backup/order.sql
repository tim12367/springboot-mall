CREATE TABLE `order`
(
    order_id           INT       NOT NULL PRIMARY KEY AUTO_INCREMENT,
    user_id            INT       NOT NULL,
    total_amount       INT       NOT NULL, -- 訂單總花費
    created_date       TIMESTAMP NOT NULL,
    last_modified_date TIMESTAMP NOT NULL
);

CREATE TABLE order_item
(
    order_item_id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    order_id      INT NOT NULL,
    product_id    INT NOT NULL,
    quantity      INT NOT NULL, -- 商品數量
    amount        INT NOT NULL  -- 商品花費
);

INSERT INTO `order` (user_id, total_amount, created_date, last_modified_date) VALUES (6, 100110, '2022-06-02 16:51:49', '2022-06-02 16:51:49');
INSERT INTO order_item (order_id, product_id, quantity, amount) VALUES (1, 4, 2, 60);
INSERT INTO order_item (order_id, product_id, quantity, amount) VALUES (1, 6, 5, 50);
INSERT INTO order_item (order_id, product_id, quantity, amount) VALUES (1, 7, 1, 100000);

SELECT * FROM order_item LEFT JOIN product ON order_item.product_id = product.product_id;