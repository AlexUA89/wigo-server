CREATE TABLE users (
    id          UUID PRIMARY KEY,
    name        varchar
);

CREATE TABLE statuses (
    id          UUID PRIMARY KEY,
    user_id     UUID REFERENCES users,
    latitude    decimal(9, 6),
    longitude   decimal(9, 6),
    name        varchar,
    text        varchar,
    start_date  timestamp,
    end_date    timestamp
);

CREATE TABLE messages (
    id          UUID PRIMARY KEY,
    status_id   UUID REFERENCES statuses,
    user_id     UUID REFERENCES users,
    text        varchar,
    created     timestamp
);
