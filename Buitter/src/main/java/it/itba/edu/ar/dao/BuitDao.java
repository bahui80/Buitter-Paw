package it.itba.edu.ar.dao;

import it.itba.edu.ar.model.Buit;
import it.itba.edu.ar.model.User;

import java.util.List;

public interface BuitDao {
	
	public void buit(Buit buit);
	public Buit getBuit(String message, User user);
	public void removeBuit(int buitid);
	public List<Buit> getUserBuits(User user);
	public List<Buit> getHashtagBuits(String hashtag);
}
