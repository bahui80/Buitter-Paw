package it.itba.edu.ar.domain.user;

import it.itba.edu.ar.domain.HibernateGenericRepo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Query;
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

	@Override
	public List<User> whoToFollow(User user) {
		List<User> whoToFollow = new ArrayList<User>();
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
			whoToFollow = this.getHighestFollowers(3, user);
			//whoToFollow = this.getMostPopular(n,this.getHighestFollowersCount(3));
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
	
	@SuppressWarnings("unchecked")
	private List<User> getHighestFollowers(int qty, User user){
		Query query = getSession().
				createQuery("FROM User as u " +
						"WHERE size(followers) > 0 AND " +
						" ? <> u.username AND " +
						" NOT EXISTS (FROM u.followers b WHERE b.username = " +
						" ? ) " + 
						"ORDER BY size(followers) DESC");
		query.setString(0, user.getUsername());
		query.setString(1, user.getUsername());
		query.setMaxResults(qty);
		
		List<User> results = query.list();
		return results;
	}

/*	@SuppressWarnings("unchecked")
	private List<User> getMostPopular(int qty, List<Integer> maxs) {
		String a = "(";
		for (int i = 0; i < maxs.size(); i++) {
			a = a + maxs.get(i);
			if(i != maxs.size())
				a = a + ",";
		}
		String b = " )";
		a = a + b;
		Query query = getSession().
				createQuery("FROM User as u" +
						"HAVING COUNT(u.following) > " + a);
		
		query.setMaxResults(qty);
		List<User> users = query.list();
		return users;
	}*/
	
}
