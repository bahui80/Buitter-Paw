package it.itba.edu.ar.converters;

import it.itba.edu.ar.dao.impl.HibernateGenericDao;
import it.itba.edu.ar.model.User;

import org.hibernate.Session;
import org.springframework.core.convert.converter.Converter;

public class UsernameToUserConverter extends HibernateGenericDao implements Converter<String, User>{

	public User convert(String username) {
		Session session = getSession();
		return (User) session.get(this.getClass(), username);
	}
	
}
