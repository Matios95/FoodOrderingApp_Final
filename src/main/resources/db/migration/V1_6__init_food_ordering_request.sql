CREATE TABLE food_ordering_request
(
    food_ordering_request_id    SERIAL                      NOT NULL,
    food_ordering_request_code  INT                         NOT NULL,
    datetime                    TIMESTAMP WITH TIME ZONE    NOT NULL,
    customer_id                 INT                         NOT NULL,
    PRIMARY KEY (food_ordering_request_id),
    UNIQUE(food_ordering_request_code),
    CONSTRAINT fk_food_ordering_request_customer
        FOREIGN KEY (customer_id)
            REFERENCES customer (customer_id)
)