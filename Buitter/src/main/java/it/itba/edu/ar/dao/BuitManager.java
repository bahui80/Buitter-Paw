package it.itba.edu.ar.dao;

import it.itba.edu.ar.connection.ConnectionManager;
import it.itba.edu.ar.connection.DBInfo;
import it.itba.edu.ar.connection.DatabaseException;
import it.itba.edu.ar.model.Buit;
import it.itba.edu.ar.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BuitManager implements BuitDao{

	private static BuitManager instance;
	private final ConnectionManager manager;
	
	public static synchronized BuitManager sharedInstance(){
		if(instance == null){
			instance = new BuitManager();
		}
		return instance;
	}
	
	private BuitManager(){
		DBInfo info = DBInfo.sharedInstance();
		manager = new ConnectionManager(info.getDriver(),info.getConnectionString() , info.getUsername(), info.getPassword());
	}
	
	public void buit(Buit buit) {
		try {
			Connection connection = manager.getConnection();
			PreparedStatement stmt = connection.prepareStatement
					("INSERT INTO Buits(message,userid,date) " +
							"VALUES(?,?,current_timestamp)");
			
			stmt.setString(1,buit.getMessage());
			stmt.setInt(2, buit.getUser().getId());

			stmt.execute();

			connection.close();
		} catch (SQLException e) {
			throw new DatabaseException(e.getMessage(), e);
		}
		
	}
	
	public Buit getBuit(String message, User user){
		Buit buit = null;
		try {
			Connection connection = manager.getConnection();
			PreparedStatement stmt = connection.prepareStatement(
					"SELECT b.buitid, b.message, b.date " +
					"FROM Users as u,Buits as b " +
					"WHERE u.userid = ? AND u.userid = b.userid AND b.message = ? " +
					"AND b.date >= (SELECT MAX(b.date) " +
					"FROM Users as u,Buits as b " +
					"WHERE u.userid = ? AND u.userid = b.userid AND b.message = ? )");
			stmt.setInt(1, user.getId());
			stmt.setString(2, message);
			stmt.setInt(3,user.getId());
			stmt.setString(4, message);
			
			ResultSet results = stmt.executeQuery();
			if (results.next()) {

				buit = new Buit(results.getInt(1),results.getString(2), user, results.getTimestamp(3));

			}
			connection.close();
		} catch (SQLException e) {
			throw new DatabaseException(e.getMessage(), e);
		}
		return buit;
	}

	public List<Buit> getUserBuits(User user) {
		List<Buit> buits = new ArrayList<Buit>();
		try {
			Connection connection = manager.getConnection();
			PreparedStatement stmt = connection.prepareStatement(
					"SELECT b.buitid, b.message, b.date " +
					"FROM Users as u,Buits as b " +
					"WHERE u.username = ? AND u.userid = b.userid " +
					"ORDER BY b.date DESC");
			stmt.setString(1, user.getUsername());
			
			ResultSet results = stmt.executeQuery();
			while (results.next()) {

				buits.add(new Buit(results.getInt(1),results.getString(2), user, results.getTimestamp(3)));

			}
			connection.close();
		} catch (SQLException e) {
			throw new DatabaseException(e.getMessage(), e);
		}
		return buits;
	}
	
	/*
	 * new User(int id, String name, String surname, String username, String password, 
			String description, String secret_question, String secret_answer, 
			Date creationDate, String photo)
	 * new Buit(int id, String message, User user, Date date);
	 */
	public List<Buit> getHashtagBuits(String hashtag) {
		List<Buit> buits = new ArrayList<Buit>();
		try {
			Connection connection = manager.getConnection();
			PreparedStatement stmt = connection.prepareStatement(
					"SELECT b.buitid, b.message, u.userid, u.name, u.surname, u.username, " +
					"u.password, u.description, u.secret_question, u.secret_answer, " +
					"u.date, u.photo, " +
					"b.date " +
					"FROM Hashtags as h,Buits as b, Buithash as bh, Users as u " +
					"WHERE h.hashtagid = bh.hashtagid AND b.buitid = bh.buitid AND u.userid = b.userid " +
					"AND h.hashtag = ? " +
					"ORDER BY b.date DESC");
			
			stmt.setString(1, hashtag);
			
			ResultSet results = stmt.executeQuery();
			
			while (results.next()) {
				User user = new User(results.getInt(3), results.getString(4), 
						results.getString(5), results.getString(6), 
						results.getString(7), results.getString(8),
						results.getString(9), results.getString(10), 
						results.getTimestamp(11), results.getBytes(12));
				buits.add(new Buit(results.getInt(1),results.getString(2),user,results.getTimestamp(13)));
			}
			connection.close();
		} catch (SQLException e) {
			throw new DatabaseException(e.getMessage(), e);
		}
		return buits;
	}

	public void removeBuit(int buitid) {
		try {
			Connection connection = manager.getConnection();
			PreparedStatement stmt = connection.prepareStatement
					("DELETE FROM Buits " +
					"WHERE buitid = ?");
			
			stmt.setInt(1,buitid);
			stmt.execute();

			connection.close();
		} catch (SQLException e) {
			throw new DatabaseException(e.getMessage(), e);
		}
		
	}

}
