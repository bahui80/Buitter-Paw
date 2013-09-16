
package it.itba.edu.ar.services;

import it.itba.edu.ar.dao.BuitManager;
import it.itba.edu.ar.dao.HashtagManager;
import it.itba.edu.ar.model.Buit;
import it.itba.edu.ar.model.Hashtag;
import it.itba.edu.ar.model.User;

import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
	
	public void removeBuit(Buit buit, User user) {
		BuitManager buitManager = BuitManager.sharedInstance();

		buitManager.removeBuit(buit.getId());
	}

	public Buit buit(Buit buit) {
		BuitManager buitManager = BuitManager.sharedInstance();

		buitManager.buit(buit);
		
		return buitManager.getBuit(buit.getMessage(),buit.getUser());
	}

	public List<Hashtag> trendingTopics(Date date) {
		HashtagManager hashtagManager = HashtagManager.sharedInstance();
		return hashtagManager.getHashtagsSinceDate(date);
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
