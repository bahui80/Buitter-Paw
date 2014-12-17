package it.itba.edu.ar.domain.publicity;

import javax.persistence.Entity;

import it.itba.edu.ar.domain.PersistentEntity;

@Entity
public class Publicity extends PersistentEntity {
	private String clientName;
	private String imageUrl;
	private String url;
	
	Publicity() {
	}

	public String getClientName() {
		return clientName;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public String getUrl() {
		return url;
	}
}
