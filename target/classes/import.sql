INSERT INTO users (name, lastname, username, email, password, enabled) VALUES('william', 'rehel','admin', 'william.rehel@gmail.com', '$2y$12$nX93AyMCw16.WK4fmUZPKOhBGrD3hs/GqwfwzTuDJ2t7LidiyjgCy', 1);

INSERT INTO authorities (user_id, authority) VALUES(1, 'ROLE_ADMIN');
