package it.itba.edu.ar.dao.impl;

import java.io.Serializable;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class HibernateGenericDao {
	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;		
	}
	
	protected org.hibernate.classic.Session getSession() {
		return sessionFactory.getCurrentSession();
	}
	
	public Serializable save(Object o) {
		return getSession().save(o);
	}
}
