package it.itba.edu.ar.dao;

import it.itba.edu.ar.connection.ConnectionManager;
import it.itba.edu.ar.connection.DatabaseException;
import it.itba.edu.ar.model.Buit;
import it.itba.edu.ar.model.Url;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UrlManager implements UrlDao{

	private static UrlManager instance;
	private static ConnectionManager manager;
	
	private static final String driver = "org.postgresql.Driver";
	private static final String connectionString = "jdbc:postgresql://localhost/paw2";
	private static final String username = "paw";
	private static final String password = "paw";
	
	public static synchronized UrlManager sharedInstance(){
		if(instance == null){
			instance = new UrlManager();
		}
		return instance;
	}
	
	private UrlManager(){
		manager = new ConnectionManager(driver,connectionString , username, password);
	}

	public void insertUrl(Url url) {
		try {
			Connection connection = manager.getConnection();
			PreparedStatement stmt = connection.prepareStatement
					("INSERT INTO Urls(urlid, url, buiturl, buitid) VALUES(?,?,?,?)");
			
			stmt.setInt(1, url.getUrlid());
			stmt.setString(2,url.getUrl());
			stmt.setString(3,url.getBuiturl());
			stmt.setInt(4,url.getBuitid());
			
			stmt.executeUpdate();
			connection.close();
		} catch (SQLException e) {
			throw new DatabaseException(e.getMessage(), e);
		}

		
	}

	public List<Url> urlsForBuit(Buit buit) {
		List<Url> urls = new ArrayList<Url>();
		try {
			Connection connection = manager.getConnection();
			PreparedStatement stmt = connection.prepareStatement(
					"SELECT urlid, url, buiturl, buitid " +
					"FROM urls" +
					"WHERE buitid = ?");
			stmt.setInt(1, buit.getId());
			
			ResultSet results = stmt.executeQuery();
			while (results.next()) {
				
				
				urls.add(new Url(results.getInt(1), results.getString(2), 
						results.getString(3), results.getInt(4)));
			}
			connection.close();
		} catch (SQLException e) {
			throw new DatabaseException(e.getMessage(), e);
		}
		return urls;
	}
	
	
	
}
