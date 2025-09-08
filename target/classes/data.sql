-- Insert only runs if no admin exists.
INSERT INTO users (username, password)
SELECT 'admin', 'password'
WHERE NOT EXISTS (SELECT 1 FROM users WHERE username='admin');
