package it.itba.edu.ar.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class HibernateGenericDao {
	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;		
	}
	
	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}
}
