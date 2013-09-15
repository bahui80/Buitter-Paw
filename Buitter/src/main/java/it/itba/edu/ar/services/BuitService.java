
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

	public void buit(Buit buit) {
		BuitManager buitManager = BuitManager.sharedInstance();

		buitManager.buit(buit);
	}

	public List<Hashtag> trendingTopics(Date date) {
		HashtagManager hashtagManager = HashtagManager.sharedInstance();
		return hashtagManager.getHashtagsSinceDate(date);
	}

	public List<Buit> getUserBuits(User user){
		BuitManager buitManager = BuitManager.sharedInstance();

		return buitManager.getUserBuits(user);
	}
	
	public void addHashtag(Hashtag hashtag){
		HashtagManager hashtagManager = HashtagManager.sharedInstance();
		hashtagManager.insertHashtag(hashtag);
	}
}
