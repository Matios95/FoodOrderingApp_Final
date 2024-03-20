CREATE TABLE owner
(
    owner_id    SERIAL      NOT NULL,
    email       VARCHAR(32) NOT NULL,
    name        VARCHAR(32) NOT NULL,
    surname     VARCHAR(32) NOT NULL,
    PRIMARY KEY (owner_id),
    UNIQUE (email),
    CONSTRAINT fk_user_owner
        FOREIGN KEY (email)
            REFERENCES food_ordering_app_user (email)
);