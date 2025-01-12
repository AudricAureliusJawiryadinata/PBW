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
    username VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
	password VARCHAR(225) NOT NULL
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
    tanggal_show DATE NOT NULL
);

CREATE TABLE setlist (
    id SERIAL PRIMARY KEY,
    nama_lagu VARCHAR(100) NOT NULL,
    show_terkait VARCHAR(100) NOT NULL,
    show_id INT NOT NULL,
    artist_id INT,
	nama_artis VARCHAR (255),
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
INSERT INTO admin (username, password)
VALUES
    ('audric', '12345');

INSERT INTO member (username, email, password)
Values
	('rafli', 'user1@gmail.com', '12345');


INSERT INTO artis (nama_artis, genre_musik)
VALUES
    ('Coldplay', 'Alternative Rock'),
	('Ed Sheeran', 'Pop'),
	('Taylor Swift', 'Pop'),
    ('Bruno Mars', 'R&B');
	
ALTER TABLE show
ALTER COLUMN lokasi_show DROP DEFAULT,
ALTER COLUMN tanggal_show DROP DEFAULT;

UPDATE setlist
SET nama_artis = artis.nama_artis
FROM artis
WHERE setlist.artist_id = artis.id
  AND setlist.nama_artis IS NULL;
