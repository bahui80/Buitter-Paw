package it.itba.edu.ar.domain.userlist;

import it.itba.edu.ar.domain.HibernateGenericRepo;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class HibernateUserListRepo extends HibernateGenericRepo implements UserListRepo {

	@Autowired
	public HibernateUserListRepo(SessionFactory sessionFactory) {
		super(sessionFactory);
	}
	
	public void deleteList(UserList userList) {
		userList.getOwner().removeList(userList);
		getSession().delete(userList);
	}

	public void add(UserList userList) {
		if(exists(userList)) {
			throw new DuplicatedListException(userList);
		}
		getSession().save(userList);
		userList.getOwner().addNewList(userList);
	}
	
	private boolean exists(UserList userList) {
		Query query = getSession().createQuery("SELECT s FROM UserList s WHERE s.name = ? and s.owner.id = ?");
		query.setParameter(0, userList.getName());
		query.setParameter(1, userList.getOwner().getId());
		return !query.list().isEmpty();
	}
}
