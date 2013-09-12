package it.itba.edu.ar.dao;

import it.itba.edu.ar.model.User;

public interface UserDao {

	public User getUserByUsername(String username);
	public User getUserByUsernameAndPassword(String username,String password);
	public User getUserById(int id);
	public void insertUser(User user);
	public void updateUser(User user);
	
}
