package it.itba.edu.ar.services.impl;

import it.itba.edu.ar.dao.UserDao;
import it.itba.edu.ar.model.User;
import it.itba.edu.ar.services.UserService;
import it.itba.edu.ar.servlets.ServletValidationException;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

	private UserDao userDao;

	@Autowired
	public UserServiceImpl(UserDao userDao) {
		this.userDao = userDao;
	}

	public User getUserByUsername(String username) {
		if (username == null) {
			throw new ServletValidationException();
		}

		User user = userDao.getUserByUsername(username);
		return user;
	}

	public User getUserById(int id) {
		if (id <= 0) {
			throw new ServletValidationException();
		}

		User user = userDao.getUserById(id);
		return user;
	}

	public User getUserByUsernameAndPassword(String username, String password) {
		if (username == null || password == null) {
			throw new ServletValidationException();
		}

		User user = userDao
				.getUserByUsernameAndPassword(username, password);
		return user;
	}

	public boolean checkUsername(String username) {
		if (username == null) {
			throw new ServletValidationException();
		}

		User user = userDao.getUserByUsername(username);

		if (user != null)
			return true;
		return false;
	}

	public User login(User user) {
		if (user == null) {
			throw new ServletValidationException();
		}

		User usr = userDao.getUserByUsernameAndPassword(user.getUsername(),
				user.getPassword());

		return usr;
	}

	public User register(User user) {
		if (user == null) {
			throw new ServletValidationException();
		}

		userDao.insertUser(user);

		return userDao.getUserByUsername(user.getUsername());
	}

	public void updateUser(User user) {
		if (user == null) {
			throw new ServletValidationException();
		}
		userDao.updateUser(user);
	}

	public void changePassword(User user) {
		if (user == null) {
			throw new ServletValidationException();
		}
		this.updateUser(user);
	}

	public List<User> search(String query) {

		if (query == null || query.trim().isEmpty())
			return userDao.getAllUsers();
		return userDao.getAllUsersMatching(query);
	}

}
