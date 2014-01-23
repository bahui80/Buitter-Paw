package it.itba.edu.ar.web.converters;

import it.itba.edu.ar.domain.user.User;
import it.itba.edu.ar.domain.user.UserRepo;

import org.springframework.core.convert.converter.Converter;

public class UserConverter implements Converter<String, User> {

	private UserRepo userRepo;
	
	public UserConverter(UserRepo userRepo) {
		this.userRepo = userRepo;
	}
	
	public User convert(String username) {
		return userRepo.get(username);
	}
	
}
