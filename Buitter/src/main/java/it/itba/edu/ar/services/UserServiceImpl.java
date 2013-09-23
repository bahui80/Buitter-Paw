package it.itba.edu.ar.services;

import it.itba.edu.ar.dao.UserManager;
import it.itba.edu.ar.model.User;
import it.itba.edu.ar.servlets.ServletValidationException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UserServiceImpl {

	private static UserServiceImpl instance;

	public static synchronized UserServiceImpl sharedInstance() {
		if (instance == null) {
			instance = new UserServiceImpl();
		}
		return instance;
	}

	private UserServiceImpl() {

	}

	public User getUserByUsername(String username) {
		if (username == null) {
			throw new ServletValidationException();
		}
		UserManager userManager = UserManager.sharedInstance();

		User user = userManager.getUserByUsername(username);
		return user;
	}

	public User getUserById(int id) {
		if (id <= 0) {
			throw new ServletValidationException();
		}
		UserManager userManager = UserManager.sharedInstance();

		User user = userManager.getUserById(id);
		return user;
	}
	
	public User getUserByUsernameAndPassword(String username, String password){
		if (username == null || password == null) {
			throw new ServletValidationException();
		}
		UserManager userManager = UserManager.sharedInstance();

		User user = userManager.getUserByUsernameAndPassword(username,password);
		return user;
	}

	public boolean checkUsername(String username) {
		if (username == null) {
			throw new ServletValidationException();
		}
		UserManager userManager = UserManager.sharedInstance();

		User user = userManager.getUserByUsername(username);

		if (user != null)
			return true;
		return false;
	}

	public User login(User user) {
		if (user == null) {
			throw new ServletValidationException();
		}

		UserManager userManager = UserManager.sharedInstance();

		User usr = userManager.getUserByUsernameAndPassword(user.getUsername(),
				user.getPassword());

		return usr;
	}

	public User register(User user) {
		if (user == null) {
			throw new ServletValidationException();
		}

		UserManager userManager = UserManager.sharedInstance();

		userManager.insertUser(user);

		return userManager.getUserByUsername(user.getUsername());
	}

	public void updateUser(User user) {
		if (user == null) {
			throw new ServletValidationException();
		}
		UserManager userManager = UserManager.sharedInstance();

		userManager.updateUser(user);
	}

	public void changePassword(User user) {
		if (user == null) {
			throw new ServletValidationException();
		}
		this.updateUser(user);
	}

	public List<User> search(String query){
		UserManager userManager = UserManager.sharedInstance();
		
		if(query == null || query.trim().isEmpty())
			return userManager.getAllUsers();
		return userManager.getAllUsersMatching(query);
	}
	
}
