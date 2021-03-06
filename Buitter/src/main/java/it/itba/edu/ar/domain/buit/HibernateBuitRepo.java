package it.itba.edu.ar.domain.buit;

import it.itba.edu.ar.domain.HibernateGenericRepo;
import it.itba.edu.ar.domain.event.MentionedEvent;
import it.itba.edu.ar.domain.event.ReBuitEvent;
import it.itba.edu.ar.domain.user.User;
import it.itba.edu.ar.domain.user.UserRepo;
import it.itba.edu.ar.web.common.ViewControllerHelper;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.ScrollableResults;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class HibernateBuitRepo extends HibernateGenericRepo implements BuitRepo {
	private UserRepo userRepo;
	
	@Autowired
	public HibernateBuitRepo(SessionFactory sessionFactory, UserRepo userRepo) {
		super(sessionFactory);
		this.userRepo = userRepo;
	}
	
	/* 
	 * 
	 * Methods for business logic.
	 * 
	 */
	
	public void rebuit(Buit buit, User user){
		Date date = new Date();
		ReBuit rebuit = new ReBuit(buit,user, date);
		
		buit.getBuitter().addEvent(new ReBuitEvent(date,user));
		buit.addRebuit(rebuit);
		
		Hashtag hashtg;
		String message = rebuit.getMessage();
//		List<Hashtag> hashtgs = new ArrayList<Hashtag>();
		List<String> hashtags = ViewControllerHelper.getHashTags(message);
//		List<String>  s_urls = ViewControllerHelper.getUrls(message);
//		List<Url> urls = new ArrayList<Url>();
		for(String hashtag: hashtags) {
			if((hashtg = getHashtag(hashtag)) == null) {
				hashtg = new Hashtag(hashtag, date, user);
				addHashtag(hashtg);
			}
		}
//		for (String s : s_urls) {
//			Url url = new Url(s);
//			url.setBuiturl(ViewControllerHelper.cutUrl(s));
//			urls.add(url);
//		}
//		message = ViewControllerHelper.shortenBuit(message, urls);
		addrebuit(rebuit);
	}
	
	public void buit(String message, User user) {
		if(message == null || user == null || message.isEmpty() || message.trim().length() == 0) {
			throw new IllegalArgumentException();
		}
		Date date = new Date();
		
		Hashtag hashtag;
		List<String> s_hashtags = ViewControllerHelper.getHashTags(message);
		List<Hashtag> hashtags = new ArrayList<Hashtag>();
		Set<String> s_users = ViewControllerHelper.getUsers(message);
		List<String>  s_urls = ViewControllerHelper.getUrls(message);
		List<Url> urls = new ArrayList<Url>();
		for(String s_hashtag: s_hashtags) {
			if((hashtag = getHashtag(s_hashtag)) == null) {
				hashtag = new Hashtag(s_hashtag, date, user);
				addHashtag(hashtag);
			}
			hashtags.add(hashtag);
		}
		
		Iterator<String> it = s_users.iterator();
		while(it.hasNext()) {
			User u;
			if((u = userRepo.get(it.next())) == null) {
				it.remove();
			}else{
				MentionedEvent e = new MentionedEvent(new Date(),user);
				u.addEvent(e);
			}
		}
		
		for (String s : s_urls) {
			Url url = new Url(s);
			url.setBuiturl(ViewControllerHelper.cutUrl(s));
			urls.add(url);
		}
		message = ViewControllerHelper.shortenBuit(message, urls);
		
		Buit buit = new Buit(message, user, hashtags, date, urls, s_users);
		user.addBuit(buit);
		addbuit(buit);
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
	
	public void addrebuit(ReBuit buit){
		getSession().save(buit);
	}
	
	public void addHashtag(Hashtag hashtag) {
		getSession().save(hashtag);
	}
	
	public void removeBuit(Buit buit, User user) {
		user.removeBuit(buit);
		for(Hashtag hashtag: buit.getHashtags()) {
			hashtag.removeBuit(buit);
		}
		getSession().delete(buit);
	}
}
