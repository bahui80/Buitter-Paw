package it.itba.edu.ar.services;

import it.itba.edu.ar.model.User;

import java.util.List;

public interface UserService {

	public User getUserByUsername(String username);
	public User getUserById(int id);	
	public User getUserByUsernameAndPassword(String username, String password);
	public boolean checkUsername(String username);
	public User login(User user);
	public User register(User user);
	public void updateUser(User user);
	public void changePassword(User user);
	public List<User> search(String query);
	
}
