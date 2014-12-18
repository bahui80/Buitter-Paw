-- Users table --
ALTER TABLE Users ADD COLUMN backgroundimage bytea;
ALTER TABLE Users ADD COLUMN thumbnailphoto bytea;

-- New table publicity --
CREATE TABLE publicity (
  id serial PRIMARY KEY NOT NULL,
  clientname character varying(255),
  imageurl character varying(255),
  url character varying(255)
);

-- New table userlist --
CREATE TABLE userlist (
  id serial PRIMARY KEY NOT NULL,
  description character varying(100),
  name character varying(32) NOT NULL,
  owner_id integer REFERENCES Users(id)
 );

-- New table userlist_users --
CREATE TABLE userlist_users (
  userlistsin_id integer NOT NULL REFERENCES Userlist(id),
  users_id integer NOT NULL REFERENCES Users(id),
  PRIMARY KEY (userlistsin_id, users_id)
);

-- New table users_blacklist --
CREATE TABLE users_blacklist (
  blacklist_id integer NOT NULL REFERENCES Users(id),
  blacklistedby_id integer NOT NULL REFERENCES Users(id),
  PRIMARY KEY (blacklist_id, blacklistedby_id)
);
