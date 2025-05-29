INSERT INTO estado_participacion (nombre_estado) VALUES
('Confirmado'),
('Pendiente'),
('Cancelado'),
('Asistió'),
('No asistió'),
('Reprogramado'),
('En espera'),
('Suspendido'),
('Participación válida'),
('Participación rechazada');




INSERT INTO participacion (id_usuario, id_estado, id_evento, fecha_participacion) VALUES 
(1, 1, 1, '2025-05-01 10:00:00'), -- Confirmado
(2, 2, 2, '2025-05-02 11:30:00'), -- Pendiente
(3, 3, 3, '2025-05-03 09:15:00'), -- Cancelado
(4, 4, 1, '2025-05-04 14:45:00'), -- Asistió
(5, 5, 4, '2025-05-05 16:20:00'), -- No asistió
(1, 6, 2, '2025-05-06 13:05:00'), -- Reprogramado
(2, 7, 3, '2025-05-07 08:00:00'), -- En espera
(3, 8, 4, '2025-05-08 17:50:00'), -- Suspendido
(4, 9, 1, '2025-05-09 12:10:00'), -- Participación válida
(5, 10, 2, '2025-05-10 19:30:00'); -- Participación rechazada