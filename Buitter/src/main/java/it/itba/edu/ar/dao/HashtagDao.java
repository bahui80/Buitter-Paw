package it.itba.edu.ar.dao;

import it.itba.edu.ar.model.Hashtag;

import java.util.Date;
import java.util.List;

public interface HashtagDao {

		public List<Hashtag> getHashtagsSinceDate(Date date);
		public List<Hashtag> hashtagForUser(String username);
		public void insertHashtag(Hashtag hashtag);
		public Hashtag getHashtag(String hashtagname);
		public Integer getHashtagId(String hashtagname);
		public void insertHashtagBuit(int hashtagid, int buitid);

}
