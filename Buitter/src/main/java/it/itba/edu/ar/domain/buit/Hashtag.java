package it.itba.edu.ar.domain.buit;

import it.itba.edu.ar.domain.PersistentEntity;
import it.itba.edu.ar.domain.user.User;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.Sort;
import org.hibernate.annotations.SortType;

@Entity
@Table(name="hashtags")
public class Hashtag extends PersistentEntity {
	
	
	@Column(updatable = false, length=140,nullable=false, unique = true) private String hashtag;
	@ManyToOne
	@JoinColumn(name = "userid")
	private User user;
	@ManyToMany(mappedBy="hashtags") @Sort(type=SortType.COMPARATOR, comparator = Buit.BuitComparator.class) 
		private Set<Buit> buits;
	@Transient private long count;
	@Temporal(TemporalType.TIMESTAMP)@Column(nullable=false) private Date date;
	
	Hashtag(){
	}
	
	public Hashtag(String hashtag, Date date, User user, int count){
		this(hashtag,date,user);
		this.setCount(count);
	}
	
	public Hashtag(String hashtag, Date date, User user){
		this.setHashtag(hashtag);
		this.setUser(user);
		this.setDate(date);
	}
	
	public Hashtag(String hashtag, long count) {
		this.setHashtag(hashtag);
		this.setCount(count);
	}
	
	private void setDate(Date date){
		if(date == null)
			throw new IllegalArgumentException();
		this.date = date;
	}
	
	private void setHashtag(String hashtag){
		if(hashtag == null || hashtag.length() > 139)
			throw new IllegalArgumentException();
		this.hashtag = hashtag;
	}
	
	public void removeBuit(Buit buit) {
		if(buit == null || !buits.contains(buit)) {
			throw new IllegalArgumentException();
		}
		buits.remove(buit);
	}
	
	public long getCount(){
		return count;
	}
	
	public Set<Buit> getBuits() {
		return this.buits;
	}
	
	public void setCount(long count){
		if(count < 1)
			throw new IllegalArgumentException();
		this.count = count;
	}
	
	public String getHashtag() {
		return hashtag;
	}
	
	public User getUser(){
		return user;
	}
	
	private void setUser(User user){
		if(user == null)
			throw new IllegalArgumentException();
		this.user = user;
	}

	public Date getDate(){
		return date;
	}
	
	@Override
	public String toString() {
		return "Hashtag [hashtag=" + hashtag + ", date=" + date
				+ ", username=" + user.getUsername() + "]";
	}
	
	
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
}

