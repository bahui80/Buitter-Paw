package it.itba.edu.ar.domain.user;

import it.itba.edu.ar.domain.PersistentEntity;
import it.itba.edu.ar.domain.buit.Buit;
import it.itba.edu.ar.domain.event.Event;
import it.itba.edu.ar.domain.event.FollowedEvent;
import it.itba.edu.ar.domain.userlist.UserList;
import it.itba.edu.ar.web.common.BuitFilter;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Sort;
import org.hibernate.annotations.SortType;
import org.imgscalr.Scalr;

@Entity
@Table(name="users")
public class User extends PersistentEntity {
	
	@Column(length=32,nullable=false) 
	private String name;
	@Column(length=32,nullable=false)
	private String surname;
	@Column(length=32,nullable=false)
	private String password;
	@Temporal(TemporalType.TIMESTAMP)@Column(name="date", nullable=false)
	private Date creationDate;
	private byte[] photo;
	private byte[] thumbnailPhoto;
	private byte[] backgroundImage;
	@Column(updatable = false, length=32, unique=true,nullable=false) private String username;
	@Column(length=140,nullable=false) private String description;
	@Column(length=60,nullable=false) private String secret_question;
	@Column(length=60,nullable=false) private String secret_answer;
	private boolean privacy;
	private int visits;
	@OneToMany (mappedBy="buitter") @Sort(type=SortType.COMPARATOR, comparator = Buit.BuitComparator.class)
	private Set<Buit> mybuits;
 	@ManyToMany (mappedBy="followers", cascade=CascadeType.ALL)
 	private Set<User> following = new HashSet<User>();
 	@ManyToMany
 	private Set<User> followers = new HashSet<User>();
 	@ManyToMany (mappedBy="favoritter") @Sort(type=SortType.COMPARATOR, comparator = Buit.BuitComparator.class)
 	private Set<Buit> favorites;
 	@OneToMany (cascade=CascadeType.ALL) 
 	@Sort(type=SortType.COMPARATOR, comparator = Event.EventComparator.class)  
 	private List<Event> events;
 	@ManyToMany (mappedBy="blacklistedBy", cascade=CascadeType.ALL)
 	private Set<User> blacklist = new HashSet<User>();
 	@ManyToMany
 	@JoinTable(name="users_blacklist")
 	private Set<User> blacklistedBy = new HashSet<User>();
 	@OneToMany(mappedBy="owner")
 	private Set<UserList> myUserLists = new HashSet<UserList>();
 	@ManyToMany(mappedBy="users", cascade=CascadeType.ALL)
 	private Set<UserList> userListsIn = new HashSet<UserList>();
	
 	User(){
	}
		
	public User(String name, String surname, String username, String password, 
			String description, String secret_question, String secret_answer, 
			Date creationDate, int visits, boolean privacy, byte[] photo, byte[] backgroundImage){		
		this.setUsername(username);
		this.setPassword(password);
		this.setName(name);
		this.setSurname(surname);
		this.setDescription(description);
		this.setSecretAnswer(secret_answer);
		this.setSecretQuestion(secret_question);
		this.setPhoto(photo);
		this.creationDate = creationDate;
		this.setPrivacy(privacy);
		this.visits = visits;
		this.backgroundImage = backgroundImage;
	}
	
	public String getName() {
		return name;
	}

	public Set<Buit> getMybuits() {
		return mybuits;
	}

//	public void setMybuits(Set<Buit> mybuits) {
//		if(mybuits == null)
//			throw new IllegalArgumentException();
//		this.mybuits = mybuits;
//	}

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
		if(photo != null) {
			thumbnailPhoto = thumbnailGenerator(photo);
		}
	}
	
	public byte[] getThumbnailPhoto() {
		return thumbnailPhoto;
	}
	
	public byte[] getBackgroundImage() {
		return backgroundImage;
	}
	
	public void setBackgroundImage(byte[] backgroundImage) {
		this.backgroundImage = backgroundImage;
	}

	public List<Event> getEvents() {
		return events;
	}

//	public void setEvents(List<Event> events) {
//		if(events == null)
//			throw new IllegalArgumentException();
//		this.events = events;
//	}
	
	public Set<User> getBlacklist() {
		return blacklist;
	}
	
	public void setBlacklist(Set<User> blacklist) {
		if(blacklist == null) {
			throw new IllegalArgumentException();
		}
		this.blacklist = blacklist;
	}
	
	public Set<User> getBlacklistedBy() {
		return blacklistedBy;
	}
	
	public void setBlacklistedBy(Set<User> blacklistedBy) {
		if(blacklistedBy == null) {
			throw new IllegalArgumentException();
		}
		this.blacklistedBy = blacklistedBy;
	}
	
	public Set<UserList> getMyUserLists() {
		return myUserLists;
	}
	
	public void setMyUserLists(Set<UserList> myUserLists) {
		this.myUserLists = myUserLists;
	}
	
	public Set<UserList> getUserListsIn() {
		return userListsIn;
	}
	
	public void setUserListsIn(Set<UserList> userListsIn) {
		this.userListsIn = userListsIn;
	}
	
	public void follow(User user) {
		if(user == null || followers.contains(user)) {
			throw new IllegalArgumentException();
		}
		followers.add(user);
		getEvents().add(new FollowedEvent(new Date(), user));
		user.getFollowing().add(this);
	}
	
	public void unfollow(User user) {
		if(user == null || !followers.contains(user)) {
			throw new IllegalArgumentException();
		}
		followers.remove(user);
		user.getFollowing().remove(this);
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
	
	public void addEvent(Event e){
		this.events.add(e);
	}
	
	public void blacklistUser(User user) {
		if(user == null || blacklistedBy.contains(user)) {
			throw new IllegalArgumentException();
		}
		blacklistedBy.add(user);
		user.getBlacklist().add(this);
	}
	
	public void unBlacklistUser(User user) {
		if(user == null || !getBlacklistedBy().contains(user)) {
			throw new IllegalArgumentException();
		}
		getBlacklistedBy().remove(user);
		user.getBlacklist().remove(this);
	}
	
	public boolean isBlacklisted(User user) {
		if(user == null) {
			throw new IllegalArgumentException();
		}
		return blacklist.contains(user);
	}
	
	public Set<Buit> filterMyBuits(BuitFilter bf){
		Set<Buit> filteredBuits = new HashSet<Buit>();
		for (Buit buit : mybuits) {
			if(bf.eval(buit))
				filteredBuits.add(buit);
		}
		return filteredBuits;
	}
	
	public Date getDate(){
		return creationDate;
	}
	
//	public void setDate(Date date){
//		if(date == null)
//			throw new IllegalArgumentException();
//		this.creationDate = date;
//	}
	
	public void addVisit() {
		this.visits++;
	}
	
	public void removeVisit() {
		if(visits == 0)
			throw new IllegalStateException();
		this.visits--;
	}
	
	public boolean checkPassword(String password) {
		return this.password.equals(password);
	}
	
	public void addBuit(Buit buit) {
		this.mybuits.add(buit);
	}
	
	public void removeBuit(Buit buit) {
		if(favorites.contains(buit)) {
			unfavorite(buit);
		}
		this.mybuits.remove(buit);
	}
	
	public void addNewListIn(UserList userList) {
		if(!userListsIn.contains(userList)) {
			userListsIn.add(userList);
			userList.addUser(this);
		}
	}
	
	public void addNewList(UserList userList) {
		if(!myUserLists.contains(userList)) {
			myUserLists.add(userList);
		}
	}
	
	public void removeList(UserList userList) {
		if(myUserLists.contains(userList)) {
			myUserLists.remove(userList);
		}
	}
	
	private byte[] thumbnailGenerator(byte[] photo) {
		try {
			InputStream is = new ByteArrayInputStream(photo);
			BufferedImage bufferedPhoto = ImageIO.read(is);
			
			BufferedImage bufferedThumbnail = Scalr.resize(bufferedPhoto, 150, 150);
			
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(bufferedThumbnail, "jpg", baos);
			baos.flush();
			byte[] byteThumbnail = baos.toByteArray();
			baos.close();
			
			return byteThumbnail;
		} catch (IOException e) {
			return photo;
		}		
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
	
	@Override
	public String toString() {
		return "User [name=" + name + ", surname=" + surname + ", username="
				+ username + "]";
	}
}
