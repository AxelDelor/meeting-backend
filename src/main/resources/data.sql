-- Room --
INSERT INTO rooms (name, capacity, floor)
VALUES ('Salle 103', 15, 'RDC');


-- Booking --
INSERT INTO bookings (room_id, title, organizer, start_time, end_time)
VALUES (1, 'Réunion', 'Joe Dante', '2026-12-12T10:00:00', '2026-12-12T12:00:00');