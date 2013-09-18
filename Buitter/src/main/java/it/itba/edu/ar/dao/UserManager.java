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
	
	/*
	 * new User(int id, String name, String surname, String username, String password, 
			String description, String secret_question, String secret_answer, 
			String creationDate, String photo)
	*/
	public User getUserByUsername(String username) {
		User usr = null;
		try {
			Connection connection = manager.getConnection();
			PreparedStatement stmt = connection.prepareStatement(
					"SELECT * " +
					"FROM Users WHERE username = ?");
			stmt.setString(1,username);

			ResultSet results = stmt.executeQuery();
			if (results.next()) {
				usr = new User(results.getInt("userid"),results.getString("name"),results.getString("surname"),
						results.getString("username"),results.getString("password"),results.getString("description"),
						results.getString("secret_question"),
						results.getString("secret_answer"),results.getTimestamp("date"), results.getBytes("photo"));
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
					"SELECT * " +
					"FROM Users WHERE userid = ?");
			stmt.setInt(1,id);

			ResultSet results = stmt.executeQuery();
			if (results.next()) {
				usr = new User(results.getInt("userid"),results.getString("name"),results.getString("surname"),
						results.getString("username"),results.getString("password"),results.getString("description"),
						results.getString("secret_question"),
						results.getString("secret_answer"),results.getTimestamp("date"), results.getBytes("photo"));

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
					"SELECT * " +
					"FROM Users WHERE surname = ? ");
			stmt.setString(1, surname);

			ResultSet results = stmt.executeQuery();
			while (results.next()) {
				 usrs.add(new User(results.getInt("userid"),results.getString("name"),results.getString("surname"),
							results.getString("username"),results.getString("password"),results.getString("description"),
							results.getString("secret_question"),
							results.getString("secret_answer"),results.getTimestamp("date"), results.getBytes("photo")));
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
					"SELECT * " +
					"FROM Users WHERE name = ? ");
			stmt.setString(1, name);

			ResultSet results = stmt.executeQuery();
			while (results.next()) {
				 usrs.add(new User(results.getInt("userid"),results.getString("name"),results.getString("surname"),
							results.getString("username"),results.getString("password"),results.getString("description"),
							results.getString("secret_question"),
							results.getString("secret_answer"),results.getTimestamp("date"), results.getBytes("photo")));
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
					"SELECT * " +
					"FROM Users WHERE username = ? AND password = ?");
			stmt.setString(1, username);
			stmt.setString(2,password);

			ResultSet results = stmt.executeQuery();
			if (results.next()) {
				usr = new User(results.getInt("userid"),results.getString("name"),results.getString("surname"),
						results.getString("username"),results.getString("password"),results.getString("description"),
						results.getString("secret_question"),
						results.getString("secret_answer"),results.getTimestamp("date"), results.getBytes("photo"));
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
							"secret_answer) VALUES(?,?,?,current_timestamp,?,?,?,?,?)");
			
			stmt.setString(1, user.getName());
			stmt.setString(2,user.getSurname());
			stmt.setString(3,user.getPassword());
			stmt.setBytes(4, user.getPhoto());
			stmt.setString(5, user.getUsername());
			stmt.setString(6, user.getDescription());
			stmt.setString(7, user.getSecretQuestion());
			stmt.setString(8, user.getSecretAnswer());
			
			stmt.executeUpdate();
			connection.close();
		} catch (SQLException e) {
			throw new DatabaseException(e.getMessage(), e);
		}

	}

	
	public void updateUser(User user) {
		try {
			Connection connection = manager.getConnection();
			PreparedStatement stmt = connection.prepareStatement
					("UPDATE Users SET name =  ?, surname = ?, password = ?," +
							"description = ?, secret_question = ?, secret_answer = ?, photo = ?" +
							" WHERE username = ?" );
			
			stmt.setString(1, user.getName());
			stmt.setString(2,user.getSurname());
			stmt.setString(3,user.getPassword());
			stmt.setString(4, user.getDescription());
			stmt.setString(5, user.getSecretQuestion());
			stmt.setString(6, user.getSecretAnswer());
			stmt.setBytes(7, user.getPhoto());
			stmt.setString(8, user.getUsername());
			
			stmt.executeUpdate();
			connection.close();
		} catch (SQLException e) {
			throw new DatabaseException(e.getMessage(), e);
		}
	}

	public List<User> getAllUsers() {
		List<User> usrs = new ArrayList<User>();
		try {
			Connection connection = manager.getConnection();
			PreparedStatement stmt = connection.prepareStatement(
					"SELECT * " +
					"FROM Users " +
					"ORDER BY surname, name, username");

			ResultSet results = stmt.executeQuery();
			while (results.next()) {
				 usrs.add(new User(results.getInt("userid"),results.getString("name"),results.getString("surname"),
							results.getString("username"),results.getString("password"),results.getString("description"),
							results.getString("secret_question"),
							results.getString("secret_answer"),results.getTimestamp("date"), results.getBytes("photo")));
			}
			connection.close();
		} catch (SQLException e) {
			throw new DatabaseException(e.getMessage(), e);
		}
		return usrs;
	}

	public List<User> getAllUsersMatching(String query) {
		List<User> usrs = new ArrayList<User>();
		try {
			Connection connection = manager.getConnection();
			PreparedStatement stmt = connection.prepareStatement(
					"SELECT * " +
					"FROM Users " +
					"WHERE ( (lower(name) LIKE ANY ( SELECT '%' || ? ||'%' FROM Users ) ) OR " +
					"(lower(surname) LIKE ANY ( SELECT  '%' || ? ||'%' FROM Users ) )  OR " +
					"(lower(username) LIKE ANY ( SELECT '%' || ? ||'%' FROM Users ) ) )" +
					"ORDER BY surname, name, username");
			stmt.setString(1, query.toLowerCase());
			stmt.setString(2, query.toLowerCase());
			stmt.setString(3, query.toLowerCase());

			ResultSet results = stmt.executeQuery();
			while (results.next()) {
				 usrs.add(new User(results.getInt("userid"),results.getString("name"),results.getString("surname"),
						results.getString("username"),results.getString("password"),results.getString("description"),
						results.getString("secret_question"),
						results.getString("secret_answer"),results.getTimestamp("date"), results.getBytes("photo")));
			}
			connection.close();
		} catch (SQLException e) {
			throw new DatabaseException(e.getMessage(), e);
		}
		return usrs;
	}
	
}
