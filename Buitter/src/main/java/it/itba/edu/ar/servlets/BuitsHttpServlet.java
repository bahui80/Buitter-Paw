package it.itba.edu.ar.servlets;

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
			System.out.println(result.replace("#", ""));
		//	hashTags.add(result.replace("#", ""));
			
		}
		
		return hashTags;
	}
	
	public String prepareBuit(String buit) {
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
}
