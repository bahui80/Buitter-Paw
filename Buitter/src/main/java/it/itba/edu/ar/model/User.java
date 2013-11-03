package it.itba.edu.ar.model;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Sort;
import org.hibernate.annotations.SortType;

@Entity
@Table(name="buitters")
public class User extends PersistentModel {
	
	@Column(updatable = false, length=32, unique=true,nullable=false) private String username;
	@Column(length=32,nullable=false) private String password;
	@Column(length=32,nullable=false) private String name;
	@Column(length=32,nullable=false) private String surname;
	@Column(length=140,nullable=false) private String description;
	@Column(length=60,nullable=false) private String secret_question;
	@Column(length=60,nullable=false) private String secret_answer;
	@Temporal(TemporalType.TIMESTAMP)@Column(nullable=false) private Date creationDate;
	private boolean privacy;
	private int visits;		
	@Lob private byte[] photo; 
	@OneToMany (mappedBy="buitter") @Sort(type=SortType.COMPARATOR, comparator = Buit.BuitComparator.class)
		private Set<Buit> mybuits;
 	@ManyToMany (mappedBy="followers", cascade=CascadeType.ALL) private Set<User> following;
 	@ManyToMany private Set<User> followers;
 	@ManyToMany (mappedBy="favoritter") @Sort(type=SortType.COMPARATOR, comparator = Buit.BuitComparator.class)
 	private Set<Buit> favorites;
 	@OneToMany (cascade=CascadeType.ALL) 
 	@Sort(type=SortType.COMPARATOR, comparator = Event.EventComparator.class)  
 	private List<Event> events;
	
	User(){
	}
	
	public User(String username, String password){
		if(username == null || username.length() > 32 || password == null || password.length() > 32)
			throw new IllegalArgumentException();
		this.username = username;
		this.password = password;
	}
		
	public User(String name, String surname, String username, String password, 
			String description, String secret_question, String secret_answer, 
			Date creationDate, int visits, boolean privacy, byte[] photo){		
		if(username == null || username.length() > 32 || password == null || password.length() > 32 
				|| description == null || description.length() > 140 || secret_question == null 
				|| secret_question.length() > 60 || secret_answer == null 
				|| secret_answer.length() > 60 || creationDate == null || visits < 0)
			throw new IllegalArgumentException();
		
		this.name = name;
		this.username = username;	
		this.surname = surname;
		this.password = password;
		this.description = description;
		this.secret_answer = secret_answer;
		this.secret_question = secret_question;
		this.creationDate = creationDate;
		this.visits = visits;
		this.privacy = privacy;
		this.photo = photo;
	}
	
	public String getName() {
		return name;
	}

	public Set<Buit> getMybuits() {
		return mybuits;
	}

	public void setMybuits(Set<Buit> mybuits) {
		this.mybuits = mybuits;
	}

	public void setName(String name) {
		if(name == null || name.length() > 32)
			throw new IllegalArgumentException();
		this.name = name;
	}
	
	public Set<User> getFollowing() {
		return this.following;
	}
	
	public Set<Buit> getFavourites() {
		return this.favorites;
	}
	
	public Set<User> getFollowers() {
		return this.followers;
	}
	
	public int getVisits() {
		return this.visits;
	}
	
	public boolean getPrivacy() {
		return this.privacy;
	}
	
	public Set<Buit> getBuits() {
		return this.mybuits;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		if(surname == null || surname.length() > 32)
			throw new IllegalArgumentException();
		this.surname = surname;
	}

	public void setPrivacy(boolean privacy) {
		this.privacy = privacy;
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		if(username == null || username.length() > 32)
			throw new IllegalArgumentException();
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		if(password == null || password.length() > 32)
			throw new IllegalArgumentException();
		this.password = password;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String desc) {
		if(desc == null || desc.length() > 140)
			throw new IllegalArgumentException();
		this.description = desc;
	}
	
	public String getSecretQuestion() {
		return secret_question;
	}

	public void setSecretQuestion(String s_question) {
		if(s_question == null || s_question.length() > 60)
			throw new IllegalArgumentException();
		this.secret_question = s_question;
	}
	
	public String getSecretAnswer() {
		return secret_answer;
	}

	public void setSecretAnswer(String s_answer) {
		if(s_answer == null || s_answer.length() > 60)
			throw new IllegalArgumentException();
		this.secret_answer = s_answer;
	}

	public byte[] getPhoto() {
		return photo;
	}

	public void setPhoto(byte[] photo) {
		this.photo = photo;
	}

	public List<Event> getEvents() {
		return events;
	}

	public void setEvents(List<Event> events) {
		this.events = events;
	}
	
	public void follow(User user) {
		if(user == null || followers.contains(user)) {
			throw new IllegalArgumentException();
		}
		followers.add(user);
	}
	
	public void unfollow(User user) {
		if(user == null || !followers.contains(user)) {
			throw new IllegalArgumentException();
		}
		followers.remove(user);
	}
	
	public void favorite(Buit buit) {
		if(buit == null || favorites.contains(buit)) {
			throw new IllegalArgumentException();
		}
		favorites.add(buit);
	}

	public void unfavorite(Buit buit) {
		if(buit == null || !favorites.contains(buit)) {
			throw new IllegalArgumentException();
		}
		favorites.remove(buit);
	}
	
	@Override
	public String toString() {
		return "User [name=" + name + ", surname=" + surname + ", username="
				+ username + "]";
	}
	
	public Date getDate(){
		return creationDate;
	}
	
	public void addVisit() {
		this.visits++;
	}
	
	public void removeVisit() {
		this.visits--;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((username == null) ? 0 : username.hashCode());
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
		User other = (User) obj;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}
}