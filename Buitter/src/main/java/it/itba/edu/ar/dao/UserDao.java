package it.itba.edu.ar.dao;

import java.util.List;

import it.itba.edu.ar.model.User;

public interface UserDao {

	public User getUserByUsername(String username);
	public User getUserByUsernameAndPassword(String username,String password);
	public User getUserById(int id);
	public void insertUser(User user);
	public void updateUser(User user);
	public List<User> getUsersByName(String name);
	public List<User> getUsersBySurname(String surname);
	public List<User> getAllUsers();
	public List<User> getAllUsersMatching(String query);
	
}
