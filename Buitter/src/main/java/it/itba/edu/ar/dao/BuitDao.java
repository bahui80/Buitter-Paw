package it.itba.edu.ar.dao;

import it.itba.edu.ar.model.Buit;

import java.util.List;

public interface BuitDao {
	
	public void buit(Buit buit, int userid);
	public void removeBuit(int buitid, int userid);
	public List<Buit> getUserBuits(String username);
	public List<Buit> getHashtagBuits(String hashtag);
}
