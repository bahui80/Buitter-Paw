package it.itba.edu.ar.dao.impl;

import it.itba.edu.ar.model.Buit;
import it.itba.edu.ar.model.Url;
import it.itba.edu.ar.repo.UrlRepo;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class HibernateUrlDao extends HibernateGenericRepo implements UrlRepo {

	
	@Autowired
	public HibernateUrlDao(SessionFactory sessionFactory){
		super(sessionFactory);
	}
	
	
	public void insertUrl(Url url) {
		Session session = getSession();
		session.save(url);
	}
	
	public void insertNewUrl(Url url) {
		Session session = getSession();
		session.save(url);
	}

	public List<Url> urlsForBuit(Buit buit) {
		Query query = getSession().createQuery("SELECT urlid, url, buiturl, buitid " +
												"FROM urls " + "WHERE buitid = ?");
		query.setParameter(0, buit.getId());
		List<Url> urls = (List<Url>)query.list();
		return urls;
	}
	
	public Url getUrlForId(int id){
		Query query = getSession().createQuery("SELECT * FROM Urls WHERE urlid = ?");
		query.setParameter(0,id);
		List<Url> result = (List<Url>)query.list();
		return result.get(0);
	}
	
	public Integer getIdForUrl(String url){
		Query query = getSession().createQuery("SELECT urlid FROM Urls WHERE url = ?");
		query.setParameter(0,url);
		List<Integer> result = (List<Integer>)query.list();
		return result.get(0);
	}
}
