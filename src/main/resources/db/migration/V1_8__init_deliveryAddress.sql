CREATE TABLE delivery_address
(
    delivery_address_id          SERIAL                      NOT NULL,
    place_id                    INT                         NOT NULL,
    postcode                    VARCHAR(32)                 NOT NULL,
    street                      VARCHAR(32)                 NOT NULL,
    PRIMARY KEY (delivery_address_id),
    CONSTRAINT fk_deliveryAddress_place
        FOREIGN KEY (place_id)
            REFERENCES place (place_id)
)