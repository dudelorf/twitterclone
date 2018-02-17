-- drop database twitterclone;
-- create database twitterclone;
-- use twitterclone;

drop table if exists users;
create table users (
    id int PRIMARY KEY AUTO_INCREMENT NOT NULL,
    username VARCHAR(128) NOT NULL,
    password VARCHAR(128) NOT NULL,
    salt VARCHAR(64) NOT NULL,
    token VARCHAR(64),
    token_expiration DATETIME
);

drop table if exists posts;
create table posts (
    id int PRIMARY KEY AUTO_INCREMENT NOT NULL,
    user_id int NOT NULL,
    post_body TEXT,
    post_date DATETIME
);
ALTER TABLE posts ADD INDEX post_date (post_date);