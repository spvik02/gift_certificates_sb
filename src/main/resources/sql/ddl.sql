drop table if exists orders;
drop table if exists tag_in_certificate;
drop table if exists gift_certificate;
drop table if exists tag;
drop table if exists users;

create table if not exists users
(
    id   bigserial primary key,
    name varchar(255) not null
);

create table if not exists tag
(
    id   bigserial primary key,
    name varchar(255) not null unique
);

create table if not exists gift_certificate
(
    id               bigserial primary key,
    name             varchar(255)   not null unique,
    description      text,
    price            numeric(10, 2) not null,
    duration         int,
    create_date      TIMESTAMP,
    last_update_date TIMESTAMP
);

create table if not exists tag_in_certificate
(
    certificate_id bigint not null,
    tag_id         bigint not null,
    PRIMARY KEY (certificate_id, tag_id)
);

create table if not exists orders
(
    id                  bigserial primary key,
    user_id             bigint,
    gift_certificate_id bigint,
    price               numeric(10, 2) not null,
    order_date          TIMESTAMP,
    CONSTRAINT fk_user FOREIGN key (user_id) REFERENCES users (id),
    CONSTRAINT fk_gift_certificate FOREIGN key (gift_certificate_id) REFERENCES gift_certificate (id)
);