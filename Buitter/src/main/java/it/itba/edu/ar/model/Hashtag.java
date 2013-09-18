package it.itba.edu.ar.model;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class Hashtag extends DateFormatter{
	
	private Integer id;
	private String hashtag;
	private User user;
	private int count;
	
	public Hashtag(String hashtag, Timestamp date, User user, int count){
		super(date);
		if(hashtag == null || hashtag.length() > 139 || user == null || count < 0)
			throw new IllegalArgumentException();
		
		this.hashtag = hashtag;
		this.date = date;
		this.user = user;
		this.count = count;
	}
	
	public Hashtag(int id, String hashtag, Timestamp date, User user, int count){
		super(date);
		if(id == 0 || hashtag == null || hashtag.length() > 139 || user == null || count < 0)
			throw new IllegalArgumentException();
		
		this.id = id;
		this.hashtag = hashtag;
		this.date = date;
		this.user = user;
		this.count = count;
	}
	
	public int getCount(){
		return count;
	}
	
	public void setCount(int count){
		if(count < 0)
			throw new IllegalArgumentException();
		this.count = count;
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

	@Override
	public String toString() {
		return "Hashtag [id=" + id + ", hashtag=" + hashtag + ", date=" + date
				+ ", username=" + user.getUsername() + "]";
	}
	
	@Override
	public void setSimpleDateFormatter(SimpleDateFormat formatter) {
		super.setSimpleDateFormatter(formatter);
		this.user.setSimpleDateFormatter(formatter);
	}
}
