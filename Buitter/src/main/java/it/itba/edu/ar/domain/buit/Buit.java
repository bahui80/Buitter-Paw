package it.itba.edu.ar.domain.buit;

import it.itba.edu.ar.domain.PersistentEntity;
import it.itba.edu.ar.domain.user.User;

import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CollectionOfElements;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "buits")
public class Buit extends PersistentEntity {

	@Column(length = 500, nullable = false, updatable = false)
	private String message;
	@ManyToMany 
	private Set<User> favoritter;	
	@ManyToOne
	@JoinColumn(name = "userid")
	private User buitter;
	@ManyToMany private List<Hashtag> hashtags;
	@OneToMany(cascade = CascadeType.ALL) private List<Url> urls;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false) private Date date;
	@CollectionOfElements private Set<String> mentioned_buitters;
	@OneToMany(cascade=CascadeType.ALL, mappedBy="buit") private Set<ReBuit> rebuits = new HashSet<ReBuit>();
	
	Buit() {
	}
	
	public Buit(String message, User buitter, List<Hashtag> hashtags,
			Date date) {
		this.setMessage(message);
		this.setHashtags(hashtags);
		this.setBuitter(buitter);
		this.setBuitter(buitter);
		this.setDate(date);
	}

	public Buit(String message, User buitter, List<Hashtag> hashtags,
			Date date, List<Url> urls, Set<String> mentioned_buitters) {
		this(message,buitter,hashtags,date);
		this.setUrls(urls);
		this.mentioned_buitters = mentioned_buitters;
	}

	//GETTERS
	public String getMessage() {
		return message;
	}
	
	public boolean getIsrebuit() {
		return false;
	}
	
	public Set<String> getMentionedBuitters() {
		return mentioned_buitters;
	}

	public User getBuitter() {
		return buitter;
	}

	public List<Url> getUrls() {
		return this.urls;
	}

	public Date getDate() {
		return date;
	}

	public List<Hashtag> getHashtags() {
		return hashtags;
	}
	
	public Set<User> getFavoritter() {
		return favoritter;
	}

	//SETERS
	private void setDate(Date date){
		if(date == null)
			throw new IllegalArgumentException();
		this.date = date;
	}
	
	public void setMessage(String message) {
		if (message == null || message.length() > 500 || message.length() == 0) {
			throw new IllegalArgumentException();
		}
		this.message = message;
	}
	
	private void setBuitter(User buitter) {
		if (buitter == null)
			throw new IllegalArgumentException();
		this.buitter = buitter;
	}
	
	public void setUrls(List<Url> urls) {
		if (urls == null)
			throw new IllegalArgumentException();
		this.urls = urls;
	}
	
	private void setHashtags(List<Hashtag> hashtags) {
		if( hashtags == null )
			throw new IllegalArgumentException();
		this.hashtags = hashtags;
	}
	
	public Set<ReBuit> getRebuits() {
		return rebuits;
	}

	public void setRebuits(Set<ReBuit> rebuits) {
		if(rebuits == null)
			throw new IllegalArgumentException();
		this.rebuits = rebuits;
	}
	
	public void addRebuit(ReBuit rb){
		if(rb == null || rebuits.contains(rb) || !rb.getMessage().equals(this.getMessage()))
			throw new IllegalArgumentException();
		this.rebuits.add(rb);
	}
	
	public void addFavorite(User user){
		if(user == null || favoritter.contains(user))
			throw new IllegalArgumentException();
		favoritter.add(user);
	}
	
	public void removeFavorite(User user){
		if(user == null || !favoritter.contains(user)) {
			throw new IllegalArgumentException();
		}
		favoritter.remove(user);
	}
	
	@Override
	public String toString() {
		return "Buit [message=" + message + ", username="
				+ buitter.getUsername() + ", date=" + date + "]";
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result
				+ ((hashtags == null) ? 0 : hashtags.hashCode());
		result = prime * result + ((message == null) ? 0 : message.hashCode());
		result = prime * result + ((urls == null) ? 0 : urls.hashCode());
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
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (hashtags == null) {
			if (other.hashtags != null)
				return false;
		} else if (!hashtags.equals(other.hashtags))
			return false;
		if (message == null) {
			if (other.message != null)
				return false;
		} else if (!message.equals(other.message))
			return false;
		if (urls == null) {
			if (other.urls != null)
				return false;
		} else if (!urls.equals(other.urls))
			return false;
		return true;
	}
	
	//COMPARATOR
	public static class BuitComparator implements Comparator<Buit> {
		public int compare(Buit d1, Buit d2) {
			Date t1 = d1.getDate();
			Date t2 = d2.getDate();
			String m1 = d1.getMessage();
			String m2 = d2.getMessage();
			
			int result = t2.compareTo(t1);
			if(result == 0) {
				return m2.compareTo(m1);
			} else {
				return result;
			}
		}
	}
}
