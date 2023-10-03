CREATE TABLE category
(
    id   BINARY(16) NOT NULL,
    name VARCHAR(255) NULL,
    CONSTRAINT pk_category PRIMARY KEY (id)
);

CREATE TABLE price
(
    id       BINARY(16) NOT NULL,
    currency VARCHAR(255) NULL,
    price DOUBLE NOT NULL,
    CONSTRAINT pk_price PRIMARY KEY (id)
);

CREATE TABLE product
(
    id            BINARY(16) NOT NULL,
    title         VARCHAR(255) NULL,
    `description` VARCHAR(255) NULL,
    image         VARCHAR(255) NULL,
    category      BINARY(16) NULL,
    price_id      BINARY(16) NULL,
    CONSTRAINT pk_product PRIMARY KEY (id)
);

ALTER TABLE product
    ADD CONSTRAINT FK_PRODUCT_ON_CATEGORY FOREIGN KEY (category) REFERENCES category (id);

ALTER TABLE product
    ADD CONSTRAINT FK_PRODUCT_ON_PRICE FOREIGN KEY (price_id) REFERENCES price (id);