package it.itba.edu.ar.services.impl;

import it.itba.edu.ar.dao.UrlDao;
import it.itba.edu.ar.model.Buit;
import it.itba.edu.ar.model.Url;
import it.itba.edu.ar.services.UrlService;
import it.itba.edu.ar.servlets.ServletValidationException;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


public class UrlServiceImpl implements UrlService {

	private UrlDao urlDao;

	@Autowired
	public UrlServiceImpl(UrlDao urlDao) {
		this.urlDao = urlDao;
	}

	public void insertUrl(Url url) {
		if (url == null) {
			throw new ServletValidationException();
		}

		String hashedUrl = UUID.randomUUID().toString().substring(0, 4);
		String buiturl = "buit.li/" + hashedUrl;
		url.setBuiturl(buiturl);

		urlDao.insertUrl(url);
	}

	public boolean buitHasUrl(Buit buit) {
		if (buit == null) {
			throw new ServletValidationException();
		}

		if (urlDao.urlsForBuit(buit) != null)
			return true;
		return false;
	}

	public List<Url> urlsForBuit(Buit buit) {
		if (buit == null) {
			throw new ServletValidationException();
		}

		return urlDao.urlsForBuit(buit);
	}

}
