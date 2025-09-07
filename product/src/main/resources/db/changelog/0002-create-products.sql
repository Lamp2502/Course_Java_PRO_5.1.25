CREATE TABLE IF NOT EXISTS users
(
    id       BIGSERIAL PRIMARY KEY,
    username VARCHAR(255) UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS products (
    id BIGSERIAL PRIMARY KEY,
    account_number VARCHAR(64) NOT NULL,
    balance NUMERIC(19,2) NOT NULL,
    product_type VARCHAR(16) NOT NULL,
    user_id BIGINT NOT NULL
);

ALTER TABLE products
    ADD CONSTRAINT fk_products_users
    FOREIGN KEY (user_id) REFERENCES users(id);

ALTER TABLE products
    ADD CONSTRAINT uk_products_account_number UNIQUE (account_number);

DO $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM pg_constraint WHERE conname = 'chk_products_type') THEN
        ALTER TABLE products
            ADD CONSTRAINT chk_products_type CHECK (product_type IN ('ACCOUNT','CARD'));
    END IF;
END$$;

CREATE INDEX IF NOT EXISTS idx_products_user_id ON products(user_id);

--rollback DROP INDEX IF EXISTS idx_products_user_id;
--rollback ALTER TABLE products DROP CONSTRAINT IF EXISTS chk_products_type;
--rollback ALTER TABLE products DROP CONSTRAINT IF EXISTS uk_products_account_number;
--rollback ALTER TABLE products DROP CONSTRAINT IF EXISTS fk_products_users;
--rollback DROP TABLE IF EXISTS products;
