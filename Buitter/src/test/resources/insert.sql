-- BEGIN USERS
INSERT INTO users(name, surname, password, date, username, description, 
            secret_question, secret_answer) VALUES ('Julian','Gutierrez','123456789','2013-09-13 20:58:12.557','gurygutierrez',
    'Pues toma pa que te enamores more, more','Who was your favourite teacher?','Rinaldi');
    
INSERT INTO users(name, surname, password, date, username, description, 
            secret_question, secret_answer) VALUES ('Martin','Purita','12345678','2013-09-14 14:58:12.557','masacre',
    'A dieta','What is the name of your dog?','Betty');
    
INSERT INTO users(name, surname, password, date, username, description, 
            secret_question, secret_answer) VALUES ('Santiago','Milito','12345678','2012-09-15 19:58:12.557','santimilito',
    'Hoy consigo pre','Who was your favourite teacher?','Starico');
    
INSERT INTO users(name, surname, password, date, username, description, 
            secret_question, secret_answer) VALUES ('Tomas','Pulenta','12345678','2012-09-15 19:58:12.557','tomipule',
    'No vives de ensalada','Do you hate Twitter?','I love buitter');
    
INSERT INTO users(name, surname, password, date, username, description, 
            secret_question, secret_answer) VALUES ('Agustin','Scigliano','12345678','2011-05-15 17:58:12.557','negro',
    'No vives de ensalada','Do you hate Twitter?','Yes');

-- END USERS

-- BEGIN BUITS  
INSERT INTO buits(message, userid, date) VALUES ('Escuchando #radioTKM','1', '2013-09-15 09:58:12.557');
INSERT INTO buits(message, userid, date) VALUES ('Sabadoo #NPDanceFitness http://www.youtube.com/watch?v=oX8aAjtRZrc','1', '2013-09-16 14:58:12.557');
INSERT INTO buits(message, userid, date) VALUES ('#NuevasReglas @facumenzella @rochimartinez','1', '2013-09-16 14:58:12.557');

INSERT INTO buits(message, userid, date) VALUES ('Empece con mi nueva dieta calorica','2', '2013-09-17 14:58:12.557');
INSERT INTO buits(message, userid, date) VALUES ('#NuevasReglas @facumenzella @rochimartinez','2', '2013-09-17 14:58:12.557');
INSERT INTO buits(message, userid, date) VALUES ('Tomando un gancia','2', '2013-09-17 14:58:12.557');
INSERT INTO buits(message, userid, date) VALUES ('#NuevasReglas @negro @pau','2', '2013-09-19 14:58:12.557');

INSERT INTO buits(message, userid, date) VALUES ('#NuevasReglas','5', '2013-09-19 14:58:12.557');
INSERT INTO buits(message, userid, date) VALUES ('Vendehumo #militodebe','5', '2013-09-17 14:58:12.557');

-- END BUITS

-- BEGIN HASHTAGS
INSERT INTO hashtags(hashtag, userid, date) VALUES ('radioTKM','1', '2013-09-15 09:58:12.557');
INSERT INTO hashtags(hashtag, userid, date) VALUES ('NuevasReglas','1', '2013-09-16 14:58:12.557');
INSERT INTO hashtags(hashtag, userid, date) VALUES ('NPDanceFitness','1', '2013-09-16 14:58:12.557');
INSERT INTO hashtags(hashtag, userid, date) VALUES ('militodebe','5', '2013-09-17 14:58:12.557');
-- END HASHTAGS

-- BEGIN BUITHASH
INSERT INTO buithash(buitid, hashtagid) VALUES('1','1');
INSERT INTO buithash(buitid, hashtagid) VALUES('2','3');
INSERT INTO buithash(buitid, hashtagid) VALUES('3','2');
INSERT INTO buithash(buitid, hashtagid) VALUES('5','2');
INSERT INTO buithash(buitid, hashtagid) VALUES('7','2');
INSERT INTO buithash(buitid, hashtagid) VALUES('8','2');
INSERT INTO buithash(buitid, hashtagid) VALUES('9','4');

-- END BUITHASH

-- BEGIN URLS
INSERT INTO urls(url, buiturl, buitid) VALUES('http://www.youtube.com/watch?v=oX8aAjtRZrc','buit.li/3a2c','2');

-- END URLS
