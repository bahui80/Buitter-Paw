package it.itba.edu.ar.dao;

import it.itba.edu.ar.model.User;

public interface UserDao {

	public User getUserByUsername(String username);
	public User getUserById(int id);
	public void login(User user);
	public void register(User user);
	public void updateUser(User user);
	public void changePassword(User user);

}
