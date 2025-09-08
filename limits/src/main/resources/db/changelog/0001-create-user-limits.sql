--liquibase formatted sql
--changeset you:0001-create-user-limits
CREATE TABLE IF NOT EXISTS user_limits (
  user_id BIGINT PRIMARY KEY,
  day_limit NUMERIC(19,2) NOT NULL,
  remaining NUMERIC(19,2) NOT NULL,
  last_reset_date DATE NOT NULL,
  CONSTRAINT chk_day_limit_nonneg CHECK (day_limit >= 0),
  CONSTRAINT chk_remaining_nonneg CHECK (remaining >= 0)
);
INSERT INTO user_limits(user_id, day_limit, remaining, last_reset_date)
SELECT i, 10000.00, 10000.00, CURRENT_DATE FROM generate_series(1,100) g(i)
ON CONFLICT (user_id) DO NOTHING;
