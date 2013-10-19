package it.itba.edu.ar.converters;

import it.itba.edu.ar.dao.impl.HibernateGenericDao;
import it.itba.edu.ar.model.Hashtag;

import org.hibernate.Session;
import org.springframework.core.convert.converter.Converter;

public class StringToHashtagConverter extends HibernateGenericDao implements Converter<String,Hashtag>{

	public Hashtag convert(String hashtagname) {
		Session session = getSession();
		return (Hashtag) session.get(this.getClass(), hashtagname);
		
		
//		"SELECT h.hashtag, u.userid, u.name, u.surname, u.username, u.password, " +
//						"u.description, u.secret_question, u.secret_answer,u.date, u.photo, " +
//						"h.hashtagid, COUNT(b.buitid) as count , h.date " +
//						"FROM Users as u, Hashtags as h, Buits as b, Buithash as bh " +
//						"WHERE h.userid = u.userid  AND h.hashtag = ?" +
//						"GROUP BY h.hashtag, u.username, u.userid, h.hashtagid, h.date ");
	}
	
}
