CREATE TABLE IF NOT EXISTS users
(
    id       BIGSERIAL PRIMARY KEY,
    username VARCHAR(255) UNIQUE NOT NULL
);

-- Создаём таблицу продуктов клиента.
CREATE TABLE products (
                          id BIGSERIAL PRIMARY KEY,
                          account_number VARCHAR(64) NOT NULL,
                          balance NUMERIC(19,2) NOT NULL,
                          product_type VARCHAR(16) NOT NULL,
                          user_id BIGINT NOT NULL
);

-- Внешний ключ на пользователей (предполагается, что таблица users уже существует).
ALTER TABLE products
    ADD CONSTRAINT fk_products_users
        FOREIGN KEY (user_id) REFERENCES users(id);

-- Уникальность номера счёта/карты (при необходимости можно заменить на составной уникальный ключ (user_id, account_number)).
ALTER TABLE products
    ADD CONSTRAINT uk_products_account_number UNIQUE (account_number);

-- Ограничиваем значения типа продукта.
ALTER TABLE products
    ADD CONSTRAINT chk_products_type CHECK (product_type IN ('ACCOUNT','CARD'));

-- Индекс для быстрых выборок по user_id.
CREATE INDEX idx_products_user_id ON products(user_id);