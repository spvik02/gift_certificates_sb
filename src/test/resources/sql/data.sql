create table users
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

insert into tag(name)
values ('sport'),
       ('nature'),
       ('entertainments');

insert into users(name)
values ('usagi'),
       ('user');

insert into gift_certificate(name, description, price, duration, create_date, last_update_date)
values ('pool', 'billiard', 7.45, 14, '2023-05-18 15:34:42.709394', '2023-05-18 15:34:42.709394'),
       ('swimming pool', 'swim swim swim', 5.45, 7, '2023-05-18 15:34:42.709394', '2023-05-18 15:34:42.709394'),
       ('cinema', 'fullmetal alchemist', 5.45, 3, '2023-05-18 15:34:42.709394', '2023-05-18 15:34:42.709394');

insert into tag_in_certificate(certificate_id, tag_id)
VALUES (1, 1),
       (1, 3),
       (2, 1),
       (2, 2),
       (3, 3);

insert into orders(user_id, gift_certificate_id, price)
values (1, 1, 7.45),
       (1, 3, 5.45),
       (2, 2, 5.45);
