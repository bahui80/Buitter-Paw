package it.itba.edu.ar.domain.buit;

import java.util.Date;
import java.util.List;

import it.itba.edu.ar.domain.user.User;

public interface BuitRepo {

	public void buit(String message, User user);
	public List<Hashtag> getHashtagsSinceDate(Date date, int quantity);
	public Hashtag getHashtag(String hashtag);
	public Buit getBuit(int buitid);
	public void addbuit(Buit buit);
	public void addHashtag(Hashtag hashtag);
	public void removeBuit(Buit buit, User user);
	public void rebuit(Buit buit, User user);
}
