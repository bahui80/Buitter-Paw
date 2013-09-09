package it.itba.edu.ar.dao;

import it.itba.edu.ar.model.User;

public interface UserDao {
	
	public boolean checkUserName(String username);
	public User login(String username, String password);
	public void register(User user);
	public User updateUser(User user);
	public User changePassword(String username, String password);
	
}
