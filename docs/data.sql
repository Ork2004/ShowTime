-- USERS
INSERT INTO users (name, email, password, role) VALUES
('User', 'user@example.com', '$2a$10$1/TUiIcGPFP6GzohQacBseNbRx2kHfJiIGARRDs/cbmeHvIifshDG', 'USER'),
('Admin', 'admin@example.com', '$2a$10$/sGjctI6GtfjLphPpxI/1efRPf6UHVGKKU0I3HYMeSkhoZRcgmFiG', 'ADMIN');

-- THEATERS
INSERT INTO theaters (name, location) VALUES
('Mega Cinema', 'Almaty, Dostyk Ave 45'),
('Kinopark 8', 'Astana, Khan Shatyr'),
('Chaplin Cinemas', 'Shymkent, Mega Planet');

-- HALLS
INSERT INTO halls (theater_id, name) VALUES
(1, 'Hall 1'),
(1, 'Hall 2'),
(1, 'VIP Hall'),
(2, 'Hall 1'),
(2, 'Hall 2'),
(2, 'Premium Hall'),
(3, 'Hall A'),
(3, 'Hall B'),
(3, 'Hall C');

-- GENRES
INSERT INTO genres (name) VALUES
('Action'),
('Comedy'),
('Drama'),
('Horror'),
('Science Fiction'),
('Romance'),
('Animation'),
('Fantasy'),
('Thriller'),
('Adventure');

-- MOVIES
INSERT INTO movies (title, duration, rating, genre_id) VALUES
('Inception', '02:28:00', 8.8, 5),
('The Dark Knight', '02:32:00', 9.0, 1),
('Interstellar', '02:49:00', 8.6, 5),
('The Godfather', '02:55:00', 9.2, 3),
('Forrest Gump', '02:22:00', 8.8, 3),
('Avengers: Endgame', '03:01:00', 8.4, 10),
('Joker', '02:02:00', 8.5, 3),
('Frozen II', '01:43:00', 6.9, 7),
('Spider-Man: No Way Home', '02:28:00', 8.3, 1),
('Titanic', '03:15:00', 7.8, 6),
('The Conjuring', '01:52:00', 7.5, 4),
('Avatar: The Way of Water', '03:12:00', 7.7, 5),
('Inside Out', '01:35:00', 8.1, 7),
('The Matrix', '02:16:00', 8.7, 5),
('Guardians of the Galaxy Vol. 3', '02:30:00', 8.1, 10);

-- SHOWS
INSERT INTO shows (movie_id, hall_id, show_time, price) VALUES
(1, 1, '2025-10-18 14:30:00', 2500.00),
(1, 7, '2025-10-19 15:00:00', 2300.00),
(1, 9, '2025-10-20 16:30:00', 2400.00),

(2, 2, '2025-10-18 17:00:00', 2700.00),
(2, 8, '2025-10-19 18:00:00', 2600.00),
(2, 9, '2025-10-20 19:30:00', 2500.00),

(3, 3, '2025-10-18 20:00:00', 3000.00),
(3, 6, '2025-10-19 21:00:00', 3200.00),
(3, 9, '2025-10-20 22:30:00', 3100.00),

(4, 1, '2025-10-18 15:30:00', 2000.00),
(4, 7, '2025-10-19 17:00:00', 2100.00),
(4, 8, '2025-10-20 19:00:00', 2200.00),

(5, 2, '2025-10-18 16:00:00', 2100.00),
(5, 8, '2025-10-19 18:30:00', 2200.00),
(5, 9, '2025-10-20 20:00:00', 2300.00),

(6, 3, '2025-10-18 21:00:00', 3500.00),
(6, 6, '2025-10-19 22:00:00', 3600.00),
(6, 9, '2025-10-20 23:30:00', 3700.00),

(7, 1, '2025-10-18 15:00:00', 2300.00),
(7, 7, '2025-10-19 17:30:00', 2400.00),
(7, 8, '2025-10-20 19:30:00', 2500.00),

(8, 2, '2025-10-18 13:00:00', 1800.00),
(8, 8, '2025-10-19 15:30:00', 1900.00),
(8, 9, '2025-10-20 17:30:00', 2000.00),

(9, 3, '2025-10-18 19:00:00', 2800.00),
(9, 6, '2025-10-19 20:30:00', 2900.00),
(9, 9, '2025-10-20 22:00:00', 3000.00),

(10, 1, '2025-10-18 14:00:00', 2100.00),
(10, 7, '2025-10-19 16:30:00', 2200.00),
(10, 8, '2025-10-20 19:00:00', 2300.00),

(11, 2, '2025-10-18 16:00:00', 2000.00),
(11, 8, '2025-10-19 18:30:00', 2100.00),
(11, 9, '2025-10-20 21:00:00', 2200.00),

(12, 3, '2025-10-18 19:30:00', 3200.00),
(12, 6, '2025-10-19 21:00:00', 3300.00),
(12, 9, '2025-10-20 23:00:00', 3400.00),

(13, 1, '2025-10-18 13:00:00', 1900.00),
(13, 7, '2025-10-19 15:30:00', 2000.00),
(13, 8, '2025-10-20 17:00:00', 2100.00),

(14, 2, '2025-10-18 17:00:00', 2600.00),
(14, 8, '2025-10-19 19:30:00', 2700.00),
(14, 9, '2025-10-20 21:30:00', 2800.00),

(15, 3, '2025-10-18 20:30:00', 3300.00),
(15, 6, '2025-10-19 22:00:00', 3400.00),
(15, 9, '2025-10-20 23:30:00', 3500.00);

--SEATS
-- Mega Cinema
-- Hall 1 (id = 1)
DO $$
DECLARE r INT; s INT;
BEGIN
  FOR r IN 1..6 LOOP
    FOR s IN 1..8 LOOP
      INSERT INTO seats (hall_id, row_number, seat_number) VALUES (1, r, s);
    END LOOP;
  END LOOP;
END $$;

-- Hall 2 (id = 2)
DO $$
DECLARE r INT; s INT;
BEGIN
  FOR r IN 1..5 LOOP
    FOR s IN 1..6 LOOP
      INSERT INTO seats (hall_id, row_number, seat_number) VALUES (2, r, s);
    END LOOP;
  END LOOP;
END $$;

-- VIP Hall (id = 3)
DO $$
DECLARE r INT; s INT;
BEGIN
  FOR r IN 1..3 LOOP
    FOR s IN 1..4 LOOP
      INSERT INTO seats (hall_id, row_number, seat_number) VALUES (3, r, s);
    END LOOP;
  END LOOP;
END $$;

-- Kinopark 8
-- Hall 1 (id = 4)
DO $$
DECLARE r INT; s INT;
BEGIN
  FOR r IN 1..5 LOOP
    FOR s IN 1..7 LOOP
      INSERT INTO seats (hall_id, row_number, seat_number) VALUES (4, r, s);
    END LOOP;
  END LOOP;
END $$;

-- Hall 2 (id = 5)
DO $$
DECLARE r INT; s INT;
BEGIN
  FOR r IN 1..6 LOOP
    FOR s IN 1..8 LOOP
      INSERT INTO seats (hall_id, row_number, seat_number) VALUES (5, r, s);
    END LOOP;
  END LOOP;
END $$;

-- Premium Hall (id = 6)
DO $$
DECLARE r INT; s INT;
BEGIN
  FOR r IN 1..4 LOOP
    FOR s IN 1..5 LOOP
      INSERT INTO seats (hall_id, row_number, seat_number) VALUES (6, r, s);
    END LOOP;
  END LOOP;
END $$;

-- Chaplin Cinemas
-- Hall A (id = 7)
DO $$
DECLARE r INT; s INT;
BEGIN
  FOR r IN 1..7 LOOP
    FOR s IN 1..10 LOOP
      INSERT INTO seats (hall_id, row_number, seat_number) VALUES (7, r, s);
    END LOOP;
  END LOOP;
END $$;

-- Hall B (id = 8)
DO $$
DECLARE r INT; s INT;
BEGIN
  FOR r IN 1..6 LOOP
    FOR s IN 1..8 LOOP
      INSERT INTO seats (hall_id, row_number, seat_number) VALUES (8, r, s);
    END LOOP;
  END LOOP;
END $$;

-- Hall C (id = 9)
DO $$
DECLARE r INT; s INT;
BEGIN
  FOR r IN 1..5 LOOP
    FOR s IN 1..6 LOOP
      INSERT INTO seats (hall_id, row_number, seat_number) VALUES (9, r, s);
    END LOOP;
  END LOOP;
END $$;
