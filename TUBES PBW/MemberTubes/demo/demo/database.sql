select * from artis
select * from member
select * from admin
select * from setlist
select * from show
select * from comments

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
    nama_show VARCHAR(100) NOT NULL
);

CREATE TABLE setlist (
    id SERIAL PRIMARY KEY,
    nama_lagu VARCHAR(100) NOT NULL,
    show_terkait VARCHAR(100) NOT NULL,
    show_id INT NOT NULL,
    FOREIGN KEY (show_id) REFERENCES show (id)
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
	
INSERT INTO setlist (nama_lagu, show_terkait, show_id)
VALUES
    ('Shape of You', 'Show 1',1),
    ('Rolling in the Deep', 'Show 2',2),
    ('Nothing Else Matters', 'Show 1',1),
    ('Butter', 'Show 2',2),
    ('Someone Like You', 'Show 1',1);


DELETE FROM artis WHERE id = '37'
