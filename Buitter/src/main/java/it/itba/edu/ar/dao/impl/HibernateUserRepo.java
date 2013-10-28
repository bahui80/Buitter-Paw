package it.itba.edu.ar.dao.impl;

import it.itba.edu.ar.model.User;
import it.itba.edu.ar.repo.UserRepo;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class HibernateUserRepo extends HibernateGenericRepo implements UserRepo {

	@Autowired
	public HibernateUserRepo(SessionFactory sessionFactory){
		super(sessionFactory);
	}
	
	/*
	 * new User(int id, String name, String surname, String username, String password, 
			String description, String secret_question, String secret_answer, 
			String creationDate, String photo)
	*/
	public User getUserByUsername(String username) {
		Transaction tr = getSession().beginTransaction();
		// OLD QUERY SELECT * from Users WHERE username = ?
		Query query = getSession().createQuery("FROM User WHERE username = ?");
		query.setParameter(0, username);
		List<User> result = (List<User>)query.list();
		tr.commit();
		return result.size() > 0 ? result.get(0): null;
	}
	
	public User getUserById(int id) {
		Transaction tr = getSession().getTransaction();
		Query query = getSession().createQuery("SELECT * " +
					"FROM Users WHERE userid = ?");
		query.setParameter(0, id);
		List<User> result = (List<User>)query.list();
		tr.commit();
		return result.get(0);
	}
	
	public List<User> getUsersBySurname(String surname) {
		Transaction tr = getSession().getTransaction();
		Query query = getSession().createQuery("SELECT * " +
					"FROM Users WHERE surname = ? ");
		query.setParameter(0, surname);
		List<User> users = (List<User>)query.list();
		tr.commit();
		return users;
	}
	
	public List<User> getUsersByName(String name) {
		Transaction tr = getSession().getTransaction();
		Query query = getSession().createQuery("SELECT * " +
					"FROM Users WHERE name = ? ");
		query.setParameter(0, name);
		List<User> users = (List<User>)query.list();
		tr.commit();
		return users;
	}

	public User getUserByUsernameAndPassword(String username, String password) {
		Transaction tr = getSession().getTransaction();
		Query query = getSession().createQuery("SELECT * " +
					"FROM Users WHERE username = ? AND password = ?");
		query.setParameter(0, username);
		query.setParameter(1, password);
		List<User> user = (List<User>)query.list();
		tr.commit();
		return user.get(0);
	}

	public void updateUser(User user) {
		Transaction tr = getSession().getTransaction();
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
		tr.commit();
	}
	
	/* 
	 * 
	 * Methods for business logic.
	 * 
	 */

	public List<User> search(String consult) {
		if (consult == null || consult.trim().isEmpty()) {
			return this.getAll();
		}
		return this.getAllUsersMatching(consult);
	}
	
	public boolean login(User user, String password) {
		if(user == null || !user.getPassword().equals(password)) {
			return false;
		}
		return true;
	}

	/* 
	 * 
	 * Methods for database.
	 * 
	 */
	
	@SuppressWarnings("unchecked")
	@Override
	public List<User> getAllUsersMatching(String consult) {
		String lowerConsult = consult.toLowerCase();
		Query query = getSession().createQuery("FROM User " +
					"WHERE ( (lower(name) LIKE ( '%" + lowerConsult + "%' )) OR " +
					"(lower(surname) LIKE  ( '%" + lowerConsult + "%' ) )  OR " +
					"(lower(username) LIKE  ( '%" + lowerConsult + "%' ) ) )" +
					"ORDER BY surname, name, username");
		List<User> users = (List<User>)query.list();
		return users;
	}
	
	@Override
	public void add(User user) {
//		if (existsCode(user.getCode())) {
//			throw new IllegalArgumentException();
//		}
		getSession().save(user);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<User> getAll() {
		Query query = getSession().createQuery("FROM User ORDER BY surname, name, username");
		List<User> users = query.list();
		return users;
	}

	@SuppressWarnings("unchecked")
	@Override
	public User get(String username) {
		Query query = getSession().createQuery("FROM User WHERE username=?");
		query.setParameter(0, username);
		List<User> user = query.list();
		return user.isEmpty() ? null : user.get(0);
	}
}
