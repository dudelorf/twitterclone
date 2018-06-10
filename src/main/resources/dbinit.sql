drop database if exists twitterclone;
create database twitterclone;
use twitterclone;

drop table if exists users;
create table users (
    id int PRIMARY KEY AUTO_INCREMENT NOT NULL,
    firstname VARCHAR(128) NOT NULL,
    lastname VARCHAR(128) NOT NULL,
    username VARCHAR(128) NOT NULL,
    email VARCHAR(128) NOT NULL,
    password VARCHAR(128) NOT NULL,
    salt VARCHAR(64) NOT NULL,
    token VARCHAR(64),
    tokenExpiration DATETIME
);
ALTER TABLE users ADD INDEX username (username);
ALTER TABLE users ADD INDEX email (email);
ALTER TABLE users ADD INDEX token (token);

drop table if exists posts;
create table posts (
    id int PRIMARY KEY AUTO_INCREMENT NOT NULL,
    userId int NOT NULL,
    username VARCHAR(128) NOT NULL,
    postBody TEXT,
    postDate DATETIME
);
ALTER TABLE posts ADD INDEX userId (userId);
ALTER TABLE posts ADD INDEX postDate (postDate);

drop table if exists subscriptions;
create table subscriptions (
    id int PRIMARY KEY AUTO_INCREMENT NOT NULL,
    subscriberId int NOT NULL,
    posterId int NOT NULL
);
ALTER TABLE subscriptions ADD INDEX subscriberId (subscriberId);