package it.itba.edu.ar.dao;

import it.itba.edu.ar.model.User;

public interface UserDao {
	
	public boolean checkUserName(String username);
	public User login(User user);
	public void register(User user);
	public void updateUser(User user);
	public void changePassword(User user);
	
}
