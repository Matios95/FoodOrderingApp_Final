CREATE TABLE food_ordering_app_user
(
    email     VARCHAR(32)   NOT NULL,
    password  VARCHAR(128)  NOT NULL,
    active    BOOLEAN       NOT NULL,
    PRIMARY KEY (email)
);

CREATE TABLE food_ordering_app_role
(
    role_id SERIAL      NOT NULL,
    role    VARCHAR(20) NOT NULL,
    PRIMARY KEY (role_id)
);

CREATE TABLE food_ordering_app_user_role
(
    email     VARCHAR(32)   NOT NULL,
    role_id   INT           NOT NULL,
    PRIMARY KEY (email, role_id),
    CONSTRAINT fk_food_ordering_app_user_role_user
        FOREIGN KEY (email)
            REFERENCES food_ordering_app_user (email),
    CONSTRAINT fk_food_ordering_app_user_role_role
        FOREIGN KEY (role_id)
            REFERENCES food_ordering_app_role (role_id)
);


insert into food_ordering_app_role (role_id, role) values (1, 'OWNER');
insert into food_ordering_app_role (role_id, role) values (2, 'CUSTOMER');