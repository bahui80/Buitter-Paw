package it.itba.edu.ar.servlets;

import it.itba.edu.ar.model.Url;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServlet;

@SuppressWarnings("serial")
public class BuitsHttpServlet extends HttpServlet {
	
	public List<String> getHashTags(String buit) {
		List<String> hashTags = new ArrayList<String>();
		String patternStr = "#([A-Za-z0-9_]+)";
		Pattern pattern = Pattern.compile(patternStr);
		Matcher matcher = pattern.matcher(buit);
		String result = "";
		
		while(matcher.find()) {
			result = matcher.group();
			hashTags.add(result.replace("#", ""));
		}
		
		return hashTags;
	}
	
	public String prepareBuitUrl(String buit, List<Url> urls) {
		for(Url url: urls) {
			String searchHTML = "<a href='" + url.getUrl() + "' target='_blank'>" + url.getBuiturl() + "</a>";
			buit = buit.replace(url.getUrl(),searchHTML);
		}
		return buit;
	}
	
	
	public String prepareBuitHashtag(String buit) {
		String patternStr = "#([A-Za-z0-9_]+)";
		Pattern pattern = Pattern.compile(patternStr);
		Matcher matcher = pattern.matcher(buit);
		String result = "";
		
		// Search for Hashtags
		while (matcher.find()) {
			result = matcher.group();
			result = result.replace(" ", "");
			String search = result.replace("#", "");
			String searchHTML = "<a href='hashtag?name=" + search + "'>" + result + "</a>";
			buit = buit.replace(result, searchHTML);
		}
		
		return buit;
	}
	
	public List<String> getUrls(String buit) {
		List<String> urls = new ArrayList<String>();
		String patternStr = "\\b(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
		Pattern pattern = Pattern.compile(patternStr);
		Matcher matcher = pattern.matcher(buit);
		String result = "";
		
		while(matcher.find()) {
			result = matcher.group();
			urls.add(result);
		}
		
		return urls;
	}
}
