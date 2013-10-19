package it.itba.edu.ar.converters;

import it.itba.edu.ar.dao.impl.HibernateGenericDao;
import it.itba.edu.ar.model.User;

import org.hibernate.Session;
import org.springframework.core.convert.converter.Converter;

public class IdToUserConverter extends HibernateGenericDao implements Converter<Integer, User>{

	public User convert(Integer id) {
		Session session = getSession();
		return (User) session.get(this.getClass(), id);
	}
}
