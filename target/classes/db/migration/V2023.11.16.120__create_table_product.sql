CREATE TABLE if not exists Product
(
    id               BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    name             VARCHAR(255)                            NOT NULL,
    description      VARCHAR(255),
    category_id      BIGINT,
    price            DECIMAL                                 NOT NULL,
    image_url         VARCHAR(255),
    available_in_stock INTEGER                                 NOT NULL,
    latitude         DECIMAL(9, 6),
    longitude        DECIMAL(9, 6),
    CONSTRAINT pk_product PRIMARY KEY (id)
);

CREATE TABLE if not exists Product_images
(
    Product_id BIGINT NOT NULL,
    images_id  BIGINT NOT NULL
);

ALTER TABLE Product_images
    ADD CONSTRAINT uc_product_images_images UNIQUE (images_id);

ALTER TABLE Product
    ADD CONSTRAINT FK_PRODUCT_ON_CATEGORY FOREIGN KEY (category_id) REFERENCES Category (id);

ALTER TABLE Product_images
    ADD CONSTRAINT fk_proima_on_image FOREIGN KEY (images_id) REFERENCES Image (id);

ALTER TABLE Product_images
    ADD CONSTRAINT fk_proima_on_product FOREIGN KEY (Product_id) REFERENCES Product (id);

ALTER TABLE Image
    ADD CONSTRAINT FK_IMAGE_ON_PRODUCT FOREIGN KEY (product_id) REFERENCES Product (id);
