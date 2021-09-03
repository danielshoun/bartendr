CREATE SEQUENCE hibernate_sequence;

CREATE TABLE users(
    id SERIAL PRIMARY KEY NOT NULL,
    email VARCHAR(256) UNIQUE NOT NULL,
    password VARCHAR(60) NOT NULL,
    enabled BOOLEAN NOT NULL,
    first_name VARCHAR(32) NOT NULL,
    last_name VARCHAR(32) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL
);

CREATE TABLE email_ver_tokens(
    id SERIAL PRIMARY KEY NOT NULL,
    user_id INT UNIQUE NOT NULL,
    token VARCHAR(36) UNIQUE NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    CONSTRAINT fk_user_id FOREIGN KEY (user_id) REFERENCES users(id)
)