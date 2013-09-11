package it.itba.edu.ar.model;

import java.util.Date;

public class Hashtag {

	private Integer id;
	private String hashtag;
	private Date date;
	private String username;
	
	public Hashtag(String hashtag, Date date, String username){
		this.hashtag = hashtag;
		this.date = date;
		this.username = username;
	}
	
	public Hashtag(int id, String hashtag, Date date, String username){
		this.id = id;
		this.hashtag = hashtag;
		this.date = date;
		this.username = username;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getHashtag() {
		return hashtag;
	}

	public void setHashtag(String hashtag) {
		this.hashtag = hashtag;
	}

	@Override
	public String toString() {
		return "Hashtag [id=" + id + ", hashtag=" + hashtag + ", date=" + date
				+ ", username=" + username + "]";
	}
	
}
