-- BEGIN URLS FROM SPRINT1 --
UPDATE Buits SET message = 'Sabadoo #NPDanceFitness buit.li/3a2c' WHERE id = 2;
INSERT INTO Buits_urls(buits_id, urls_id) VALUES(2,1);
-- END URLS FROM SPRINT1 --

-- BEGIN MENTIONES FROM SPRINT1
INSERT INTO Buits_mentioned_buitters(buits_id, element) VALUES(10,'negro');
INSERT INTO Event(date, message, firer_id) VALUES('2013-09-18 11:58:12.557','mentioned you',3);
INSERT INTO MentionedEvent(id) VALUES(1);
INSERT INTO Users_event(users_id, events_id) VALUES(5,1);

INSERT INTO Buits_mentioned_buitters(buits_id, element) VALUES(7,'negro');
INSERT INTO Event(date, message, firer_id) VALUES('2013-09-19 14:58:12.557','mentioned you',2);
INSERT INTO MentionedEvent(id) VALUES(2);
INSERT INTO Users_event(users_id, events_id) VALUES(5,2);
-- END MENTIONES FROM SPRINT1

-- BEGIN NEW INSERTS FOLLOWS --
INSERT INTO Event(date, message, firer_id) VALUES('2013-11-15 17:32:12.557','started following you',5);
INSERT INTO FollowedEvent(id) VALUES(3);
INSERT INTO Users_event(users_id, events_id) VALUES(4,3);
INSERT INTO Users_users(following_id, followers_id) VALUES(4,5);
-- END NEW INSERTS FOLLOWS --

-- BEGIN NEW INSERTS FAVORITES --
INSERT INTO Buits_users(favorites_id, favoritter_id) VALUES(3,5);
-- END NEW INSERTS FAVORITES --

-- BEGIN NEW INSERTS REBUITS --
INSERT INTO Buits(message, userid, date) VALUES('Buitter merece un 12 #notaPerfecta',4,'2014-01-21 13:51:30.908');
INSERT INTO Buits_hashtags(buits_id, hashtags_id) VALUES(15,9);
INSERT INTO Event(date, message, firer_id) VALUES('2014-01-21 13:51:30.908','rebuitted your buit',4);
INSERT INTO Rebuitevent(id) VALUES(4);
INSERT INTO Rebuits(rebuit_date, id, buit_id, user_id) VALUES('2013-09-19 16:32:14.557',15,13,6);
INSERT INTO Users_event(users_id, events_id) VALUES(6,4);
-- END NEW INSERTS REBUITS --