package it.itba.edu.ar.repo;

import it.itba.edu.ar.model.Buit;
import it.itba.edu.ar.model.Hashtag;
import it.itba.edu.ar.model.Url;
import it.itba.edu.ar.model.User;
import it.itba.edu.ar.web.ViewControllerHelper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.ScrollableResults;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class HibernateBuitRepo extends HibernateGenericRepo implements BuitRepo {

	@Autowired
	public HibernateBuitRepo(SessionFactory sessionFactory) {
		super(sessionFactory);
	}
	
	/* 
	 * 
	 * Methods for business logic.
	 * 
	 */
	// TODO VER BIEN COMO RESOLVER LO DE LAS URLS
	public void buit(String message, User user) {
		if(message == null || user == null || message.isEmpty() || message.trim().length() == 0) {
			throw new IllegalArgumentException();
		}
		Date date = new Date();
		
		Hashtag hashtg;
		List<Hashtag> hashtgs = new ArrayList<Hashtag>();
		List<String> hashtags = ViewControllerHelper.getHashTags(message);
		List<String>  s_urls = ViewControllerHelper.getUrls(message);
		List<Url> urls = new ArrayList<Url>();
		for(String hashtag: hashtags) {
			if((hashtg = getHashtag(hashtag)) == null) {
				hashtg = new Hashtag(hashtag, date, user);
				addHashtag(hashtg);
			}
			hashtgs.add(hashtg);
		}
		for (String s : s_urls) {
			Url url = new Url(s);
			url.setBuiturl(ViewControllerHelper.cutUrl(s));
			urls.add(url);
		}
		message = ViewControllerHelper.shortenBuit(message, urls);
		addbuit(new Buit(message, user, hashtgs, date,urls));
	}
	
	/* 
	 * 
	 * Methods for database.
	 * 
	 */
	
	public List<Hashtag> getHashtagsSinceDate(Date date, int quantity) {
		Query query = getSession().createQuery("SELECT h.hashtag, COUNT(*) " +
												"FROM Buit b INNER JOIN b.hashtags h " +
												"WHERE b.date > ? " +
												"GROUP BY h.hashtag " +
												"ORDER BY count(*) desc");
		query.setParameter(0, date);
		query.setMaxResults(quantity);
		List<Hashtag> hashtags = new ArrayList<Hashtag>();
		ScrollableResults results = query.scroll();
		while(results.next()) {
			hashtags.add(new Hashtag(results.getString(0), results.getLong(1)));
		}
		return hashtags;
	}
	

	@SuppressWarnings("unchecked")
	public Hashtag getHashtag(String hashtag) {
		Query query = getSession().createQuery("FROM Hashtag WHERE hashtag = ?");
		query.setParameter(0, hashtag);
		List<Hashtag> hashtags = query.list();
		return hashtags.isEmpty() ? null : (Hashtag) hashtags.get(0);
	}
	
	public Buit getBuit(int buitid) {
		return (Buit) getSession().get(Buit.class, buitid);
	}
	
	public void addbuit(Buit buit) {
		getSession().save(buit);
	
	}
	
	public void addHashtag(Hashtag hashtag) {
			getSession().save(hashtag);
	}
	
	public void removeBuit(Buit buit) {
		getSession().delete(buit);
	}
}
