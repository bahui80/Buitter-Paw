package it.itba.edu.ar.services;

import it.itba.edu.ar.dao.UrlDao;
import it.itba.edu.ar.dao.UrlManager;
import it.itba.edu.ar.model.Buit;
import it.itba.edu.ar.model.Url;
import it.itba.edu.ar.servlets.ServletValidationException;

import java.util.List;
import java.util.UUID;

public final class UrlServiceImpl {

	private static UrlServiceImpl instance;
	private static UrlDao urlManager;
//	public static synchronized UrlServiceImpl sharedInstance() {
//		if (instance == null) {
//			instance = new UrlServiceImpl();
//		}
//		return instance;
//	}

	public UrlServiceImpl(UrlDao urlDao) {
		this.urlManager = urlDao;
	}

	public void insertUrl(Url url) {
		if (url == null) {
			throw new ServletValidationException();
		}

		String hashedUrl = UUID.randomUUID().toString().substring(0, 4);
		String buiturl = "buit.li/" + hashedUrl;
		url.setBuiturl(buiturl);

		urlManager.insertUrl(url);
	}

	public boolean buitHasUrl(Buit buit) {
		if (buit == null) {
			throw new ServletValidationException();
		}

		if (urlManager.urlsForBuit(buit) != null)
			return true;
		return false;
	}

	public List<Url> urlsForBuit(Buit buit) {
		if (buit == null) {
			throw new ServletValidationException();
		}

		return urlManager.urlsForBuit(buit);
	}

}
