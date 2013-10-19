package it.itba.edu.ar.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
@Entity
public class Buit {
	@Id @GeneratedValue(strategy=javax.persistence.GenerationType.AUTO)	private Integer id;
	@Column(length=500, nullable=false)	private String message;
	@ManyToOne private User user;
	@Temporal(TemporalType.DATE)@Column(nullable=false)private Date date;
	
	public Buit(){
	}
	
	public Buit(int id, String message, User user, Date date){
		if(id == 0 || message == null || message.length() < 1 || user == null )
			throw new IllegalArgumentException();
		this.id = id;
		this.message = message;
		this.user = user;
		this.date = date;
	}

	public Buit(String message,  User user, Date date){
		if(message == null || message.length() < 1 || user == null )
			throw new IllegalArgumentException();
		
		this.message = message;
		this.user = user;
		this.date = date;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		if(id == 0)
			throw new IllegalArgumentException();
		this.id = id;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		if(message == null || message.length() < 1) {
			throw new IllegalArgumentException();
		}
		this.message = message;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		if(user == null)
			throw new IllegalArgumentException();
		this.user = user;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "Buit [id=" + id + ", message=" + message + ", username="
				+ user.getUsername() + ", date=" + date + "]";
	}

	public String getDate(){
		return date.toString();
	}
}

