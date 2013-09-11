package it.itba.edu.ar.model;

import java.util.Date;

public class Buit {
	private Integer id;
	private String message;
	private String username;
	private Date date;

	public Buit(int id, String message, String username, Date date){
		this.id = id;
		this.message = message;
		this.username = username;
		this.date = date;
	}
	
	@Override
	public String toString() {
		return "Buit [id=" + id + ", message=" + message + ", username="
				+ username + ", date=" + date + "]";
	}

	public Buit(String message, String username, Date date){
		this.message = message;
		this.username = username;
		this.date = date;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username	) {
		this.username = username;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
}
