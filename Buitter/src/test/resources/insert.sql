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
    'Fiel seguidor del capitan Jack Sparrow','Do you hate Twitter?','Yes');
    
INSERT INTO users(name, surname, password, date, username, description, 
            secret_question, secret_answer) VALUES ('Andres','Gregoire','12345678','2013-09-19 16:00:00.557','andipaw',
    'Amante de Buitter','Do you hate Twitter?','Si, Buitter es superior');

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

INSERT INTO buits(message, userid, date) VALUES ('Siempre acusando a la gente de vendehumo @negro #militonodebe','3', '2013-09-18 11:58:12.557');
INSERT INTO buits(message, userid, date) VALUES ('#NuevasReglas','3', '2013-09-18 11:59:14.557');

INSERT INTO buits(message, userid, date) VALUES ('Me impresiono Buitter. #buitterFan #weBuitNow #weDontTwitt','6', '2013-09-19 16:30:14.557');
INSERT INTO buits(message, userid, date) VALUES ('Buitter merece un 12 #notaPerfecta','6', '2013-09-19 16:32:14.557');
INSERT INTO buits(message, userid, date) VALUES ('Banquense ser segundos @germanromarion #SocialCthulhu #weBuitNow','6', '2013-09-19 16:34:14.557');
-- END BUITS

-- BEGIN HASHTAGS
INSERT INTO hashtags(hashtag, userid, date) VALUES ('radioTKM','1', '2013-09-15 09:58:12.557');
INSERT INTO hashtags(hashtag, userid, date) VALUES ('NuevasReglas','1', '2013-09-16 14:58:12.557');
INSERT INTO hashtags(hashtag, userid, date) VALUES ('NPDanceFitness','1', '2013-09-16 14:58:12.557');
INSERT INTO hashtags(hashtag, userid, date) VALUES ('militodebe','5', '2013-09-17 14:58:12.557');
INSERT INTO hashtags(hashtag, userid, date) VALUES ('militonodebe','3', '2013-09-18 11:58:12.557');
INSERT INTO hashtags(hashtag, userid, date) VALUES ('buitterFan','6', '2013-09-19 16:30:14.557');
INSERT INTO hashtags(hashtag, userid, date) VALUES ('weBuitNow','6', '2013-09-19 16:30:14.557');
INSERT INTO hashtags(hashtag, userid, date) VALUES ('weDontTwitt','6', '2013-09-19 16:30:14.557');
INSERT INTO hashtags(hashtag, userid, date) VALUES ('notaPerfecta','6', '2013-09-19 16:32:14.557');
INSERT INTO hashtags(hashtag, userid, date) VALUES ('SocialCthulhu','6', '2013-09-19 16:34:14.557');
-- END HASHTAGS

-- BEGIN BUITHASH
INSERT INTO buithash(buitid, hashtagid) VALUES('1','1');
INSERT INTO buithash(buitid, hashtagid) VALUES('2','3');
INSERT INTO buithash(buitid, hashtagid) VALUES('3','2');
INSERT INTO buithash(buitid, hashtagid) VALUES('5','2');
INSERT INTO buithash(buitid, hashtagid) VALUES('7','2');
INSERT INTO buithash(buitid, hashtagid) VALUES('8','2');
INSERT INTO buithash(buitid, hashtagid) VALUES('9','4');
INSERT INTO buithash(buitid, hashtagid) VALUES('10','5');
INSERT INTO buithash(buitid, hashtagid) VALUES('11','2');
INSERT INTO buithash(buitid, hashtagid) VALUES('12','6');
INSERT INTO buithash(buitid, hashtagid) VALUES('12','7');
INSERT INTO buithash(buitid, hashtagid) VALUES('12','8');
INSERT INTO buithash(buitid, hashtagid) VALUES('13','9');
INSERT INTO buithash(buitid, hashtagid) VALUES('14','10');
INSERT INTO buithash(buitid, hashtagid) VALUES('14','7');
-- END BUITHASH

-- BEGIN URLS
INSERT INTO urls(url, buiturl, buitid) VALUES('http://www.youtube.com/watch?v=oX8aAjtRZrc','buit.li/3a2c','2');
-- END URLS
