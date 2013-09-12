package it.itba.edu.ar.services;

import it.itba.edu.ar.dao.BuitManager;
import it.itba.edu.ar.dao.HashtagManager;
import it.itba.edu.ar.model.Buit;
import it.itba.edu.ar.model.Hashtag;
import it.itba.edu.ar.model.User;

import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BuitService {

	public static void main(String[] args) {
		parseBuit("hadhsajkdhsakjn#sadsada. HOla juanann");
	}
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

	public static String parseBuit(String buit) {
		String patternStr = "#([A-Za-z0-9_]+)";
		Pattern pattern = Pattern.compile(patternStr);
		Matcher matcher = pattern.matcher(buit);
		String result = "";
		
		// Search for Hashtags
		while (matcher.find()) {
			result = matcher.group();
			result = result.replace(" ", "");
			String search = result.replace("#", "");
			String searchHTML = "<a href='hashtag?name='" + search + "'>" + result + "</a>";
			buit = buit.replace(result, searchHTML);
		}
		
		return buit;
	}
}
