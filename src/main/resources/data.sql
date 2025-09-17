-- Insert only runs if no admin exists.
INSERT INTO users (username, password, first_name, last_name, email, role)
SELECT 'admin', '$2a$10$AwylYcY0ejaD8X4di6Pj8eKc.BXQdmHTeZeUFk8h3jmR37RAUTe0S', 'Loic', 'Bernard', 'loic.bernard@test.com', 'ADMIN'
WHERE NOT EXISTS (SELECT 1 FROM users WHERE username='admin');
