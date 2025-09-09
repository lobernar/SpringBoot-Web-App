-- Insert only runs if no admin exists.
INSERT INTO users (username, password, first_name, last_name, email)
SELECT 'admin', 'password', 'Loic', 'Bernard', 'loic.bernard@test.com'
WHERE NOT EXISTS (SELECT 1 FROM users WHERE username='admin');
