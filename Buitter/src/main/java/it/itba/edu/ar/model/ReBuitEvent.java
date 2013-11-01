package it.itba.edu.ar.model;

import java.util.Date;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorColumn(discriminatorType = DiscriminatorType.INTEGER, name = "type")
@DiscriminatorValue("1")
public class ReBuitEvent extends Event {

	public ReBuitEvent(Date date, User firer) {
		this.setDate(date);
		this.setFirer(firer);
		this.setMessage("rebuitted your buit");
	}
	 ReBuitEvent(){
	 }
}
