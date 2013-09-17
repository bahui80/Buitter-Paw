package it.itba.edu.ar.dao;

import it.itba.edu.ar.connection.ConnectionManager;
import it.itba.edu.ar.connection.DatabaseException;
import it.itba.edu.ar.model.Buit;
import it.itba.edu.ar.model.Url;
import it.itba.edu.ar.model.User;

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
					("INSERT INTO Urls(url, buiturl, buitid) VALUES(?,?,?)");
			
			stmt.setString(1,url.getUrl());
			stmt.setString(2,url.getBuiturl());
			stmt.setInt(3,url.getBuitid());
			
			stmt.executeUpdate();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DatabaseException(e.getMessage(), e);
		}
	}
	
	public void insertNewUrl(Url url) {
		try {
			Connection connection = manager.getConnection();
			PreparedStatement stmt = connection.prepareStatement
					("INSERT INTO Urls( url, buiturl, buitid) VALUES(?,?,?)");
			
			stmt.setString(1,url.getUrl());
			stmt.setString(2,url.getBuiturl());
			stmt.setInt(3,url.getBuitid());
			
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
	
	public Url getUrlForId(int id){
			Url url = null;
			try {
				Connection connection = manager.getConnection();
				PreparedStatement stmt = connection.prepareStatement(
						"SELECT * FROM Urls WHERE urlid = ?");
				stmt.setInt(1,id);

				ResultSet results = stmt.executeQuery();
				if (results.next()) {
					url = new Url(results.getInt(1),results.getString(2),results.getString(3),results.getInt(4));
				}
				connection.close();
			} catch (SQLException e) {
				throw new DatabaseException(e.getMessage(), e);
			}
			return url;
	}
	
	public Integer getIdForUrl(String url){
		Integer id = null;
		try {
			Connection connection = manager.getConnection();
			PreparedStatement stmt = connection.prepareStatement(
					"SELECT urlid FROM Urls WHERE url = ?");
			stmt.setString(1,url);

			ResultSet results = stmt.executeQuery();
			if (results.next()) {
				id = results.getInt(1);
			}
			connection.close();
		} catch (SQLException e) {
			throw new DatabaseException(e.getMessage(), e);
		}
		return id;
}
	
}
