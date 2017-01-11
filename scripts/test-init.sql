insert into statuses(id, user_id, latitude, longitude, name, text, url, start_date, end_date, category) values
('bc6cbd1c-dcd9-447f-8831-f59d88c883fa', 'f908854b-93f5-48bc-9213-7abcb1169d48', 12.345, 67.89, 'salsa open air', 'good place!!!', 'https://www.facebook.com/LatinDanceOpenAir/', '2016-09-15T01:34:19.399Z', '2016-09-15T01:34:19.399Z', 'LECTURE');
insert into status_images(status_id, url) values
('bc6cbd1c-dcd9-447f-8831-f59d88c883fa', 'http://images.freeimages.com/images/previews/682/curve-ahead-3-1384097.jpg');
insert into status_hashtags(status_id, hashtag) values
('bc6cbd1c-dcd9-447f-8831-f59d88c883fa', 'salsa'),
('bc6cbd1c-dcd9-447f-8831-f59d88c883fa', 'dancing');
insert into messages(id, status_id, user_id, text, created) values
('82447ef1-d8b9-47b6-8704-5608b6e4c30e', 'bc6cbd1c-dcd9-447f-8831-f59d88c883fa', 'f908854b-93f5-48bc-9213-7abcb1169d48', 'Hi!!!', '2016-09-15T02:16:12.751Z');