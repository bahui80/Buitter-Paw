package it.itba.edu.ar.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="rebuits")
public class ReBuit extends Buit {
	
	@ManyToOne private User user;
	@OneToOne (cascade = CascadeType.ALL) private Buit buit;
	@Temporal(TemporalType.TIMESTAMP)@Column(nullable=false)private Date rebuit_date;
	
	ReBuit(){
	}
	
	public ReBuit(Buit buit, User user, Date rebuit_date){
		super(buit.getMessage(),user, new ArrayList<Hashtag>(buit.getHashtags()),
				rebuit_date);
		List<Url> urls = new ArrayList<Url>();
		if(rebuit_date == null || user==null || buit == null)
			throw new IllegalArgumentException();
		for (Url u : buit.getUrls()) {
			Url newU = new Url(u.getUrl(),u.getBuiturl());
			urls.add(newU);
		}
		this.setUrls(urls);
		this.buit = buit;
		this.rebuit_date = buit.getDate();
		this.user = buit.getBuitter();
	}

	//GETTERS
	public User getUser(){
		return this.user;
	}
	
	//MISCELANEOUS
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((buit == null) ? 0 : buit.hashCode());
		result = prime * result
				+ ((rebuit_date == null) ? 0 : rebuit_date.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		ReBuit other = (ReBuit) obj;
		if (buit == null) {
			if (other.buit != null)
				return false;
		} else if (!buit.equals(other.buit))
			return false;
		if (rebuit_date == null) {
			if (other.rebuit_date != null)
				return false;
		} else if (!rebuit_date.equals(other.rebuit_date))
			return false;
		return true;
	}
	
}
