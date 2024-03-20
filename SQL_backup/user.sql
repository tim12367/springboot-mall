-- User
CREATE TABLE user
(
    user_id            INT          NOT NULL PRIMARY KEY AUTO_INCREMENT,
    email              VARCHAR(256) NOT NULL UNIQUE KEY, -- email 唯一 256
    password           VARCHAR(256) NOT NULL,            -- password 256
    created_date       TIMESTAMP    NOT NULL,
    last_modified_date TIMESTAMP    NOT NULL
);