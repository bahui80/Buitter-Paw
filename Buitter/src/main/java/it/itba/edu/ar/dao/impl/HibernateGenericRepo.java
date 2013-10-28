package it.itba.edu.ar.dao.impl;

import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;

public abstract class HibernateGenericRepo {
	private final SessionFactory sessionFactory;
	
	public HibernateGenericRepo(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}
}


