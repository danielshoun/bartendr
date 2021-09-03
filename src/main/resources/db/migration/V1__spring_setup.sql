CREATE SEQUENCE hibernate_sequence;

CREATE TABLE users(
    id SERIAL PRIMARY KEY NOT NULL,
    email VARCHAR(256) NOT NULL,
    password VARCHAR(60) NOT NULL,
    enabled BOOLEAN NOT NULL,
    first_name VARCHAR(32) NOT NULL,
    last_name VARCHAR(32) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL
);