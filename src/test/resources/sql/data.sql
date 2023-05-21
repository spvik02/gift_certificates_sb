insert into tag(id, name)
values (1, 'sport'),
       (2, 'nature'),
       (3, 'entertainments');

insert into users(id, name)
values (1, 'usagi'),
       (2, 'user');

insert into gift_certificate(id, name, description, price, duration, create_date, last_update_date)
values (1, 'pool', 'billiard', 7.45, 14, '2023-05-18 15:34:42.709394', '2023-05-18 15:34:42.709394'),
       (2, 'swimming pool', 'swim swim swim', 5.45, 7, '2023-05-18 15:34:42.709394', '2023-05-18 15:34:42.709394'),
       (3, 'cinema', 'fullmetal alchemist', 5.45, 3, '2023-05-18 15:34:42.709394', '2023-05-18 15:34:42.709394');

insert into tag_in_certificate(certificate_id, tag_id)
VALUES (1, 1),
       (1, 3),
       (2, 1),
       (2, 2),
       (3, 3);

insert into orders(id, user_id, gift_certificate_id, price)
values (1, 1, 1, 7.45),
       (2, 1, 3, 5.45),
       (3, 2, 2, 5.45);

select setval('tag_id_seq', (select(max(id)) from tag));
select setval('users_id_seq', (select(max(id)) from users));
select setval('gift_certificate_id_seq', (select(max(id)) from gift_certificate));
select setval('orders_id_seq', (select(max(id)) from orders));
