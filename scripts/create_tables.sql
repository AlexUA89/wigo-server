CREATE TABLE statuses (
    id          UUID PRIMARY KEY,
    latitude    decimal(9, 6),
    longitude   decimal(9, 6),
    name        varchar,
    text        varchar,
    start_date  timestamp,
    end_date    timestamp
);

CREATE TABLE messages (
    id          UUID PRIMARY KEY,
    status_id   UUID,
    user_id     UUID,
    text        varchar,
    created     timestamp
);

CREATE TABLE users (
    id          UUID PRIMARY KEY,
    name        varchar
);
