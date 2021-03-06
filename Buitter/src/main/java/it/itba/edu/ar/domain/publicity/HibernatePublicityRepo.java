package it.itba.edu.ar.domain.publicity;

import it.itba.edu.ar.domain.HibernateGenericRepo;

import java.util.Collections;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class HibernatePublicityRepo extends HibernateGenericRepo implements PublicityRepo {

	@Autowired
	public HibernatePublicityRepo(SessionFactory sessionFactory) {
		super(sessionFactory);
	}
	
	public Publicity getRandomPublicity() {
		Publicity publicity = null;
		List<Publicity> publicities = getAll();
		Collections.shuffle(publicities);
		if(publicities.size() > 0) {
			publicity = publicities.get(0);
		}
		return publicity;
	}
	
	@SuppressWarnings("unchecked")
	public List<Publicity> getAll() {
		Query query = getSession().createQuery("FROM Publicity");
		return query.list();
	}
}
