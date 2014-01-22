package it.itba.edu.ar.web.converters;

import it.itba.edu.ar.domain.user.User;
import it.itba.edu.ar.domain.user.UserRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UserConverter implements Converter<String, User> {

	private UserRepo userRepo;
	
	@Autowired
	public UserConverter(UserRepo userRepo) {
		this.userRepo = userRepo;
	}
	
	public User convert(String username) {
		return userRepo.get(username);
	}
	
}
