package it.itba.edu.ar.dao;

import it.itba.edu.ar.model.Buit;
import it.itba.edu.ar.model.Url;

import java.util.List;

public interface UrlDao {

	public void insertUrl(Url url);
	public void insertNewUrl(Url url);
	public List<Url> urlsForBuit(Buit buit);
	public Url getUrlForId(int id);

}

