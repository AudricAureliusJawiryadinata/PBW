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
DROP TABLE IF EXISTS history;

CREATE TABLE member (
    id SERIAL PRIMARY KEY,
    username VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
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

CREATE TABLE comments (
    id SERIAL PRIMARY KEY,             -- Unique ID for each comment
    show_id INT NOT NULL,              -- Foreign key referencing show
    member_id INT,                     -- Foreign key referencing member (optional)
    comment_text TEXT NOT NULL,        -- The comment text
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,  -- Timestamp when the comment was made
    FOREIGN KEY (show_id) REFERENCES show(id),       -- Link to the 'show' table
    FOREIGN KEY (member_id) REFERENCES member(id),   -- Link to the 'member' table
    CONSTRAINT chk_comment_text_length CHECK (LENGTH(comment_text) <= 500) -- Optional length constraint
);



CREATE TABLE history (
    id SERIAL PRIMARY KEY,          -- ID unik untuk setiap aktivitas
    user_id INT NOT NULL,           -- ID member yang melakukan aktivitas
    action VARCHAR(255) NOT NULL,   -- Jenis aksi (misalnya: "Tambah Artis")
    details TEXT NOT NULL,          -- Informasi detail tentang aktivitas
    timestamp TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP -- Waktu aktivitas dilakukan
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
	
DELETE FROM setlist WHERE id = '6'
DELETE FROM setlist WHERE id = '9'

