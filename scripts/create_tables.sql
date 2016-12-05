alter user postgres password '1';

CREATE TABLE users (
    id          UUID PRIMARY KEY,
    nickname    varchar NOT NULL,
    name        varchar NOT NULL,
    email       varchar UNIQUE NOT NULL
);

CREATE TABLE statuses (
    id          UUID PRIMARY KEY,
    user_id     UUID NOT NULL REFERENCES users,
    latitude    decimal(9, 6) NOT NULL,
    longitude   decimal(9, 6) NOT NULL,
    name        varchar NOT NULL,
    text        varchar NOT NULL,
    url         varchar,
    start_date  timestamp,
    end_date    timestamp,
    kind        varchar NOT NULL check (kind in ('event', 'chat')),
    category    varchar NOT NULL
);

create table images (
    url         varchar PRIMARY KEY,
    user_id     UUID not null REFERENCES users
);

create table status_images (
    status_id   UUID not null REFERENCES statuses,
    url         varchar not null REFERENCES images
);

CREATE INDEX statuses_search ON statuses USING gin(to_tsvector('english', name || '\n'|| text));

CREATE TABLE status_hashtags (
    status_id   UUID NOT NULL REFERENCES statuses,
    hashtag     varchar not null
);

CREATE TABLE messages (
    id          UUID PRIMARY KEY,
    status_id   UUID NOT NULL REFERENCES statuses,
    user_id     UUID NOT NULL REFERENCES users,
    text        varchar NOT NULL,
    created     timestamp NOT NULL
);

insert into users(id, nickname, name, email) values
('f908854b-93f5-48bc-9213-7abcb1169d48', 'admin', 'admin', 'admin@wigo.com');
