package it.itba.edu.ar.model;

import java.sql.Timestamp;

public class Hashtag {
	
	private Integer id;
	private String hashtag;
	private String date;
	private User user;
	
	public Hashtag(String hashtag, String date, User user){
		if(hashtag == null || hashtag.length() > 139 || date == null || user == null)
			throw new IllegalArgumentException();
		
		this.hashtag = hashtag;
		this.date = date;
		this.user = user;
	}
	
	public Hashtag(int id, String hashtag, String date, User user){
		if(id == 0 || hashtag == null || hashtag.length() > 139 || date == null || user == null)
			throw new IllegalArgumentException();
		
		this.id = id;
		this.hashtag = hashtag;
		this.date = date;
		this.user = user;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		if(id == 0)
			throw new IllegalArgumentException();
		this.id = id;
	}

	public String getHashtag() {
		return hashtag;
	}
	
	public User getUser(){
		return user;
	}
	
	public void setUser(User user){
		if(user == null)
			throw new IllegalArgumentException();
		this.user = user;
	}
	
	public String getDate(){
		return this.date;
	}

	@Override
	public String toString() {
		return "Hashtag [id=" + id + ", hashtag=" + hashtag + ", date=" + date
				+ ", username=" + user.getUsername() + "]";
	}
	
}
