create schema if not exists characters;

create table characters.user (
    id bigserial primary key,
    name varchar(100) not null,
    password varchar(100) not null
);
