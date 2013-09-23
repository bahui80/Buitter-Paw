package it.itba.edu.ar.services;

import it.itba.edu.ar.model.Buit;
import it.itba.edu.ar.model.Url;

import java.util.List;

public interface UrlService {

	public void insertUrl(Url url);
	public boolean buitHasUrl(Buit buit);
	public List<Url> urlsForBuit(Buit buit);
	
}
