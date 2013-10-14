package it.itba.edu.ar.converters;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import it.itba.edu.ar.connection.ConnectionManager;
import it.itba.edu.ar.connection.DatabaseException;
import it.itba.edu.ar.model.User;

import org.springframework.core.convert.converter.Converter;

public class UsernameToUserConverter implements Converter<String, User>{

	private static ConnectionManager manager;
	
	public User convert(String username) {
		if(manager == null){
			manager = new ConnectionManager("org.postgresql.Driver","jdbc:postgresql://localhost/paw2" , "paw", "paw");
		}
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
	
}
