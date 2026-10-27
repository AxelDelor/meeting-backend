-- Room --
INSERT INTO rooms (name, capacity, floor)
VALUES ('Salle 103', 15, 'RDC');
INSERT INTO rooms (name, capacity, floor)
VALUES ('Salle 104', 18, 'RDC');
INSERT INTO rooms (name, capacity, floor)
VALUES ('Salle 206', 10, '1st floor');


-- Booking --
INSERT INTO bookings (room_id, title, organizer, start_time, end_time)
VALUES (1, 'Réunion', 'Joe Dante', '2026-12-12T10:00:00', '2026-12-12T12:00:00');
INSERT INTO bookings (room_id, title, organizer, start_time, end_time)
VALUES (3, 'Pot', 'Mac Tiernan', '2026-11-11T15:00:00', '2026-12-12T18:00:00');