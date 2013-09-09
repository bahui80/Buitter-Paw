CREATE TABLE Users (
	userid SERIAL PRIMARY KEY NOT NULL,  
	name VARCHAR(32) NOT NULL,
	surname VARCHAR(32) NOT NULL,
	password VARCHAR(32) NOT NULL,
	date DATE NOT NULL,
	photo BYTEA,
	username VARCHAR(32) NOT NULL,
	description VARCHAR(140) NOT NULL,
	secret_question VARCHAR(60) NOT NULL,
	secret_answer VARCHAR(60) NOT NULL,
	creation_date DATE NOT NULL
);

CREATE TABLE Buits (
	buitid SERIAL PRIMARY KEY NOT NULL,  
	message VARCHAR(500) NOT NULL,
	userid INTEGER NOT NULL REFERENCES Users(userid),
	date DATE NOT NULL
);

CREATE TABLE Hashtags (
	hashtag VARCHAR(40) NOT NULL,
	hashtagid SERIAL PRIMARY KEY NOT NULL
);

CREATE TABLE Buithash (
	buitid INTEGER NOT NULL,
	hashtagid INTEGER NOT NULL,
	PRIMARY KEY(buitid, hashtagid)
);
