create schema if not exists characters;

create table characters.character (
    id bigserial primary key,
    name varchar(100) not null,
    birth_name varchar(100) not null,
    city varchar(50) not null,
    habilities varchar[] not null
);
