-- GENRES
CREATE TABLE genres (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255)
);

-- THEATERS
CREATE TABLE theaters (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255),
    location VARCHAR(255)
);

-- HALLS
CREATE TABLE halls (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255),
    theater_id BIGINT REFERENCES theaters(id)
);

-- SEATS
CREATE TABLE seats (
    id BIGSERIAL PRIMARY KEY,
    row_number INT,
    seat_number INT,
    hall_id BIGINT REFERENCES halls(id)
);

-- MOVIES
CREATE TABLE movies (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(255),
    duration VARCHAR(255),
    release_date DATE,
    rating NUMERIC(4,2),
    genre_id BIGINT REFERENCES genres(id)
);

-- SHOWS
CREATE TABLE shows (
    id BIGSERIAL PRIMARY KEY,
    show_time TIMESTAMP,
    price NUMERIC(10,2),
    movie_id BIGINT REFERENCES movies(id),
    hall_id BIGINT REFERENCES halls(id)
);

-- USERS
CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255),
    email VARCHAR(255),
    password VARCHAR(255),
    role VARCHAR(50) CHECK (role IN ('USER', 'ADMIN'))
);

-- TICKETS
CREATE TABLE tickets (
    id BIGSERIAL PRIMARY KEY,
    booked_at TIMESTAMP,
    show_id BIGINT REFERENCES shows(id),
    seat_id BIGINT REFERENCES seats(id),
    user_id BIGINT REFERENCES users(id)
);
