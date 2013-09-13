package it.itba.edu.ar.services;

import it.itba.edu.ar.dao.UserManager;
import it.itba.edu.ar.model.User;
import it.itba.edu.ar.servlets.ServletValidationException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UserService {

	private static UserService instance;

	public static synchronized UserService sharedInstance() {
		if (instance == null) {
			instance = new UserService();
		}
		return instance;
	}

	private UserService() {

	}

	public User getUserByUsername(String username) {
		UserManager userManager = UserManager.sharedInstance();

		User user = userManager.getUserByUsername(username);
		return user;
	}

	public User getUserById(int id) {
		UserManager userManager = UserManager.sharedInstance();

		User user = userManager.getUserById(id);
		return user;
	}
	
	public User getUserByUsernameAndPassword(String username, String password){
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

	public HashMap<String,List<User>> search(String query){
		List<User> surname_query = UserManager.sharedInstance().getUsersBySurname(query);
		List<User> name_query = UserManager.sharedInstance().getUsersByName(query);
		User user = UserManager.sharedInstance().getUserByUsername(query);
		List<User> userList = new ArrayList<User>();
		userList.add(user);
		
		HashMap<String, List<User>> map = new HashMap<String, List<User>>();
		map.put("username", userList);
		map.put("name", name_query);
		map.put("surname", surname_query);
		
		return map;	
		
	}
	
}
