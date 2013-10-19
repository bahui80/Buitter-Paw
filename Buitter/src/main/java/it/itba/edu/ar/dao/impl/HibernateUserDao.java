package it.itba.edu.ar.dao.impl;

import it.itba.edu.ar.dao.UserDao;
import it.itba.edu.ar.model.User;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class HibernateUserDao extends HibernateGenericDao implements UserDao {

	@Autowired
	public HibernateUserDao(SessionFactory sessionFactory){
		super.setSessionFactory(sessionFactory);
	}
	
	/*
	 * new User(int id, String name, String surname, String username, String password, 
			String description, String secret_question, String secret_answer, 
			String creationDate, String photo)
	*/
	public User getUserByUsername(String username) {
		Query query = getSession().createQuery("SELECT * " +
												"FROM Users WHERE username = ?");
		query.setParameter(0, username);
		List<User> result = (List<User>)query.list();
		return result.get(0);
	}
	
	public User getUserById(int id) {
		Query query = getSession().createQuery("SELECT * " +
					"FROM Users WHERE userid = ?");
		query.setParameter(0, id);
		List<User> result = (List<User>)query.list();
		return result.get(0);
	}
	
	public List<User> getUsersBySurname(String surname) {
		Query query = getSession().createQuery("SELECT * " +
					"FROM Users WHERE surname = ? ");
		query.setParameter(0, surname);
		List<User> users = (List<User>)query.list();
		return users;
	}
	
	public List<User> getUsersByName(String name) {
		Query query = getSession().createQuery("SELECT * " +
					"FROM Users WHERE name = ? ");
		query.setParameter(0, name);
		List<User> users = (List<User>)query.list();
		return users;
	}

	public User getUserByUsernameAndPassword(String username, String password) {
		Query query = getSession().createQuery("SELECT * " +
					"FROM Users WHERE username = ? AND password = ?");
		query.setParameter(0, username);
		query.setParameter(1, password);
		List<User> user = (List<User>)query.list();
		return user.get(0);
	}

	public void insertUser(User user) {
		Session session = getSession();
		session.save(user);
	}

	
	public void updateUser(User user) {
		Query query = getSession().createQuery("UPDATE Users SET name =  ?, surname = ?, password = ?," +
							"description = ?, secret_question = ?, secret_answer = ?, photo = ?" +
							" WHERE username = ?" );
			
		query.setParameter(0, user.getName());
		query.setParameter(1,user.getSurname());
		query.setParameter(2,user.getPassword());
		query.setParameter(3, user.getDescription());
		query.setParameter(4, user.getSecretQuestion());
		query.setParameter(5, user.getSecretAnswer());
		query.setParameter(6, user.getPhoto());
		query.setParameter(7, user.getUsername());
		query.executeUpdate();
	}

	public List<User> getAllUsers() {
		Query query = getSession().createQuery("SELECT * " +
					"FROM Users " +
					"ORDER BY surname, name, username");
		List<User> users = (List<User>)query.list();
		return users;
	}

	public List<User> getAllUsersMatching(String consult) {
		Query query = getSession().createQuery("SELECT * " +
					"FROM Users " +
					"WHERE ( (lower(name) LIKE ANY ( SELECT '%' || ? ||'%' FROM Users ) ) OR " +
					"(lower(surname) LIKE ANY ( SELECT  '%' || ? ||'%' FROM Users ) )  OR " +
					"(lower(username) LIKE ANY ( SELECT '%' || ? ||'%' FROM Users ) ) )" +
					"ORDER BY surname, name, username");
		query.setParameter(0, consult.toLowerCase());
		query.setParameter(1, consult.toLowerCase());
		query.setParameter(3, consult.toLowerCase());
		List<User> users = (List<User>)query.list();
		
		return users;
	}
}
