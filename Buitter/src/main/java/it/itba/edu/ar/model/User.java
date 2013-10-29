package it.itba.edu.ar.model;

import java.util.Arrays;
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
	
	@Column (updatable = false, length=32, unique=true,nullable=false)private String username;
	@Column(length=32,nullable=false)private String password;
	@Column(length=32,nullable=false)private String name;
	@Column(length=32,nullable=false)private String surname;
	@Column(length=140,nullable=false)private String description;
	@Column(length=60,nullable=false)private String secret_question;
	@Column(length=60,nullable=false)private String secret_answer;
	@Temporal(TemporalType.TIMESTAMP)@Column(nullable=false) private Date creationDate;
	private boolean privacy;
	private int visits;		
	@Lob private byte[] photo; 
	@OneToMany (mappedBy="buitter") @Sort(type=SortType.COMPARATOR, comparator = Buit.BuitComparator.class)
		private Set<Buit> mybuits;
 	@ManyToMany (mappedBy="followers", cascade=CascadeType.ALL) private Set<User> following;
 	@ManyToMany private Set<User> followers;
 	@OneToMany private List<Buit> favorites;
 	@OneToMany (mappedBy="user")  private List<Event> events;
	
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

	@Override
	public String toString() {
		return "User [name=" + name + ", surname=" + surname + ", username="
				+ username + "]";
	}
	
	public String getDate(){
		return creationDate.toString();
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
				+ ((creationDate == null) ? 0 : creationDate.hashCode());
		result = prime * result
				+ ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((events == null) ? 0 : events.hashCode());
		result = prime * result
				+ ((favorites == null) ? 0 : favorites.hashCode());
		result = prime * result
				+ ((followers == null) ? 0 : followers.hashCode());
		result = prime * result
				+ ((following == null) ? 0 : following.hashCode());
		result = prime * result + ((mybuits == null) ? 0 : mybuits.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result
				+ ((password == null) ? 0 : password.hashCode());
		result = prime * result + Arrays.hashCode(photo);
		result = prime * result + (privacy ? 1231 : 1237);
		result = prime * result
				+ ((secret_answer == null) ? 0 : secret_answer.hashCode());
		result = prime * result
				+ ((secret_question == null) ? 0 : secret_question.hashCode());
		result = prime * result + ((surname == null) ? 0 : surname.hashCode());
		result = prime * result
				+ ((username == null) ? 0 : username.hashCode());
		result = prime * result + visits;
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
		if (creationDate == null) {
			if (other.creationDate != null)
				return false;
		} else if (!creationDate.equals(other.creationDate))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (events == null) {
			if (other.events != null)
				return false;
		} else if (!events.equals(other.events))
			return false;
		if (favorites == null) {
			if (other.favorites != null)
				return false;
		} else if (!favorites.equals(other.favorites))
			return false;
		if (followers == null) {
			if (other.followers != null)
				return false;
		} else if (!followers.equals(other.followers))
			return false;
		if (following == null) {
			if (other.following != null)
				return false;
		} else if (!following.equals(other.following))
			return false;
		if (mybuits == null) {
			if (other.mybuits != null)
				return false;
		} else if (!mybuits.equals(other.mybuits))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (!Arrays.equals(photo, other.photo))
			return false;
		if (privacy != other.privacy)
			return false;
		if (secret_answer == null) {
			if (other.secret_answer != null)
				return false;
		} else if (!secret_answer.equals(other.secret_answer))
			return false;
		if (secret_question == null) {
			if (other.secret_question != null)
				return false;
		} else if (!secret_question.equals(other.secret_question))
			return false;
		if (surname == null) {
			if (other.surname != null)
				return false;
		} else if (!surname.equals(other.surname))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		if (visits != other.visits)
			return false;
		return true;
	}
	
}