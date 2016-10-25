insert into users(id, nickname, name, email) values
('be5bc4c4-d1dc-49b6-baa9-57dc21635f58', 'jonny', 'John', 'john@wigo.com');
insert into statuses(id, user_id, latitude, longitude, name, text, start_date, end_date, kind_id) values
('bc6cbd1c-dcd9-447f-8831-f59d88c883fa', 'be5bc4c4-d1dc-49b6-baa9-57dc21635f58', 12.345, 67.89, 'salsa open air', 'good place!!!', '2016-09-15T01:34:19.399Z', '2016-09-15T01:34:19.399Z', 'event');
insert into status_hashtags(status_id, hashtag) values
('bc6cbd1c-dcd9-447f-8831-f59d88c883fa', 'salsa'),
('bc6cbd1c-dcd9-447f-8831-f59d88c883fa', 'dancing');
insert into messages(id, status_id, user_id, text, created) values
('82447ef1-d8b9-47b6-8704-5608b6e4c30e', 'bc6cbd1c-dcd9-447f-8831-f59d88c883fa', 'be5bc4c4-d1dc-49b6-baa9-57dc21635f58', 'Hi!!!', '2016-09-15T02:16:12.751Z');