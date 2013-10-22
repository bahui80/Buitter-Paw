package it.itba.edu.ar.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="rebuits")
public class ReBuit extends Buit {
	
	@ManyToOne private User user;
	@Temporal(TemporalType.DATE)@Column(nullable=false)private Date rebuit_date;
	
	public ReBuit(){
	}
	
}
