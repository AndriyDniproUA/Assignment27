CREATE DATABASE notes_repository;

CREATE TABLE  notes(
id SERIAL PRIMARY KEY,
title VARCHAR(254) NOT NULL,
contents TEXT NOT NULL,
origin_date_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP);


-- for testing
INSERT INTO notes (title, contents) VALUES
    ('test1','note number one'),
    ('test2','note number two');

-- for getAll()
SELECT id,title, contents, origin_date_time FROM notes;




