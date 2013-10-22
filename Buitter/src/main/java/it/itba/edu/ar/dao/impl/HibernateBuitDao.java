package it.itba.edu.ar.dao.impl;

import it.itba.edu.ar.dao.BuitDao;
import it.itba.edu.ar.model.Buit;
import it.itba.edu.ar.model.User;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class HibernateBuitDao extends HibernateGenericDao implements BuitDao {

	@Autowired
	public HibernateBuitDao(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}
	
	public void buit(Buit buit) {
		Session session = getSession();
		session.save(buit);
	}
	
	public Buit getBuit(String message, User user){
		Query query = getSession().createQuery("" +
				"SELECT b.buitid, b.message, b.date " +
				"FROM Users as u,Buits as b " +
				"WHERE u.userid = ? AND u.userid = b.userid AND b.message = ? " +
				"AND b.date >= (SELECT MAX(b.date) " +
				"FROM Users as u,Buits as b " +
				"WHERE u.userid = ? AND u.userid = b.userid AND b.message = ? )");
		
		query.setParameter(1, user.getId());
		query.setParameter(2, message);
		query.setParameter(3,user.getId());
		query.setParameter(4, message);

		List<Buit> list = (List<Buit>)query.list();
		Buit buit = list.get(0);
		
		return buit;
	}

	public List<Buit> getUserBuits(User user) {
		Query query = getSession().createQuery(" from Buit where user = ?");
		query.setParameter(0, user);
		List<Buit> buits = (List<Buit>)query.list();
		
//					"SELECT b.buitid, b.message, b.date " +
//					"FROM Users as u,Buits as b " +
//					"WHERE u.username = ? AND u.userid = b.userid " +
//					"ORDER BY b.date DESC");
//			stmt.setString(1, user.getUsername());
			
		return buits;
	}
	
	/*
	 * new User(int id, String name, String surname, String username, String password, 
			String description, String secret_question, String secret_answer, 
			Date creationDate, String photo)
	 * new Buit(int id, String message, User user, Date date);
	 */
	public List<Buit> getHashtagBuits(String hashtag) {
		Query query = getSession().createQuery("SELECT b.buitid, b.message, u.userid, u.name, u.surname, u.username, " +
					"u.password, u.description, u.secret_question, u.secret_answer, " +
					"u.date, u.photo, " +
					"b.date " +
					"FROM Hashtags as h,Buits as b, Buithash as bh, Users as u " +
					"WHERE h.hashtagid = bh.hashtagid AND b.buitid = bh.buitid AND u.userid = b.userid " +
					"AND h.hashtag = ? " +
					"ORDER BY b.date DESC");
			
		query.setParameter(1, hashtag);
		List<Buit> buits = (List<Buit>)query.list();
		return buits;
	}

	public void removeBuit(int buitid) {
		Session session = getSession();
		Transaction t = session.beginTransaction();
		Query query = session.createQuery("DELETE FROM Buits " +
					"WHERE buitid = ?");
			
		query.setParameter(1, buitid);
		query.executeUpdate();
		t.commit();
	}
}
