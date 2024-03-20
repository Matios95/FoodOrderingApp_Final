 CREATE TABLE product
 (
     product_id     SERIAL          NOT NULL,
     product_code   VARCHAR(128)     NOT NULL,
     type           VARCHAR(32)     NOT NULL,
     name           VARCHAR(32)     NOT NULL,
     description    VARCHAR(128)     NOT NULL,
     price          NUMERIC(19, 2)  NOT NULL,
     place_id       INT             NOT NULL,
     image          bytea,
     PRIMARY KEY (product_id),
     UNIQUE (product_code),
     CONSTRAINT fk_product_place
             FOREIGN KEY (place_id)
                 REFERENCES place (place_id)
 )