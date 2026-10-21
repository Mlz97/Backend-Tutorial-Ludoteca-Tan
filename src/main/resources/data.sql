INSERT INTO category(name)
VALUES ('Eurogames');
INSERT INTO category(name)
VALUES ('Ameritrash');
INSERT INTO category(name)
VALUES ('Familiar');

INSERT INTO author(name, nationality)
VALUES ('Alan R. Moon', 'US');
INSERT INTO author(name, nationality)
VALUES ('Vital Lacerda', 'PT');
INSERT INTO author(name, nationality)
VALUES ('Simone Luciani', 'IT');
INSERT INTO author(name, nationality)
VALUES ('Perepau Llistosella', 'ES');
INSERT INTO author(name, nationality)
VALUES ('Michael Kiesling', 'DE');
INSERT INTO author(name, nationality)
VALUES ('Phil Walker-Harding', 'US');

INSERT INTO game(title, age, category_id, author_id)
VALUES ('On Mars', '14', 1, 2);
INSERT INTO game(title, age, category_id, author_id)
VALUES ('Aventureros al tren', '8', 3, 1);
INSERT INTO game(title, age, category_id, author_id)
VALUES ('1920: Wall Street', '12', 1, 4);
INSERT INTO game(title, age, category_id, author_id)
VALUES ('Barrage', '14', 1, 3);
INSERT INTO game(title, age, category_id, author_id)
VALUES ('Los viajes de Marco Polo', '12', 1, 3);
INSERT INTO game(title, age, category_id, author_id)
VALUES ('Azul', '8', 3, 5);

INSERT INTO customer(name)
VALUES ('Manuel');
INSERT INTO customer(name)
VALUES ('Javier');
INSERT INTO customer(name)
VALUES ('Casandra');
INSERT INTO customer(name)
VALUES ('Lucía');
INSERT INTO customer(name)
VALUES ('Pedro');

INSERT INTO loan(customer_id, game_id, start_date, end_date)
VALUES (1, 6, '2026-10-07', '2026-10-10');
INSERT INTO loan(customer_id, game_id, start_date, end_date)
VALUES (2, 5, '2026-09-07', '2026-10-10');
INSERT INTO loan(customer_id, game_id, start_date, end_date)
VALUES (3, 4, '2026-10-12', '2026-10-15');
INSERT INTO loan(customer_id, game_id, start_date, end_date)
VALUES (4, 3, '2026-10-14', '2026-10-24');
INSERT INTO loan(customer_id, game_id, start_date, end_date)
VALUES (5, 6, '2026-11-07', '2026-11-10');
