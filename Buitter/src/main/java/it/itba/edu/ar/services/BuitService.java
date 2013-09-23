package it.itba.edu.ar.services;

import it.itba.edu.ar.model.Buit;
import it.itba.edu.ar.model.Hashtag;
import it.itba.edu.ar.model.User;

import java.util.Date;
import java.util.List;

public interface BuitService {
	
	public void removeBuit(int buitid);
	public Buit buit(Buit buit) ;
	public List<Hashtag> trendingTopics(Date date, int quantity);
	public List<Buit> getUserBuits(User user);
	public void addHashtag(Hashtag hashtag, Buit buit);
	public Hashtag getHashtag(String hashtag);
	public List<Buit> getBuitsForHashtag(String hashtag);
	
}
