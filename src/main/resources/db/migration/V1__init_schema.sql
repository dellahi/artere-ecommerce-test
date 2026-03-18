CREATE TABLE categories
(
    id          BIGSERIAL    NOT NULL PRIMARY KEY,
    name        VARCHAR(255) NOT NULL,
    description TEXT,
    parent_id   BIGINT,
    CONSTRAINT fk_category_parent FOREIGN KEY (parent_id) REFERENCES categories (id) ON DELETE CASCADE
);

CREATE TABLE products
(
    id          BIGSERIAL      NOT NULL PRIMARY KEY,
    name        VARCHAR(255)   NOT NULL,
    price       DOUBLE PRECISION NOT NULL CHECK (price >= 0),
    quantity    INTEGER        NOT NULL CHECK (quantity >= 0),
    category_id BIGINT,
    CONSTRAINT fk_product_category FOREIGN KEY (category_id) REFERENCES categories (id) ON DELETE SET NULL
);

CREATE INDEX idx_categories_parent_id ON categories (parent_id);
CREATE INDEX idx_products_category_id ON products (category_id);

