package it.itba.edu.ar.web;

import it.itba.edu.ar.model.Hashtag;
import it.itba.edu.ar.model.Url;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ViewControllerHelper {

	public static List<String> getHashTags(String buit) {
		List<String> hashTags = new ArrayList<String>();
		String patternStr = "#([A-Za-z0-9_]+)";
		Pattern pattern = Pattern.compile(patternStr);
		Matcher matcher = pattern.matcher(buit);
		String result = "";

		while (matcher.find()) {
			result = matcher.group();
			hashTags.add(result.replace("#", ""));
		}

		return hashTags;
	}
	
	public static Set<String> getUsers(String buit) {
		Set<String> users = new HashSet<String>();
		String patternStr = "@([A-Za-z0-9]+)";
		Pattern pattern = Pattern.compile(patternStr);
		Matcher matcher = pattern.matcher(buit);
		String result = "";

		while (matcher.find()) {
			result = matcher.group();
			users.add(result.replace("@", ""));
		}

		return users;
	}

	public static String shortenBuit(String buit, List<Url> urls) {
		for (Url url : urls) {
			buit = buit.replaceFirst(url.getUrl(), url.getBuiturl());
		}
		return buit;
	}
	
	public static String prepareBuitUrl(String buit, List<Url> urls) {
		for (Url url : urls) {
			String replaceHTML = " <a href='" + url.getUrl()
					+ "' target='_blank'>" + url.getBuiturl() + "</a> ";
			buit = buit.replace(url.getBuiturl(), replaceHTML);
		}
		return buit;
	}

	public static String prepareBuitHashtag(String buit, List<Hashtag> hashtags) {
		for (Hashtag hashtag : hashtags) {
			String replaceHTML = " <a href=hashtag?name=" + hashtag.getHashtag() + ">"
					+ "#" +hashtag.getHashtag() + "</a>";
			buit = buit.replaceAll("#" + hashtag.getHashtag() + "((?:\\s)|(?:\\Z))", replaceHTML);
		}
		return buit;
	}
	
	public static String prepareBuitUser(String buit, Set<String> users) {
		for (String user : users) {
			String replaceHTML = " <a href=profile?name=" + user + ">"
					+ "@" + user + "</a>";
			buit = buit.replaceAll("@" + user + "((?:\\s)|(?:\\Z))", replaceHTML);
		}
		
		return buit;
	}

	public static List<String> getUrls(String buit) {
		List<String> urls = new ArrayList<String>();
		String patternStr = "\\b(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
		Pattern pattern = Pattern.compile(patternStr);
		Matcher matcher = pattern.matcher(buit);
		String result = "";

		while (matcher.find()) {
			result = matcher.group();
			urls.add(result);
		}

		return urls;
	}
	
	public static String cutUrl(String url) {
		if (url == null) {
			throw new IllegalArgumentException();
		}
		String hashedUrl = UUID.randomUUID().toString().substring(0, 4);
		String buiturl = "buit.li/" + hashedUrl;
		return buiturl;
	}
}