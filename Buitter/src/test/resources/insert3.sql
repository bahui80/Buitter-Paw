-- BEGIN NEW INSERTS USERLIST --
INSERT INTO userlist(description, name, owner_id) VALUES ('Alumnos del ITBA', 'Alumnos', 6);
INSERT INTO userlist(description, name, owner_id) VALUES ('Profesores del ITBA', 'Profesores', 1);
INSERT INTO userlist(description, name, owner_id) VALUES ('Chicas de reggaeton', 'Reggaeton', 1);
INSERT INTO userlist(description, name, owner_id) VALUES ('Gente de la facu', 'ITBA', 1);
INSERT INTO userlist(description, name, owner_id) VALUES ('Gente de la facu', 'ITBA', 7);
INSERT INTO userlist(description, name, owner_id) VALUES ('Gente del colegio', 'CMB', 7);
INSERT INTO userlist(description, name, owner_id) VALUES ('Gente del gimnasio', 'Gimnasio', 7);
-- END NEW INSERTS USERLIST --

-- BEGIN NEW INSERTS USERLIST_USERS --
INSERT INTO userlist_users(userlistsin_id, users_id) VALUES (1, 2);
INSERT INTO userlist_users(userlistsin_id, users_id) VALUES (1, 4);
INSERT INTO userlist_users(userlistsin_id, users_id) VALUES (1, 5);
INSERT INTO userlist_users(userlistsin_id, users_id) VALUES (1, 7);
INSERT INTO userlist_users(userlistsin_id, users_id) VALUES (2, 6);
INSERT INTO userlist_users(userlistsin_id, users_id) VALUES (4, 2);
INSERT INTO userlist_users(userlistsin_id, users_id) VALUES (4, 3);
INSERT INTO userlist_users(userlistsin_id, users_id) VALUES (4, 4);
INSERT INTO userlist_users(userlistsin_id, users_id) VALUES (4, 5);
INSERT INTO userlist_users(userlistsin_id, users_id) VALUES (4, 7);
INSERT INTO userlist_users(userlistsin_id, users_id) VALUES (5, 2);
INSERT INTO userlist_users(userlistsin_id, users_id) VALUES (5, 3);
INSERT INTO userlist_users(userlistsin_id, users_id) VALUES (5, 4);
INSERT INTO userlist_users(userlistsin_id, users_id) VALUES (5, 5);
-- END NEW INSERTS USERLIST_USERS --

-- BEGIN NEW INSERTS USERS_BLACKLIST --
INSERT INTO users_blacklist(blacklist_id, blacklistedby_id) VALUES (3, 6);
-- END NEW INSERTS USERS_BLACKLIST --

-- BEGIN NEW INSERTS PUBLICITIES --
INSERT INTO publicity(clientname, imageurl, url) VALUES ('Bellevue', 'http://www.bellevuecollege.edu/ps/Images%202/MoE-Banner-12-10.jpg', 'http://www.bellevuecollege.edu/');
INSERT INTO publicity(clientname, imageurl, url) VALUES ('Three frogs graphics', 'http://www.treefrog-graphics.co.uk/images/Product%20Images/Banner%20Example.jpg', 'http://www.treefrog-graphics.co.uk/');
INSERT INTO publicity(clientname, imageurl, url) VALUES ('Frogger', 'http://wiki.thegamesdb.net/images/d/d7/Banner-example-3.jpg', 'http://www.happyhopper.org/welcome.php');
INSERT INTO publicity(clientname, imageurl, url) VALUES ('Aerolineas Argentinas', 'http://www.plusgrade.com/images/banner-example-1.jpg', 'http://www.aerolineas.com.ar/');
-- END NEW INSERTS PUBLICITIES --
