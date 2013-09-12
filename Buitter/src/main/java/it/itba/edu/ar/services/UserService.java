package it.itba.edu.ar.services;

import it.itba.edu.ar.dao.UserManager;
import it.itba.edu.ar.model.User;
import it.itba.edu.ar.servlets.ServletValidationException;

public final class UserService {
	
	private UserService(){
		
	}
	
	public static boolean checkUsername(String username){
		if(username == null){
			throw new ServletValidationException();
		}
		UserManager userManager = UserManager.sharedInstance();
		
		User user = userManager.getUserByUsername(username);
		
		if(user != null)
			return true;
		return false;
	}
	
	public static User login(User user){
		if(user == null){
			throw new ServletValidationException();
		}
		
		UserManager userManager = UserManager.sharedInstance();

		User usr = userManager.getUserByUsernameAndPassword(user.getUsername(),user.getPassword());
		
		return usr;
	}
	
	public static User register(User user){
		if(user == null){
			throw new ServletValidationException();
		}
		
		UserManager userManager = UserManager.sharedInstance();

		userManager.insertUser(user);
		
		return userManager.getUserByUsername(user.getUsername());
	}
	
	public static void updateUser(User user){
		if(user == null){
			throw new ServletValidationException();
		}
		UserManager userManager = UserManager.sharedInstance();

		userManager.updateUser(user);
	}	
	
	public static void changePassword(User user){
		if(user == null){			
			throw new ServletValidationException();
		}
		UserService.updateUser(user);
	}
	
}
