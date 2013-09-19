package it.itba.edu.ar.dao;

import it.itba.edu.ar.connection.ConnectionManager;
import it.itba.edu.ar.connection.DatabaseException;
import it.itba.edu.ar.model.Hashtag;
import it.itba.edu.ar.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class HashtagManager implements HashtagDao{

	private static HashtagManager instance;
	private final ConnectionManager manager;
	
	private static final String driver = "org.postgresql.Driver";
	private static final String connectionString = "jdbc:postgresql://localhost/paw2";
	private static final String username = "paw";
	private static final String password = "paw";
	
	public static synchronized HashtagManager sharedInstance(){
		if(instance == null){
			instance = new HashtagManager();
		}
		return instance;
	}
	
	private HashtagManager(){
		manager = new ConnectionManager(driver,connectionString , username, password);
	}
	
	/*
	 * Hashtag(int id, String hashtag, Date date, User user)
	 * User(int id, String name, String surname, String username, String password, 
			String description, String secret_question, String secret_answer, 
			Date creationDate, byte photo)
	 */
	public List<Hashtag> getHashtagsSinceDate(Timestamp date, int quantity) {
		List<Hashtag> hashtags = new ArrayList<Hashtag>();
		try {
			Connection connection = manager.getConnection();
			PreparedStatement stmt = connection.prepareStatement(
					"SELECT h.hashtag, u.userid, u.name, u.surname, u.username, u.password, " +
					"u.description, u.secret_question, u.secret_answer, u.date, u.photo, " +
					"h.hashtagid, COUNT(b.buitid) as count , h.date " +
					"FROM Users as u, Hashtags as h, Buits as b, Buithash as bh " +
					"WHERE h.hashtagid = bh.hashtagid AND b.buitid = bh.buitid AND h.userid = u.userid " +
					"AND b.date > ? " +
					"GROUP BY h.hashtag, u.userid, u.username, h.hashtagid " +
					"ORDER BY count DESC " +
					"LIMIT ?");
			stmt.setTimestamp(1, date);
			stmt.setInt(2, quantity);
			ResultSet results = stmt.executeQuery();
			while (results.next()) {
				User user = new User(results.getInt(2), results.getString(3), 
						results.getString(4), results.getString(5), 
						results.getString(6), results.getString(7),
						results.getString(8), results.getString(9), 
						results.getTimestamp(10), results.getBytes(11));
				
				hashtags.add(new Hashtag(results.getInt(12),results.getString(1),
						results.getTimestamp(14),user, results.getInt(13)));
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
					"SELECT h.hashtag, u.id, u.name, u.surname, u.username, u.password, " +
					"u.description, u.secret_question, u.secret_answer, u.date, u.photo, " +
					"h.hashtagid, COUNT(b.buitid) as count , h.date " +
					"FROM Users as u, Hashtags as h, Buits as b, Buithash as bh " +
					"WHERE h.userid = u.userid AND u.username = ? " +
					"GROUP BY h.hashtag, u.username, h.hashtagid, h.date ");
			
			stmt.setString(1,username);

			ResultSet results = stmt.executeQuery();
			while (results.next()) {
				User user = new User(results.getInt(2), results.getString(3), 
						results.getString(4), results.getString(5), 
						results.getString(6), results.getString(7),
						results.getString(8), results.getString(9), 
						results.getTimestamp(10), results.getBytes	(11));
				
				hashtags.add(new Hashtag(results.getInt(12),results.getString(1),
						results.getTimestamp(14),user, results.getInt(13)));
			}
			connection.close();
		} catch (SQLException e) {
			throw new DatabaseException(e.getMessage(), e);
		}
		
		return hashtags;
	}
	
	public void insertHashtagBuit(int hashtagid, int buitid){
		try {
			Connection connection = manager.getConnection();
			PreparedStatement stmt = connection.prepareStatement
					("INSERT INTO BuitHash	(buitid,hashtagid) " +
							"VALUES(?,?)");
			
			stmt.setInt(1,buitid);
			stmt.setInt(2, hashtagid);

			stmt.execute();

			connection.close();
		} catch (SQLException e) {
			throw new DatabaseException(e.getMessage(), e);
		}
	}
	
	public void insertHashtag(Hashtag hashtag) {
		try {
			Connection connection = manager.getConnection();
			PreparedStatement stmt = connection.prepareStatement
					("INSERT INTO Hashtags(hashtag,userid,date) " +
							"VALUES(?,?,current_timestamp)");
			
			stmt.setString(1,hashtag.getHashtag());
			stmt.setInt(2, hashtag.getUser().getId());

			stmt.execute();

			connection.close();
		} catch (SQLException e) {
			throw new DatabaseException(e.getMessage(), e);
		}
	}
	
	public Integer getHashtagId(String hashtagname){
		Integer hashtag = null;
		 try {
				Connection connection = manager.getConnection();
				PreparedStatement stmt = connection.prepareStatement(
						"SELECT h.hashtagid " +
						"FROM Hashtags as h " +
						"WHERE h.hashtag = ?");
			
				stmt.setString(1, hashtagname);
				
				ResultSet results = stmt.executeQuery();
				if (results.next()) {
					hashtag = results.getInt(1);
				}
				connection.close();
			} catch (SQLException e) {
				throw new DatabaseException(e.getMessage(), e);
			}
			
			return hashtag;
	 }
	
	 public Hashtag getHashtag(String hashtagname) {
		 Hashtag hashtag = null;
		 try {
				Connection connection = manager.getConnection();
				PreparedStatement stmt = connection.prepareStatement(
						"SELECT h.hashtag, u.userid, u.name, u.surname, u.username, u.password, " +
						"u.description, u.secret_question, u.secret_answer,u.date, u.photo, " +
						"h.hashtagid, COUNT(b.buitid) as count , h.date " +
						"FROM Users as u, Hashtags as h, Buits as b, Buithash as bh " +
						"WHERE h.userid = u.userid  AND h.hashtag = ?" +
						"GROUP BY h.hashtag, u.username, u.userid, h.hashtagid, h.date ");
				
				
				stmt.setString(1, hashtagname);
				
				ResultSet results = stmt.executeQuery();
				if (results.next()) {
					User user = new User(results.getInt(2), results.getString(3), 
							results.getString(4), results.getString(5), 
							results.getString(6), results.getString(7),
							results.getString(8), results.getString(9), 
							results.getTimestamp(10), results.getBytes(11));
					
					hashtag = new Hashtag(results.getInt(12),results.getString(1),
							results.getTimestamp(14),user, results.getInt(13));
				}
				connection.close();
			} catch (SQLException e) {
				throw new DatabaseException(e.getMessage(), e);
			}
			
			return hashtag;
	 }
}
