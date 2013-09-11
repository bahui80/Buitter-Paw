package it.itba.edu.ar.dao;

import it.itba.edu.ar.connection.ConnectionManager;
import it.itba.edu.ar.connection.DatabaseException;
import it.itba.edu.ar.model.User;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class UserManager implements UserDao {

	private static UserManager instance;
	private final ConnectionManager manager;
	
	private static final String driver = "org.postgresql.Driver";
	private static final String connectionString = "jdbc:postgresql://localhost/paw2";
	private static final String username = "paw";
	private static final String password = "paw";
	
	public static void main(String args[]){
		UserManager manager = UserManager.sharedInstance();
		
		manager.register(new User("Juan", "Buireo", "jujubuireo", "1234", 
			"Sexo,drogas, rocanrol", "Como se llama mi perro?", "Boris", 
			new Date(), null));
		
//		manager.updateUser(new User("Juan", "Buireo", "jujubuireo", "1234", 
//				"Vivir al limite", "Como se llama mi perro?", "Boris", 
//				new Date(0), null));
//		
//		System.out.println(manager.checkUserName("jujubuireo"));;
//		
//		manager.changePassword(new User("Juan", "Buireo", "jujubuireo", "12345", 
//				"Vivir al limite", "Como se llama mi perro?", "Boris", 
//				new Date(0), null));
//		
	}
	
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
				usr = new User(results.getNString(2),results.getNString(3),results.getNString(7),
						results.getNString(4),results.getNString(8),results.getNString(9),
						results.getNString(10),	results.getDate(11),results.getNString(12));
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
				usr = new User(results.getNString(2),results.getNString(3),results.getNString(7),
						results.getNString(4),results.getNString(8),results.getNString(9),
						results.getNString(10),	results.getDate(11),results.getNString(12));
			}
			connection.close();
		} catch (SQLException e) {
			throw new DatabaseException(e.getMessage(), e);
		}
		return usr;
	}

	public void login(User user) {
		User usr = null;
		try {
			Connection connection = manager.getConnection();
			PreparedStatement stmt = connection.prepareStatement(
					"SELECT . FROM Users WHERE username = ? AND password = ?");
			stmt.setString(1, user.getUsername());
			stmt.setString(2,user.getPassword());

			ResultSet results = stmt.executeQuery();
			if (results.next()) {
				usr = new User(results.getNString(2),results.getNString(3),results.getNString(7),
						results.getNString(4),results.getNString(8),results.getNString(9),
						results.getNString(10),	results.getDate(11),results.getNString(12));
			}
			connection.close();
		} catch (SQLException e) {
			throw new DatabaseException(e.getMessage(), e);
		}
	}

	public void register(User user) {
		try {
			Connection connection = manager.getConnection();
			PreparedStatement stmt = connection.prepareStatement
					("INSERT INTO Users(name,surname,password," +
							"date,username,description,secret_question," +
							"secret_answer) VALUES(?,?,?,?,?,?,?,?)");
			
			stmt.setString(1, user.getName());
			stmt.setString(2,user.getSurname());
			stmt.setString(3,user.getPassword());
			stmt.setDate(4,new java.sql.Date(user.getCreationDate().getTime()));
			stmt.setString(5, user.getUsername());
			stmt.setString(6, user.getDescription());
			stmt.setString(7, user.getSecretQuestion());
			stmt.setString(8, user.getSecretAnswer());

			stmt.executeUpdate();
			connection.commit();
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
			connection.commit();
			connection.close();
		} catch (SQLException e) {
			throw new DatabaseException(e.getMessage(), e);
		}
	}

	public void changePassword(User user) {
		this.updateUser(user);
	}

}
