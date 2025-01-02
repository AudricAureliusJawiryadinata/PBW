select * from artis
select * from member
select * from admin

DROP TABLE IF EXISTS member;
DROP TABLE IF EXISTS artis;
DROP TABLE IF EXISTS admin;
DROP TABLE IF EXISTS show;
DROP TABLE IF EXISTS setlist;

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
CREATE TABLE admin (
    id SERIAL PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL
);
INSERT INTO artis (nama_artis, genre_musik)
VALUES
    ('Taylor Swift', 'Pop'),
    ('Ed Sheeran', 'Pop'),
    ('Metallica', 'Metal'),
    ('Adele', 'Soul'),
    ('BTS', 'K-Pop');
INSERT INTO admin (username, password)
VALUES
    ('audric', '12345');

CREATE TABLE show (
    id SERIAL PRIMARY KEY,
    nama_show VARCHAR(100) NOT NULL
);

INSERT INTO show (nama_show)
VALUES
    ('Show 1'),
    ('Show 2');


CREATE TABLE setlist (
    id SERIAL PRIMARY KEY,
    nama_lagu VARCHAR(100) NOT NULL,
	show_terkait VARCHAR(100) NOT NULL,
    show_id INT NOT NULL,
    FOREIGN KEY (show_id) REFERENCES show (id)
);

INSERT INTO setlist (nama_lagu, show_terkait, show_id)
VALUES
    ('Shape of You', 'Show 1',1),
    ('Rolling in the Deep', 'Show 2',2),
    ('Nothing Else Matters', 'Show 1',1),
    ('Butter', 'Show 2',2),
    ('Someone Like You', 'Show 1',1);
