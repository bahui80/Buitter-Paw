package it.itba.edu.ar.model;

import java.util.Date;

public class MentionedEvent extends Event {

	private User other_user;
	
	public MentionedEvent(Date date, User user, User other_user) {
		super(date, user);
		this.other_user = other_user;
	}

}
