CREATE database mall;

CREATE TABLE product
(
    product_id         INT          NOT NULL PRIMARY KEY AUTO_INCREMENT, # id 自動增加
    product_name       VARCHAR(128) NOT NULL, # String 商品名稱 必填
        category           VARCHAR(32)  NOT NULL, # String 商品分類 必填
        image_url          VARCHAR(256) NOT NULL, # String 圖片連結 必填
        price              INT          NOT NULL, # int    商品價錢 必填
        stock              INT          NOT NULL, # int    商品庫存 必填
        description        VARCHAR(1024),         # String 商品描述 選填
        created_date       TIMESTAMP    NOT NULL, # timeStamp 上架時間 必填
        last_modified_date TIMESTAMP    NOT NULL  # timeStamp 最後更新時間 必填
);

INSERT INTO product (product_name, category, image_url, price, stock, description, created_date, last_modified_date) VALUES ('蘋果', 'FOOD', 'https://cdn.pixabay.com/photo/2014/02/01/17/28/apple-256261__480.jpg', 20, 10, '這是來自澳洲的蘋果！', '2022-03-01 02:41:28', '2022-03-01 02:41:32');
