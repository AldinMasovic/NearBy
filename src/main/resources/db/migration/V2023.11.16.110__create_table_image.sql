CREATE TABLE if not exists Image
(
    id         BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    image_url   VARCHAR(255)                            NOT NULL,
    product_id BIGINT,
    CONSTRAINT pk_image PRIMARY KEY (id)
);
