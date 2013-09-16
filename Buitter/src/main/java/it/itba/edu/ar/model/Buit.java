package it.itba.edu.ar.model;

import java.sql.Timestamp;

public class Buit {
	private Integer id;
	private String message;
	private User user;
	private String date;

	public Buit(int id, String message, User user, String date){
		if(id == 0 || message == null || message.length() < 1 || user == null || date == null)
			throw new IllegalArgumentException();
		
		this.id = id;
		this.message = message;
		this.user = user;
		this.date = date;
	}

	public Buit(String message,  User user, String date){
		if(message == null || message.length() < 1 || user == null || date == null)
			throw new IllegalArgumentException();
		
		this.message = message;
		this.user = user;
		this.date = date;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		if(id == 0)
			throw new IllegalArgumentException();
		this.id = id;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		if(message == null || message.length() > 320) {
			throw new IllegalArgumentException();
		}
		this.message = message;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		if(user == null)
			throw new IllegalArgumentException();
		this.user = user;
	}

	public String getDate() {
		return date;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Buit other = (Buit) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "Buit [id=" + id + ", message=" + message + ", username="
				+ user.getUsername() + ", date=" + date + "]";
	}
}

