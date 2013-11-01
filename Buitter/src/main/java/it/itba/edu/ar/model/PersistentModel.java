package it.itba.edu.ar.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class PersistentModel {
	@Id	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	public int getId() {
		return this.id;
	}
	
	public boolean isNew() {
		return id == 0;
	}
}