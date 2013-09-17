
package it.itba.edu.ar.services;

import it.itba.edu.ar.dao.BuitManager;
import it.itba.edu.ar.dao.HashtagManager;
import it.itba.edu.ar.model.Buit;
import it.itba.edu.ar.model.Hashtag;
import it.itba.edu.ar.model.User;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

public class BuitService{

	private static BuitService instance;
	
	public static synchronized BuitService sharedInstance(){
		if(instance == null){
			instance = new BuitService();
		}
		return instance;
	}
	
	private BuitService(){
	}
	
	public void removeBuit(int buitid) {
		BuitManager buitManager = BuitManager.sharedInstance();
		
		buitManager.removeBuit(buitid);
	}

	public Buit buit(Buit buit) {
		BuitManager buitManager = BuitManager.sharedInstance();
		System.out.println(buit.getMessage());
		System.out.println(buit.getMessage().length());
		buitManager.buit(buit);
		
		return buitManager.getBuit(buit.getMessage(),buit.getUser());
	}

	public List<Hashtag> trendingTopics(Date date) {
		HashtagManager hashtagManager = HashtagManager.sharedInstance();
		System.out.println(new Timestamp(date.getTime()));
		return hashtagManager.getHashtagsSinceDate(new Timestamp(date.getTime()));
	}

	public List<Buit> getUserBuits(User user){
		BuitManager buitManager = BuitManager.sharedInstance();

		return buitManager.getUserBuits(user);
	}
	
	public void addHashtag(Hashtag hashtag, Buit buit){
		HashtagManager hashtagManager = HashtagManager.sharedInstance();
		
		Integer hashtagid = hashtagManager.getHashtagId(hashtag.getHashtag());
		if(hashtagid == null){
			hashtagManager.insertHashtag(hashtag);
		}
		hashtagid = hashtagManager.getHashtagId(hashtag.getHashtag());
		hashtagManager.insertHashtagBuit(hashtagid, buit.getId());
	}
	
	public Hashtag getHashtag(String hashtag){
		HashtagManager hashtagManager = HashtagManager.sharedInstance();
		return hashtagManager.getHashtag(hashtag);
	}
	
	public List<Buit> getBuitsForHashtag(String hashtag){
		BuitManager buitManager = BuitManager.sharedInstance();
		
		return buitManager.getHashtagBuits(hashtag);
	}
}
