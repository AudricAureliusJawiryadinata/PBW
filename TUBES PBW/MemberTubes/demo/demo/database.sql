select * from artis
select * from member
select * from admin
select * from setlist
select * from song;
select * from show
select * from comments

DROP TABLE IF EXISTS member;
DROP TABLE IF EXISTS artis;
DROP TABLE IF EXISTS admin;
DROP TABLE IF EXISTS song;
DROP TABLE IF EXISTS show;
DROP TABLE IF EXISTS setlist;
DROP TABLE IF EXISTS comments;

CREATE TABLE member (
    id SERIAL PRIMARY KEY,
    nama VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL
);
CREATE TABLE artis (
    id SERIAL PRIMARY KEY,
    nama_artis VARCHAR(100) NOT NULL
);

CREATE TABLE admin (
    id SERIAL PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL
);

CREATE TABLE show (
    id SERIAL PRIMARY KEY,
    nama_show VARCHAR(100) NOT NULL,
    nama_kota VARCHAR(255) NOT NULL,
    tanggal_acara DATE NOT NULL,
    waktu_mulai VARCHAR (80) NOT NULL
);

CREATE TABLE setlist (
    id SERIAL PRIMARY KEY,
    nama_lagu VARCHAR(100) NOT NULL,
    show_terkait VARCHAR(100) NOT NULL,
    show_id INT NOT NULL,
    FOREIGN KEY (show_id) REFERENCES show (id)
);

CREATE TABLE song (
    id SERIAL PRIMARY KEY,
    nama_lagu VARCHAR(255) NOT NULL,
    genre_musik VARCHAR(50) NOT NULL,
    setlist_id INT NOT NULL,
    artis_id INT NOT NULL,
    FOREIGN KEY (setlist_id) REFERENCES setlist (id),
    FOREIGN KEY (artis_id) REFERENCES artis (id)
);

CREATE TABLE comments (
    id SERIAL PRIMARY KEY,
    user_id INT NOT NULL,     -- Foreign key referencing member
    show_id INT NOT NULL,     -- Foreign key referencing show
    comment_text TEXT NOT NULL,  -- The actual comment text
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, -- Timestamp when the comment was created
    FOREIGN KEY (user_id) REFERENCES member(id),
    FOREIGN KEY (show_id) REFERENCES show(id)
);

CREATE TABLE comments (
    id SERIAL PRIMARY KEY,             -- Unique ID for each comment
    show_id INT NOT NULL,              -- Foreign key referencing show
    comment_text TEXT NOT NULL,        -- The comment text
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,  -- Timestamp when the comment was made
    FOREIGN KEY (show_id) REFERENCES show(id)  -- Link to the 'show' table
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
	
INSERT INTO setlist (nama_lagu, show_terkait, show_id)
VALUES
    ('Shape of You', 'Show 1',1),
    ('Rolling in the Deep', 'Show 2',2),
    ('Nothing Else Matters', 'Show 1',1),
    ('Butter', 'Show 2',2),
    ('Someone Like You', 'Show 1',1);


DELETE FROM artis WHERE id = '37'
