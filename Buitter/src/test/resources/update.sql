-- Users table --
ALTER TABLE Users ADD UNIQUE (username);
ALTER TABLE Users ADD COLUMN privacy boolean DEFAULT false;
ALTER TABLE Users ADD COLUMN visits integer DEFAULT 0;
ALTER TABLE Users DROP COLUMN photo;
ALTER TABLE Users ADD COLUMN photo oid;
ALTER TABLE Users RENAME COLUMN userid TO id;

-- Urls table --
ALTER TABLE Urls DROP COLUMN buitid;
ALTER TABLE Urls ADD CONSTRAINT pk_urls PRIMARY KEY (urlid);
ALTER TABLE Urls RENAME COLUMN urlid TO id;

-- Hashtags table --
ALTER TABLE Hashtags RENAME COLUMN hashtagid TO id;

-- Buits table --
ALTER TABLE Buits RENAME COLUMN buitid TO id;

-- Buithash table --
ALTER TABLE buithash DROP CONSTRAINT buithash_pkey;
ALTER TABLE buithash RENAME COLUMN buitid TO buits_id;
ALTER TABLE buithash RENAME COLUMN hashtagid TO hashtags_id;
ALTER TABLE buithash RENAME TO buits_hashtags;

-- New table buits_urls --
CREATE TABLE buits_urls (
	buits_id integer NOT NULL REFERENCES Buits(id),
	urls_id integer NOT NULL UNIQUE REFERENCES Urls(id)
 );

-- New table event --
CREATE TABLE event (
	id serial PRIMARY KEY NOT NULL,
	date timestamp without time zone NOT NULL,
	message character varying(255),
	firer_id integer REFERENCES Users(id)
);

-- New table buits_mentioned_buitters --
CREATE TABLE buits_mentioned_buitters ( 
	buits_id integer NOT NULL REFERENCES Buits(id),
	element character varying(255)
);

-- New table buits_users --
CREATE TABLE buits_users (
	favorites_id integer NOT NULL REFERENCES Buits(id),
	favoritter_id integer NOT NULL REFERENCES Users(id),
	PRIMARY KEY (favorites_id, favoritter_id)
);

-- New table followedevent --
CREATE TABLE followedevent (
	id integer PRIMARY KEY NOT NULL REFERENCES Event(id)
);

-- New table mentionedevent --
CREATE TABLE mentionedevent (
	id integer PRIMARY KEY NOT NULL REFERENCES Event(id)
);

-- New table rebuitevent --
CREATE TABLE rebuitevent (
	id integer PRIMARY KEY NOT NULL REFERENCES Event(id)
);

-- New table users_event --
CREATE TABLE users_event (
	users_id integer NOT NULL REFERENCES Users(id),
	events_id integer NOT NULL UNIQUE REFERENCES Event(id)
);

-- New table rebuits --
CREATE TABLE rebuits (
	rebuit_date timestamp without time zone NOT NULL,
	id integer PRIMARY KEY NOT NULL REFERENCES Buits(id),
	buit_id integer REFERENCES Buits(id),
	user_id integer REFERENCES Users(id)
);

-- New table users_users --
CREATE TABLE users_users (
	following_id integer NOT NULL REFERENCES Users(id),
	followers_id integer NOT NULL REFERENCES Users(id),
	PRIMARY KEY (following_id, followers_id)
 );