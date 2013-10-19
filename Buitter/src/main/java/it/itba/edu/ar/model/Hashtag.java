package it.itba.edu.ar.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
@Entity
public class Hashtag{
	
	@Id @GeneratedValue(strategy=javax.persistence.GenerationType.AUTO)private Integer id;
	@Column(length=140,nullable=false,unique=true)private String hashtag;
	@OneToOne private User user;
	private int count;
	@Temporal(TemporalType.DATE)@Column(nullable=false)private Date date;
	
	public Hashtag(){
	}
	
	public Hashtag(String hashtag, Date date, User user, int count){
		if(hashtag == null || hashtag.length() > 139 || user == null || count < 0)
			throw new IllegalArgumentException();
		
		this.hashtag = hashtag;
		this.user = user;
		this.count = count;
	}
	
	public Hashtag(int id, String hashtag, Date date, User user, int count){
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
	
	public String getDate(){
		return date.toString();
	}
}

