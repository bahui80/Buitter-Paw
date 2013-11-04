package it.itba.edu.ar.repo;

import it.itba.edu.ar.model.Hashtag;
import it.itba.edu.ar.model.User;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.ScrollableResults;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class HibernateUserRepo extends HibernateGenericRepo implements UserRepo {

	@Autowired
	public HibernateUserRepo(SessionFactory sessionFactory){
		super(sessionFactory)	;
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

	@Override
	public Set<User> whoToFollow(User user) {
		Set<User> whoToFollow = new HashSet<User>();
		Set<User> following = user.getFollowing();
		
		//hay que meter el metodo que lee de un archivo
		int n = 5;
		
		for (User u : following) {
			Set<User> whoIsHeFollowing = u.getFollowing();
			Set<User> comunUsers = new HashSet<User>(following);
			comunUsers.retainAll(whoIsHeFollowing);
			if(comunUsers.size() >= n){
				for (User user2 : whoIsHeFollowing) {
					if(!following.contains(user2))
						whoToFollow.add(user2);
				}
			}
		}
		
		if(whoToFollow.size() < 3){
			whoToFollow.clear();
			
		}
		
		return whoToFollow;
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

	public List<User> getMostPopular(int qty) {
		Query query = getSession().createQuery("");
		query.setMaxResults(qty);
		List<User> users = new ArrayList<User>();
		ScrollableResults results = query.scroll();
		while(results.next()) {
//			users.add(new ));
		}
		return users;
	}
	
}
