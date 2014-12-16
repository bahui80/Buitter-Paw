package it.itba.edu.ar.domain.userlist;

public class DuplicatedListException extends RuntimeException {
	private UserList userList;
	
	public DuplicatedListException(UserList userList) {
		this.userList = userList;
	}
		
	public UserList getUserList() {
		return userList;
	}
}