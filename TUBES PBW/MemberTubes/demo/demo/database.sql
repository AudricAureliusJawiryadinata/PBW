select * from artis
select * from member

DROP TABLE IF EXISTS member;
DROP TABLE IF EXISTS artis;
CREATE TABLE member (
    id SERIAL PRIMARY KEY,
    username(100) NOT NULL,
    email(100) NOT NULL,
	password(225)) NOT NULL
);
CREATE TABLE artis (
    id SERIAL PRIMARY KEY,
    nama_artis VARCHAR(100) NOT NULL,
    genre_musik VARCHAR(50) NOT NULL
);
INSERT INTO artis (nama_artis, genre_musik)
VALUES
    ('Taylor Swift', 'Pop'),
    ('Ed Sheeran', 'Pop'),
    ('Metallica', 'Metal'),
    ('Adele', 'Soul'),
    ('BTS', 'K-Pop');
