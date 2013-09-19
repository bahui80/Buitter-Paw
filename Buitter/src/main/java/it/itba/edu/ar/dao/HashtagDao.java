package it.itba.edu.ar.dao;

import it.itba.edu.ar.model.Hashtag;

import java.sql.Timestamp;
import java.util.List;

public interface HashtagDao {

		public List<Hashtag> getHashtagsSinceDate(Timestamp date, int quantity);
		public List<Hashtag> hashtagForUser(String username);
		public void insertHashtag(Hashtag hashtag);
		public Hashtag getHashtag(String hashtagname);
		public Integer getHashtagId(String hashtagname);
		public void insertHashtagBuit(int hashtagid, int buitid);

}
