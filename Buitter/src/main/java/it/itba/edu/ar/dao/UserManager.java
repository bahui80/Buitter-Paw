package it.itba.edu.ar.dao;

import it.itba.edu.ar.connection.ConnectionManager;
import it.itba.edu.ar.connection.DatabaseException;
import it.itba.edu.ar.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserManager implements UserDao {

	private static UserManager instance;
	private static ConnectionManager manager;
	
	private static final String driver = "org.postgresql.Driver";
	private static final String connectionString = "jdbc:postgresql://localhost/paw2";
	private static final String username = "paw";
	private static final String password = "paw";
	
	public static synchronized UserManager sharedInstance(){
		if(instance == null){
			instance = new UserManager();
		}
		return instance;
	}
	
	private UserManager(){
		manager = new ConnectionManager(driver,connectionString , username, password);
	}
	
	public User getUserByUsername(String username) {
		User usr = null;
		try {
			Connection connection = manager.getConnection();
			PreparedStatement stmt = connection.prepareStatement(
					"SELECT * FROM Users WHERE username = ?");
			stmt.setString(1,username);

			ResultSet results = stmt.executeQuery();
			if (results.next()) {
				usr = new User(results.getInt(1),results.getString(2),results.getString(3),results.getString(7),
						results.getString(4),results.getString(8),results.getString(9),
						results.getString(10),results.getDate(5), results.getBytes(6));
			}
			connection.close();
		} catch (SQLException e) {
			throw new DatabaseException(e.getMessage(), e);
		}
		return usr;
	}
	
	public User getUserById(int id) {
		User usr = null;
		try {
			Connection connection = manager.getConnection();
			PreparedStatement stmt = connection.prepareStatement(
					"SELECT * FROM Users WHERE userid = ?");
			stmt.setInt(1,id);

			ResultSet results = stmt.executeQuery();
			if (results.next()) {
				usr = new User(results.getInt(1),results.getString(2),results.getString(3),results.getString(7),
						results.getString(4),results.getString(8),results.getString(9),
						results.getString(10),results.getDate(5), results.getBytes(6));

			}
			connection.close();
		} catch (SQLException e) {
			throw new DatabaseException(e.getMessage(), e);
		}
		return usr;
	}
	
	public List<User> getUsersBySurname(String surname) {
		List<User> usrs = new ArrayList<User>();
		try {
			Connection connection = manager.getConnection();
			PreparedStatement stmt = connection.prepareStatement(
					"SELECT * FROM Users WHERE surname = ? ");
			stmt.setString(1, surname);

			ResultSet results = stmt.executeQuery();
			while (results.next()) {
				 usrs.add(new User(results.getInt(1),results.getString(2),results.getString(3),results.getString(7),
						results.getString(4),results.getString(8),results.getString(9),
						results.getString(10),results.getDate(5), results.getBytes(6)));
			}
			connection.close();
		} catch (SQLException e) {
			throw new DatabaseException(e.getMessage(), e);
		}
		return usrs;
	}
	
	public List<User> getUsersByName(String name) {
		List<User> usrs = new ArrayList<User>();
		try {
			Connection connection = manager.getConnection();
			PreparedStatement stmt = connection.prepareStatement(
					"SELECT * FROM Users WHERE name = ? ");
			stmt.setString(1, name);

			ResultSet results = stmt.executeQuery();
			while (results.next()) {
				 usrs.add(new User(results.getInt(1),results.getString(2),results.getString(3),results.getString(7),
						results.getString(4),results.getString(8),results.getString(9),
						results.getString(10),results.getDate(5), results.getBytes(6)));
			}
			connection.close();
		} catch (SQLException e) {
			throw new DatabaseException(e.getMessage(), e);
		}
		return usrs;
	}

	public User getUserByUsernameAndPassword(String username, String password) {
		User usr = null;
		try {
			Connection connection = manager.getConnection();
			PreparedStatement stmt = connection.prepareStatement(
					"SELECT * FROM Users WHERE username = ? AND password = ?");
			stmt.setString(1, username);
			stmt.setString(2,password);

			ResultSet results = stmt.executeQuery();
			if (results.next()) {
				usr = new User(results.getInt(1),results.getString(2),results.getString(3),results.getString(7),
						results.getString(4),results.getString(8),results.getString(9),
						results.getString(10),results.getDate(5), results.getBytes(6));
			}
			connection.close();
		} catch (SQLException e) {
			throw new DatabaseException(e.getMessage(), e);
		}
		return usr;
	}

	public void insertUser(User user) {
		try {
			Connection connection = manager.getConnection();
			PreparedStatement stmt = connection.prepareStatement
					("INSERT INTO Users(name,surname,password," +
							"date,photo,username,description,secret_question," +
							"secret_answer) VALUES(?,?,?,?,?,?,?,?,?)");
			
			stmt.setString(1, user.getName());
			stmt.setString(2,user.getSurname());
			stmt.setString(3,user.getPassword());
			stmt.setDate(4,new java.sql.Date(user.getCreationDate().getTime()));
			stmt.setBytes(5, user.getPhoto());
			stmt.setString(6, user.getUsername());
			stmt.setString(7, user.getDescription());
			stmt.setString(8, user.getSecretQuestion());
			stmt.setString(9, user.getSecretAnswer());
			
			stmt.executeUpdate();
			connection.close();
		} catch (SQLException e) {
			throw new DatabaseException(e.getMessage(), e);
		}

	}

	
	// TODO modificar la atualizacion (agregar photo)
	public void updateUser(User user) {
		try {
			Connection connection = manager.getConnection();
			PreparedStatement stmt = connection.prepareStatement
					("UPDATE Users SET name =  ?, surname = ?, password = ?," +
							"description = ?, secret_question = ?, secret_answer = ?" +
							" WHERE username = ?" );
			
			stmt.setString(1, user.getName());
			stmt.setString(2,user.getSurname());
			stmt.setString(3,user.getPassword());
			stmt.setString(4, user.getDescription());
			stmt.setString(5, user.getSecretQuestion());
			stmt.setString(6, user.getSecretAnswer());
			
			stmt.setString(7, user.getUsername());
			
			stmt.executeUpdate();
			connection.close();
		} catch (SQLException e) {
			throw new DatabaseException(e.getMessage(), e);
		}
	}
	
}
