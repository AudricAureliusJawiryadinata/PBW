select * from artis
select * from member
select * from admin
select * from setlist
select * from show
select * from comments
select * from change_history

DROP TABLE IF EXISTS member;
DROP TABLE IF EXISTS artis;
DROP TABLE IF EXISTS admin;
DROP TABLE IF EXISTS show;
DROP TABLE IF EXISTS setlist;
DROP TABLE IF EXISTS comments;
DROP TABLE IF EXISTS change_history;

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

CREATE TABLE show (
    id SERIAL PRIMARY KEY,
    nama_show VARCHAR(100) NOT NULL,
    lokasi_show VARCHAR(255) NOT NULL,
    tanggal_show DATE NOT NULL,
);
CREATE TABLE setlist (
    id SERIAL PRIMARY KEY,
    nama_lagu VARCHAR(100) NOT NULL,
    show_terkait VARCHAR(100) NOT NULL,
    show_id INT NOT NULL,
    artist_id INT,
    FOREIGN KEY (show_id) REFERENCES show (id),
    FOREIGN KEY (artist_id) REFERENCES artis (id)
);


CREATE TABLE song (
    id SERIAL PRIMARY KEY,
    nama_lagu VARCHAR(255) NOT NULL,
	setlist_id INT NOT NULL,
    artis_id INT NOT NULL,
    FOREIGN KEY (setlist_id) REFERENCES setlist (id),
    FOREIGN KEY (artis_id) REFERENCES artis (id)
);

CREATE TABLE comments (
    id SERIAL PRIMARY KEY,             -- Unique ID for each comment
    show_id INT NOT NULL,              -- Foreign key referencing show
    comment_text TEXT NOT NULL,        -- The comment text
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,  -- Timestamp when the comment was made
    FOREIGN KEY (show_id) REFERENCES show(id)  -- Link to the 'show' table
);


CREATE TABLE change_history (
    id SERIAL PRIMARY KEY,
    user_id INT NOT NULL,
    action_type VARCHAR(50) NOT NULL,
    entity_type VARCHAR(50) NOT NULL,
    entity_id INT,
    description TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES member(id)
);



-- Example INSERTS
INSERT INTO comments (show_id, comment_text)
VALUES
    (1, 'Amazing show, loved it!'),
    (2, 'Not bad, but could be better.'),
    (1, 'The performances were great!'),
    (2, 'Enjoyed the concert a lot!');




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

INSERT INTO show (nama_show)
VALUES
    ('Show 1'),
    ('Show 2');
	
ALTER TABLE show
ALTER COLUMN lokasi_show DROP DEFAULT,
ALTER COLUMN tanggal_show DROP DEFAULT;

UPDATE setlist
SET nama_artis = artis.nama_artis
FROM artis
WHERE setlist.artist_id = artis.id
  AND setlist.nama_artis IS NULL;

SELECT id, nama_lagu, show_terkait, nama_artis
FROM setlist
WHERE nama_artis IS NOT NULL;

DELETE FROM setlist WHERE id = '8'
DELETE FROM setlist WHERE id = '9'