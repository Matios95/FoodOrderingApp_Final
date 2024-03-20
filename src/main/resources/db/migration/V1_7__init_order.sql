CREATE TABLE ordera
(
    ordera_id                   SERIAL                      NOT NULL,
    ordera_code                 VARCHAR(128)                 NOT NULL,
    product_id                  INT                         NOT NULL,
    quantity                    INT                         NOT NULL,
    food_ordering_request_id    INT                         NOT NULL,
    PRIMARY KEY (ordera_id),
    UNIQUE(ordera_code),
    CONSTRAINT fk_ordera_product
        FOREIGN KEY (product_id)
            REFERENCES product (product_id),
    CONSTRAINT fk_ordera_food_ordering_request
        FOREIGN KEY (food_ordering_request_id)
            REFERENCES food_ordering_request (food_ordering_request_id)
)