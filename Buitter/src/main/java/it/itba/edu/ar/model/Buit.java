package it.itba.edu.ar.model;

import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "buits")
public class Buit extends PersistentModel {

	@Column(length = 500, nullable = false, updatable = false)
	private String message;
	@ManyToOne
	private User creator;
	@ManyToMany
	private List<Hashtag> hashtags;
	@OneToMany(cascade = CascadeType.ALL)
	private List<Url> urls;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	private Date date;

	Buit() {
	}

	public Buit(String message, User creator, List<Hashtag> hashtags,
			Date date, List<Url> urls) {
		if (message == null || message.length() < 1 || creator == null
				|| date == null || hashtags == null || urls == null) {
			throw new IllegalArgumentException();
		}
		this.message = message;
		this.hashtags = hashtags;
		this.creator = creator;
		this.date = date;
		this.urls = urls;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		if (message == null || message.length() < 1) {
			throw new IllegalArgumentException();
		}
		this.message = message;
	}

	public User getUser() {
		return creator;
	}

	public void setUser(User user) {
		if (user == null)
			throw new IllegalArgumentException();
		this.creator = user;
	}

	public List<Url> getUrls() {
		return this.urls;
	}

	public void setUrl(List<Url> urls) {
		if (urls == null)
			throw new IllegalArgumentException();
		this.urls = urls;
	}

	@Override
	public String toString() {
		return "Buit [message=" + message + ", username="
				+ creator.getUsername() + ", date=" + date + "]";
	}

	public Date getDate() {
		return date;
	}

	public static class BuitComparator implements Comparator<Buit> {
		public int compare(Buit d1, Buit d2) {
			Date t1 = d1.getDate();
			Date t2 = d2.getDate();
				return t2.compareTo(t1);
		}
	}
}
