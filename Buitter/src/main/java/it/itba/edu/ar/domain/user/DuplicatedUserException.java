package it.itba.edu.ar.domain.user;


public class DuplicatedUserException extends RuntimeException {

	private User user;
	
	public DuplicatedUserException(User user) {
		this.user = user;
	}
	
	public User getUser() {
		return user;
	}
}
