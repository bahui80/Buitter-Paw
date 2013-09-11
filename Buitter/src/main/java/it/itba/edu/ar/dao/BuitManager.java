package it.itba.edu.ar.dao;

import it.itba.edu.ar.connection.ConnectionManager;
import it.itba.edu.ar.connection.DatabaseException;
import it.itba.edu.ar.model.Buit;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BuitManager implements BuitDao{

	private static BuitManager instance;
	private final ConnectionManager manager;
	
	private static final String driver = "org.postgresql.Driver";
	private static final String connectionString = "jdbc:postgresql://localhost/paw2";
	private static final String username = "paw";
	private static final String password = "paw";
	
	public static synchronized BuitManager sharedInstance(){
		if(instance == null){
			instance = new BuitManager();
		}
		return instance;
	}
	
	private BuitManager(){
		manager = new ConnectionManager(driver,connectionString , username, password);
	}
	
	public void buit(Buit buit, int userid) {
		try {
			Connection connection = manager.getConnection();
			PreparedStatement stmt = connection.prepareStatement
					("INSERT INTO Buits(message,userid,date) " +
							"VALUES(?,?,?)");
			
			stmt.setString(1,buit.getMessage());
			stmt.setInt(2, userid);

			stmt.execute();

			connection.close();
		} catch (SQLException e) {
			throw new DatabaseException(e.getMessage(), e);
		}
		
	}

	public List<Buit> getUserBuits(String username) {
		List<Buit> buits = new ArrayList<Buit>();
		try {
			Connection connection = manager.getConnection();
			PreparedStatement stmt = connection.prepareStatement(
					"SELECT b.buitid, b.message, u.username, b.date " +
					"FROM Users as u,Buits as b " +
					"WHERE u.name = ? AND u.userid = b.userid");
			stmt.setString(1, username);
			
			ResultSet results = stmt.executeQuery();
			if (results.next()) {
				buits.add(new Buit(results.getInt(1),results.getString(2), results.getString(3), results.getDate(4)));
			}
			connection.close();
		} catch (SQLException e) {
			throw new DatabaseException(e.getMessage(), e);
		}
		return buits;
	}

	public List<Buit> getHashtagBuits(String hashtag) {
		List<Buit> buits = new ArrayList<Buit>();
		try {
			Connection connection = manager.getConnection();
			PreparedStatement stmt = connection.prepareStatement(
					"SELECT b.buitid, b.message, u.username, b.date " +
					"FROM Hashtags as h,Buits as b, Buithash ash bh " +
					"WHERE h.hashtagid = bh.hashtagid AND b.buitid = bh.buitid " +
					"AND h.hashtag = ?");
			
			stmt.setString(1, hashtag);
			
			ResultSet results = stmt.executeQuery();
			if (results.next()) {
				buits.add(new Buit(results.getInt(1),results.getString(2), results.getString(3), results.getDate(4)));
			}
			connection.close();
		} catch (SQLException e) {
			throw new DatabaseException(e.getMessage(), e);
		}
		return buits;
	}

	public void removeBuit(Buit buit, int userid) {
		try {
			Connection connection = manager.getConnection();
			PreparedStatement stmt = connection.prepareStatement
					("DELETE FROM Buits " +
					"WHERE buitid = ? AND userid = ?");
			
			stmt.setInt(1,buit.getId());
			stmt.setInt(2, userid);
			stmt.execute();

			connection.close();
		} catch (SQLException e) {
			throw new DatabaseException(e.getMessage(), e);
		}
		
	}

}
