package it.itba.edu.ar.services;

import it.itba.edu.ar.dao.UserManager;
import it.itba.edu.ar.model.User;

public class UserService {
	
	public static boolean checkUsername(String username){
		UserManager userManager = UserManager.sharedInstance();
		
		User user = userManager.getUserByUsername(username);
		
		if(user != null)
			return true;
		return false;
	}
	
	public static User login(User user){
		UserManager userManager = UserManager.sharedInstance();

		userManager.login(user);
		
		return userManager.getUserByUsername(user.getUsername());
	}
	
	public static User register(User user){
		UserManager userManager = UserManager.sharedInstance();

		userManager.register(user);
		
		return userManager.getUserByUsername(user.getUsername());
	}
	
	public static void updateUser(User user){
		UserManager userManager = UserManager.sharedInstance();

		userManager.updateUser(user);
	}
	
	public static void changePassword(User user){
		UserService.updateUser(user);
	}
	
}
