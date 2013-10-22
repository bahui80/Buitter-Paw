package it.itba.edu.ar.dao.impl;

import it.itba.edu.ar.dao.HashtagDao;
import it.itba.edu.ar.model.Hashtag;

import java.sql.Timestamp;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class HibernateHashtagDao extends HibernateGenericDao implements HashtagDao {

	@Autowired
	public HibernateHashtagDao(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}
	
	
	/*
	 * Hashtag(int id, String hashtag, Date date, User user)
	 * User(int id, String name, String surname, String username, String password, 
			String description, String secret_question, String secret_answer, 
			Date creationDate, byte photo)
	 */
	public List<Hashtag> getHashtagsSinceDate(Timestamp date, int quantity) {
		Query query = getSession().createQuery("" +
				"SELECT h.hashtag, u.userid, u.name, u.surname, u.username, u.password, " +
				"u.description, u.secret_question, u.secret_answer, u.date, u.photo, " +
				"h.hashtagid, COUNT(b.buitid) as count , h.date " +
				"FROM Users as u, Hashtags as h, Buits as b, Buithash as bh " +
				"WHERE h.hashtagid = bh.hashtagid AND b.buitid = bh.buitid AND h.userid = u.userid " +
				"AND b.date > ? " +
				"GROUP BY h.hashtag, u.userid, u.username, h.hashtagid " +
				"ORDER BY count DESC " +
					"LIMIT ?");
		query.setParameter(1, date);
		query.setParameter(2, quantity);
		
		List<Hashtag> hashtags = (List<Hashtag>)query.list();
		return hashtags;
	}

	public List<Hashtag> hashtagForUser(String username) {
		Query query = getSession().createQuery("SELECT h.hashtag, u.id, u.name, u.surname, u.username, u.password, " +
					"u.description, u.secret_question, u.secret_answer, u.date, u.photo, " +
					"h.hashtagid, COUNT(b.buitid) as count , h.date " +
					"FROM Users as u, Hashtags as h, Buits as b, Buithash as bh " +
					"WHERE h.userid = u.userid AND u.username = ? " +
					"GROUP BY h.hashtag, u.username, h.hashtagid, h.date ");
		
		query.setParameter(1,username);
		List<Hashtag> hashtags = (List<Hashtag>)query.list();
		return hashtags;
	}
	
//	Comentado porque esta tabla se genero sola si se hizo bien el mapeo
	public void insertHashtagBuit(int hashtagid, int buitid){
//		try {
//			Connection connection = manager.getConnection();
//			PreparedStatement stmt = connection.prepareStatement
//					("INSERT INTO BuitHash	(buitid,hashtagid) " +
//							"VALUES(?,?)");
//			
//			stmt.setInt(1,buitid);
//			stmt.setInt(2, hashtagid);
//
//			stmt.execute();
//
//			connection.close();
//		} catch (SQLException e) {
//			throw new DatabaseException(e.getMessage(), e);
//		}
	}
	
	public void insertHashtag(Hashtag hashtag) {
		Session session = getSession();
		session.save(hashtag);
	}
	
	public Integer getHashtagId(String hashtagname){
		Query query = getSession().createQuery("SELECT h.hashtagid " +
						"FROM Hashtags as h " +
						"WHERE h.hashtag = ?");
			
		query.setParameter(1, hashtagname);
		List<Integer> hashtags = (List<Integer>)query.list();
		Integer hashtag = hashtags.get(0);
		return hashtag;
	 }
	
	 public Hashtag getHashtag(String hashtagname) {
		 Query query = getSession().createQuery("SELECT h.hashtag, u.userid, u.name, u.surname, u.username, u.password, " +
						"u.description, u.secret_question, u.secret_answer,u.date, u.photo, " +
						"h.hashtagid, COUNT(b.buitid) as count , h.date " +
						"FROM Users as u, Hashtags as h, Buits as b, Buithash as bh " +
						"WHERE h.userid = u.userid  AND h.hashtag = ?" +
						"GROUP BY h.hashtag, u.username, u.userid, h.hashtagid, h.date ");
				
				
		query.setParameter(1, hashtagname);
		List<Hashtag> hashtags = (List<Hashtag>)query.list();
		Hashtag hashtag = hashtags.get(0);
		return hashtag;
	 }
}
