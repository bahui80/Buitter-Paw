package it.itba.edu.ar.services;

import it.itba.edu.ar.dao.BuitManager;
import it.itba.edu.ar.dao.HashtagManager;
import it.itba.edu.ar.model.Buit;
import it.itba.edu.ar.model.Hashtag;
import it.itba.edu.ar.model.User;

import java.util.Date;
import java.util.List;

public class BuitService {

	public static void removeBuit(Buit buit, User user) {
		BuitManager buitManager = BuitManager.sharedInstance();

		buitManager.removeBuit(buit.getId(), user.getId());
	}

	public static void buit(Buit buit, User user) {
		BuitManager buitManager = BuitManager.sharedInstance();

		buitManager.buit(buit, user.getId());
	}

	public static List<Hashtag> trendingTopics(Date date) {
		HashtagManager hashtagManager = HashtagManager.sharedInstance();
		return hashtagManager.trendingTopics(date);
	}

}
