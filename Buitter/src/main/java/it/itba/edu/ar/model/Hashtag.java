package it.itba.edu.ar.model;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@Entity
@Table(name="hashtags")
public class Hashtag extends PersistentModel {
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((hashtag == null) ? 0 : hashtag.hashCode());
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
		Hashtag other = (Hashtag) obj;
		if (hashtag == null) {
			if (other.hashtag != null)
				return false;
		} else if (!hashtag.equals(other.hashtag))
			return false;
		return true;
	}

	@Column(updatable = false, length=140,nullable=false, unique = true)private String hashtag;
	@ManyToOne private User user;
	@ManyToMany(mappedBy="hashtags") private Set<Buit> buits;
	@Transient private long count;
	@Temporal(TemporalType.TIMESTAMP)@Column(nullable=false)private Date date;
	
	Hashtag(){
	}
	
	public Hashtag(String hashtag, Date date, User user, int count){
		if(hashtag == null || hashtag.length() > 139 || user == null || count < 0)
			throw new IllegalArgumentException();
		
		this.hashtag = hashtag;
		this.user = user;
		this.date = date;
		this.count = count;
	}
	
	public Hashtag(String hashtag, Date date, User user){
		if(hashtag == null || hashtag.length() > 139 || user == null)
			throw new IllegalArgumentException();
		
		this.hashtag = hashtag;
		this.user = user;
		this.date = date;
	}
	
	public Hashtag(String hashtag, long count) {
		this.hashtag = hashtag;
		this.count = count;
	}
	
	public long getCount(){
		return count;
	}
	
	public Set<Buit> getBuits() {
		return this.buits;
	}
	
	public void setCount(int count){
		if(count < 0)
			throw new IllegalArgumentException();
		this.count = count;
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
		return "Hashtag [hashtag=" + hashtag + ", date=" + date
				+ ", username=" + user.getUsername() + "]";
	}
	
	public String getDate(){
		return date.toString();
	}
}

