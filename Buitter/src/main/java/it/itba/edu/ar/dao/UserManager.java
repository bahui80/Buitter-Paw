package it.itba.edu.ar.dao;

import it.itba.edu.ar.connection.ConnectionManager;
import it.itba.edu.ar.connection.DatabaseException;
import it.itba.edu.ar.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
			"12-10-2013", null));
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
	
	public boolean checkUserName(String username) {
		// TODO Auto-generated method stub
		return false;
	}

	public User login(User user) {
		User usr = null;
		try {
			Connection connection = manager.getConnection();
			PreparedStatement stmt = connection.prepareStatement("SELECT . FROM Users WHERE username = ? AND password = ?");
			stmt.setString(1, user.getUsername());
			stmt.setString(2,user.getPassword());

			ResultSet results = stmt.executeQuery();
			if (results.next()) {
				usr = new User(results.getNString(2),results.getNString(3),results.getNString(7),
						results.getNString(4),results.getNString(8),results.getNString(9),
						results.getNString(10),	results.getNString(11),results.getNString(12));
			}
			connection.close();
		} catch (SQLException e) {
			throw new DatabaseException(e.getMessage(), e);
		}
		return usr;
	}

	public void register(User user) {
		try {
			Connection connection = manager.getConnection();
			PreparedStatement stmt = connection.prepareStatement
					("INSERT INTO Users VALUES(?,?,?,?,?,?,?,?,?,?)");
			stmt.setString(1, user.getName());
			stmt.setString(2,user.getSurname());
			stmt.setString(3,user.getPassword());
			stmt.setString(4,user.getCreationDate());
			stmt.setString(5,user.getCreationDate());
			stmt.setString(6, null);
			stmt.setString(7, user.getUsername());
			stmt.setString(8, user.getDescription());
			stmt.setString(9, user.getSecretQuestion());
			stmt.setString(10, user.getSecretAnswer());
			
			stmt.executeQuery();

			connection.close();
		} catch (SQLException e) {
			throw new DatabaseException(e.getMessage(), e);
		}

	}

	public void updateUser(User user) {
		try {
			Connection connection = manager.getConnection();
			PreparedStatement stmt = connection.prepareStatement
					("UPDATE Users SET VALUES(?,?,?,?,?,?,?,?,?,?)" +
							"WHERE username = ?");
			stmt.setString(1, user.getName());
			stmt.setString(2,user.getSurname());
			stmt.setString(3,user.getPassword());
			stmt.setString(4,user.getCreationDate());
			stmt.setString(5,user.getCreationDate());
			stmt.setString(6, null);
			stmt.setString(7, user.getUsername());
			stmt.setString(8, user.getDescription());
			stmt.setString(9, user.getSecretQuestion());
			stmt.setString(10, user.getSecretAnswer());
			stmt.setString(11, user.getUsername());
			
			stmt.executeQuery();

			connection.close();
		} catch (SQLException e) {
			throw new DatabaseException(e.getMessage(), e);
		}
	}

	public void changePassword(User user) {
		this.updateUser(user);
	}

}
