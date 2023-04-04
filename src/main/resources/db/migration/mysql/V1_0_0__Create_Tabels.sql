CREATE TABLE IF NOT EXISTS users
(
    id              INT PRIMARY KEY AUTO_INCREMENT,
    first_name      VARCHAR(255) NOT NULL,
    last_name       VARCHAR(255) NOT NULL,
    username        VARCHAR(255) NOT NULL UNIQUE,
    email           VARCHAR(255),
    password        VARCHAR(255) NOT NULL,
    phone_number    VARCHAR(255) NOT NULL,
    address         VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS restaurants
(
    id              INT PRIMARY KEY AUTO_INCREMENT,
    name            VARCHAR(255) NOT NULL,
    phone_number    VARCHAR(255) NOT NULL,
    address         VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS menus
(
    id              INT PRIMARY KEY AUTO_INCREMENT,
    name            VARCHAR(255) NOT NULL,
    description     VARCHAR(255) NOT NULL,
    price           INT          NOT NULL
);

CREATE TABLE IF NOT EXISTS orders
(
    id                  INT PRIMARY KEY AUTO_INCREMENT,
    total_amount        INT NOT NULL,
    delivery_address    VARCHAR(255) NOT NULL,
    status              VARCHAR(255) NOT NULL,
    created             BIGINT NOT NULL,
    user_id             INT NOT NULL,
    restaurant_id       INT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users (id),
    FOREIGN KEY (restaurant_id) REFERENCES restaurants (id)
);

CREATE TABLE IF NOT EXISTS order_items
(
    id          INT PRIMARY KEY AUTO_INCREMENT,
    quantity    INT    NOT NULL,
    price       INT    NOT NULL,
    order_id    INT    NOT NULL,
    FOREIGN KEY (order_id) REFERENCES orders (id)
);

CREATE TABLE IF NOT EXISTS restaurant_menu
(
    restaurant_id   INT NOT NULL,
    menu_id         INT NOT NULL,
    FOREIGN KEY (restaurant_id) REFERENCES restaurants (id),
    FOREIGN KEY (menu_id) REFERENCES menus (id)
);

CREATE TABLE IF NOT EXISTS order_items_menu
(
    order_items_id      INT NOT NULL,
    menu_id             INT NOT NULL,
    FOREIGN KEY (order_items_id) REFERENCES order_items (id),
    FOREIGN KEY (menu_id) REFERENCES menus (id)
);


