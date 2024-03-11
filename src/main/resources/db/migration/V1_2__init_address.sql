CREATE TABLE address
(
    address_id      SERIAL      NOT NULL,
    country         VARCHAR(32) NOT NULL,
    postcode        VARCHAR(32) NOT NULL,
    street          VARCHAR(32) NOT NULL,
    street_number   INT         NOT NULL,
    PRIMARY KEY (address_id)
)