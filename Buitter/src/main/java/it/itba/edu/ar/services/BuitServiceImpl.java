
package it.itba.edu.ar.services;

import it.itba.edu.ar.dao.BuitDao;
import it.itba.edu.ar.dao.BuitManager;
import it.itba.edu.ar.dao.HashtagDao;
import it.itba.edu.ar.dao.HashtagManager;
import it.itba.edu.ar.model.Buit;
import it.itba.edu.ar.model.Hashtag;
import it.itba.edu.ar.model.User;
import it.itba.edu.ar.servlets.ServletValidationException;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

public class BuitServiceImpl{

	private static BuitServiceImpl instance;
	private static BuitDao buitManager;
	private static HashtagDao hashtagManager;
	
//	public static synchronized BuitServiceImpl sharedInstance(){
//		if(instance == null){
//			instance = new BuitServiceImpl();
//		}
//		return instance;
//	}
	
	public BuitServiceImpl(BuitDao buitDao, HashtagDao hashtagDao){
		this.buitManager = buitDao;
		this.hashtagManager = hashtagDao;
	}
	
	public void removeBuit(int buitid) {
		if (buitid <= 0) {
			throw new ServletValidationException();
		}
		
		buitManager.removeBuit(buitid);
	}

	public Buit buit(Buit buit) {
		if (buit == null) {
			throw new ServletValidationException();
		}
		
		buitManager.buit(buit);
		
		return buitManager.getBuit(buit.getMessage(),buit.getUser());
	}

	public List<Hashtag> trendingTopics(Date date, int quantity) {
		if (date == null || quantity <= 0) {
			throw new ServletValidationException();
		}
		return hashtagManager.getHashtagsSinceDate(new Timestamp(date.getTime()), quantity);
	}

	public List<Buit> getUserBuits(User user){
		if (user == null) {
			throw new ServletValidationException();
		}

		return buitManager.getUserBuits(user);
	}
	
	public void addHashtag(Hashtag hashtag, Buit buit){
		if (hashtag == null || buit == null) {
			throw new ServletValidationException();
		}
		
		Integer hashtagid = hashtagManager.getHashtagId(hashtag.getHashtag());
		if(hashtagid == null){
			hashtagManager.insertHashtag(hashtag);
		}
		hashtagid = hashtagManager.getHashtagId(hashtag.getHashtag());
		hashtagManager.insertHashtagBuit(hashtagid, buit.getId());
	}
	
	public Hashtag getHashtag(String hashtag){
		if (hashtag == null) {
			throw new ServletValidationException();
		}
		return hashtagManager.getHashtag(hashtag);
	}
	
	public List<Buit> getBuitsForHashtag(String hashtag){
		if (hashtag == null) {
			throw new ServletValidationException();
		}		
		return buitManager.getHashtagBuits(hashtag);
	}
}
