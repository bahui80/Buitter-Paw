package it.itba.edu.ar.converters;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import it.itba.edu.ar.connection.ConnectionManager;
import it.itba.edu.ar.connection.DatabaseException;
import it.itba.edu.ar.model.Hashtag;
import it.itba.edu.ar.model.User;

import org.springframework.core.convert.converter.Converter;

public class StringToHashtagConverter implements Converter<String,Hashtag>{

	private static ConnectionManager manager;
	
	public Hashtag convert(String hashtagname) {
		if(manager == null){
			manager = new ConnectionManager("org.postgresql.Driver","jdbc:postgresql://localhost/paw2" , "paw", "paw");
		}
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
