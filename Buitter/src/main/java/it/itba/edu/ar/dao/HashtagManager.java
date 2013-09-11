package it.itba.edu.ar.dao;

import it.itba.edu.ar.connection.ConnectionManager;
import it.itba.edu.ar.connection.DatabaseException;
import it.itba.edu.ar.model.Hashtag;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
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
		System.out.println(new java.sql.Date(new Date(2013-1900, 8, 10).getTime()));
		List<Hashtag> trend = manager.trendingTopics(new Date(2013-1900, 8, 10));
		
		for (Hashtag hashtag : trend) {
			System.out.println(hashtag);
		}
		
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
					"SELECT h.hashtag, u.username, h.hashtagid, COUNT(b.buitid) as count , h.date " +
					"FROM Users as u, Hashtags as h, Buits as b, Buithash as bh " +
					"WHERE h.hashtagid = bh.hashtagid AND b.buitid = bh.buitid AND h.userid = u.userid " +
					"AND h.date > ? " +
					"GROUP BY h.hashtag, u.username, h.hashtagid " +
					"ORDER BY count DESC " +
					"LIMIT 10");
			stmt.setDate(1, new java.sql.Date(date.getTime()));
			ResultSet results = stmt.executeQuery();
			while (results.next()) {
				hashtags.add(new Hashtag(results.getInt(3),results.getString(1),
						results.getDate(5),results.getString(2)));
			}
			connection.close();
		} catch (SQLException e) {
			throw new DatabaseException(e.getMessage(), e);
		}
		return hashtags;
	}

	public List<Hashtag> hashtagForUser(String username) {
		List<Hashtag> hashtags = new ArrayList<Hashtag>();
		try {
			Connection connection = manager.getConnection();
			PreparedStatement stmt = connection.prepareStatement(
					"SELECT h.hashtag, u.username, h.hashtagid,  h.date " +
					"FROM Users as u, Hashtags as h, Buits as b, Buithash as bh " +
					"WHERE h.userid = u.userid AND u.username = ? " +
					"GROUP BY h.hashtag, u.username, h.hashtagid, h.date ");
			
			stmt.setString(1,username);

			ResultSet results = stmt.executeQuery();
			while (results.next()) {
				hashtags.add(new Hashtag(results.getInt(3),results.getString(1),
						results.getDate(5),results.getString(2)));
			}
			connection.close();
		} catch (SQLException e) {
			throw new DatabaseException(e.getMessage(), e);
		}
		
		return hashtags;
	}
	

	public void insertHashtag(Hashtag hashtag,int userid) {
		try {
			Connection connection = manager.getConnection();
			PreparedStatement stmt = connection.prepareStatement
					("INSERT INTO Hashtag(message,userid,date) " +
							"VALUES(?,?,CURRENT_DATE)");
			
			stmt.setString(1,hashtag.getHashtag());
			stmt.setInt(2, userid);

			stmt.execute();

			connection.close();
		} catch (SQLException e) {
			throw new DatabaseException(e.getMessage(), e);
		}
		
		
	}

}
