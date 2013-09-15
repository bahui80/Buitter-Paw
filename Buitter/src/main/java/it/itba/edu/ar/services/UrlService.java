package it.itba.edu.ar.services;

import it.itba.edu.ar.dao.UrlManager;
import it.itba.edu.ar.model.Buit;
import it.itba.edu.ar.model.Url;

import java.util.List;

public final class UrlService {

	private static UrlService instance;
	
	public static synchronized UrlService sharedInstance(){
		if(instance == null){
			instance = new UrlService();
		}
		return instance;
	}
	
	private UrlService(){
		
	}
	
	public void insertUrl(Url url){
		UrlManager urlManager = UrlManager.sharedInstance();

		urlManager.insertUrl(url);
	}
	
	public boolean buitHashUrl(Buit buit){
		UrlManager urlManager = UrlManager.sharedInstance();

		if(urlManager.urlsForBuit(buit) != null)
			return true;
		return false;
	}
	
	public List<Url> urlsForBuit(Buit buit){
		UrlManager urlManager = UrlManager.sharedInstance();

		return urlManager.urlsForBuit(buit);
	}
	
	
	
}
