-- drop database twitterclone;
-- create database twitterclone;
-- use twitterclone;

drop table if exists users;
create table users (
    id int(31) PRIMARY KEY AUTO_INCREMENT NOT NULL,
    username VARCHAR(127) NOT NULL,
    password VARCHAR(127) NOT NULL,
    salt VARCHAR(31) NOT NULL
);