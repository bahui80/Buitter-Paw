package it.itba.edu.ar.dao;

import it.itba.edu.ar.connection.ConnectionManager;
import it.itba.edu.ar.connection.DatabaseException;
import it.itba.edu.ar.model.Hashtag;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql	.Date;
import java.util.List;

public class HashtagManager implements HashtagDao{

	private static HashtagManager instance;
	private final ConnectionManager manager;
	
	private static final String driver = "org.postgresql.Driver";
	private static final String connectionString = "jdbc:postgresql://localhost/paw2";
	private static final String username = "paw";
	private static final String password = "paw";
	
	public static void main(String args[]){
		HashtagManager manager = HashtagManager.sharedInstance();

		manager.trendingTopics(new Date(2013, 10, 10));
	}
	
	public static synchronized HashtagManager sharedInstance(){
		if(instance == null){
			instance = new HashtagManager();
		}
		return instance;
	}
	
	private HashtagManager(){
		manager = new ConnectionManager(driver,connectionString , username, password);
	}
	
	public List<Hashtag> trendingTopics(Date date) {
		List<Hashtag> hashtags = new ArrayList<Hashtag>();
		try {
			Connection connection = manager.getConnection();
			PreparedStatement stmt = connection.prepareStatement(
					"SELECT hashtag, username, h1.hashtagid, COUNT(b1.buitid) as count " +
					"FROM Buithash AS bh1, Hashtags AS h1, Users AS u1, Buits AS b1" +
					" WHERE h1.hashtagid = bh1.hashtagid AND u1.userid = b1.userid" +
					" AND b1.buitid = bh1.buitid AND h1.date > ?" +
					" GROUP BY hashtag" +
					" ORDER BY count" +
					" LIMIT 10");
			stmt.setDate(1, date);
			ResultSet results = stmt.executeQuery();
			if (results.next()) {
		//		hashtags.add(new Hashtag(results.getInt(3),results.getString(1),
		//				results.getInt(4),results.getString(2)));
			}
			connection.close();
		} catch (SQLException e) {
			throw new DatabaseException(e.getMessage(), e);
		}
		return hashtags;
	}

	public List<Hashtag> hashtagForUser(String username) {
		return null;
	}

	public Hashtag randomHashtags() {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Hashtag> trendingTopics(java.util.Date date) {
		// TODO Auto-generated method stub
		return null;
	}

}
