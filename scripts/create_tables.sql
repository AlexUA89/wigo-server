CREATE TABLE users (
    id          UUID PRIMARY KEY,
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
    start_date  timestamp,
    end_date    timestamp
);

CREATE TABLE messages (
    id          UUID PRIMARY KEY,
    status_id   UUID NOT NULL REFERENCES statuses,
    user_id     UUID NOT NULL REFERENCES users,
    text        varchar NOT NULL,
    created     timestamp NOT NULL
);
