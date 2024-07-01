create database eazybank;
use eazybank;

-- JdbcUserDetailsManager 를 이용하기 위한 Spring Security 기준 db 스키마

CREATE TABLE users
(
    id Int NOT Null AUTO_INCREMENT,
    username VARCHAR(45) NOT NULL,
    password VARCHAR(45) NOT NULL,
    enabled Int Not NULL,
    primary key (id)
);

CREATE TABLE authorities
(
    id Int NOT Null AUTO_INCREMENT,
    username  VARCHAR(45) NOT NULL,
    authority VARCHAR(45) NOT NULL,
    primary key (id)
);

INSERT IGNORE INTO users VALUES (NULL, 'happy', '1234', 1);
INSERT IGNORE INTO authorities VALUES (NULL, 'happy', 'write');