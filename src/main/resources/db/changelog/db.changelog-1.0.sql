--liquibase formatted sql

--changeset anton_narizhny:1
CREATE TABLE IF NOT EXISTS city
(
    id BIGSERIAL PRIMARY KEY ,
    name VARCHAR(64) NOT NULL UNIQUE
);

--changeset anton_narizhny:2
CREATE TABLE IF NOT EXISTS information
(
    id BIGSERIAL PRIMARY KEY ,
    text VARCHAR(128) NOT NULL UNIQUE ,
    city_id BIGINT REFERENCES city(id)
);