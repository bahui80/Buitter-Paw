package it.itba.edu.ar.services;

import it.itba.edu.ar.dao.UrlManager;
import it.itba.edu.ar.model.Buit;
import it.itba.edu.ar.model.Url;

import java.security.SecureRandom;
import java.util.List;
import java.util.UUID;

public final class UrlService {

	private static UrlService instance;
	private SecureRandom random = new SecureRandom();

	public static synchronized UrlService sharedInstance() {
		if (instance == null) {
			instance = new UrlService();
		}
		return instance;
	}

	private UrlService() {

	}

	public void insertUrl(Url url) {
		UrlManager urlManager = UrlManager.sharedInstance();

		String hashedUrl = UUID.randomUUID().toString().substring(0, 4);
		String buiturl = "buit.li/" + hashedUrl;
		url.setBuiturl(buiturl);

		urlManager.insertUrl(url);
	}

	public boolean buitHasUrl(Buit buit) {
		UrlManager urlManager = UrlManager.sharedInstance();

		if (urlManager.urlsForBuit(buit) != null)
			return true;
		return false;
	}

	public List<Url> urlsForBuit(Buit buit) {
		UrlManager urlManager = UrlManager.sharedInstance();

		return urlManager.urlsForBuit(buit);
	}

}
