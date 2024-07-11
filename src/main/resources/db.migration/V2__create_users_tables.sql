CREATE TABLE wallets (
                         id uuid PRIMARY KEY,
                         amount BIGINT NOT NULL,
                         updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP

);
CREATE TABLE users (
                       id UUID PRIMARY KEY,
                       wallet_id UUID UNIQUE,
                       last_name VARCHAR(50) NOT NULL CHECK (last_name ~ '^[А-ЯЁ][а-яё]+$'),
                       first_name VARCHAR(50) NOT NULL CHECK (first_name ~ '^[А-ЯЁ][а-яё]+$'),
                       middle_name VARCHAR(50) CHECK (middle_name ~ '^[А-ЯЁ][а-яё]+$'),
                       mobile_phone CHAR(11) NOT NULL UNIQUE CHECK (mobile_phone ~ '^7\d{10}$'),
                       email VARCHAR(255) NOT NULL UNIQUE CHECK (email ~ '^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}$'),
                       birth_date DATE NOT NULL,
                       password VARCHAR(255) NOT NULL,
                       created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
                       updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
                       FOREIGN KEY (wallet_id) REFERENCES wallets (id)
);
