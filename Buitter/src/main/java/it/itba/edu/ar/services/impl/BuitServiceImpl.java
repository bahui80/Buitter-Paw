package it.itba.edu.ar.services.impl;

import it.itba.edu.ar.dao.BuitDao;
import it.itba.edu.ar.dao.HashtagDao;
import it.itba.edu.ar.model.Buit;
import it.itba.edu.ar.model.Hashtag;
import it.itba.edu.ar.model.User;
import it.itba.edu.ar.services.BuitService;
import it.itba.edu.ar.servlets.ServletValidationException;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BuitServiceImpl implements BuitService {

	private BuitDao buitDao;
	private HashtagDao hashtagDao;

	@Autowired
	public BuitServiceImpl(BuitDao buitDao, HashtagDao hashtagDao) {
		this.buitDao = buitDao;
		this.hashtagDao = hashtagDao;
	}

	public void removeBuit(int buitid) {
		if (buitid <= 0) {
			throw new ServletValidationException();
		}

		buitDao.removeBuit(buitid);
	}

	public Buit buit(Buit buit) {
		if (buit == null) {
			throw new ServletValidationException();
		}

		buitDao.buit(buit);

		return buitDao.getBuit(buit.getMessage(), buit.getUser());
	}

	public List<Hashtag> trendingTopics(Date date, int quantity) {
		if (date == null || quantity <= 0) {
			throw new ServletValidationException();
		}
		return hashtagDao.getHashtagsSinceDate(
				new Timestamp(date.getTime()), quantity);
	}

	public List<Buit> getUserBuits(User user) {
		if (user == null) {
			throw new ServletValidationException();
		}

		return buitDao.getUserBuits(user);
	}

	public void addHashtag(Hashtag hashtag, Buit buit) {
		if (hashtag == null || buit == null) {
			throw new ServletValidationException();
		}

		Integer hashtagid = hashtagDao.getHashtagId(hashtag.getHashtag());
		if (hashtagid == null) {
			hashtagDao.insertHashtag(hashtag);
		}
		hashtagid = hashtagDao.getHashtagId(hashtag.getHashtag());
		hashtagDao.insertHashtagBuit(hashtagid, buit.getId());
	}

	public Hashtag getHashtag(String hashtag) {
		if (hashtag == null) {
			throw new ServletValidationException();
		}
		return hashtagDao.getHashtag(hashtag);
	}

	public List<Buit> getBuitsForHashtag(String hashtag) {
		if (hashtag == null) {
			throw new ServletValidationException();
		}
		return buitDao.getHashtagBuits(hashtag);
	}
}
