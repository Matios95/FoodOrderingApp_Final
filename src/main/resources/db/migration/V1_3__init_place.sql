CREATE TABLE place
(
    place_id    SERIAL      NOT NULL,
    phone       VARCHAR(32) NOT NULL,
    name        VARCHAR(32) NOT NULL,
    owner_id    INT         NOT NULL,
    address_id  INT         NOT NULL,
    PRIMARY KEY (place_id),
    UNIQUE(phone),
    CONSTRAINT fk_place_owner
        FOREIGN KEY (owner_id)
            REFERENCES owner (owner_id),
    CONSTRAINT fk_place_address
        FOREIGN KEY (address_id)
            REFERENCES address (address_id)
)