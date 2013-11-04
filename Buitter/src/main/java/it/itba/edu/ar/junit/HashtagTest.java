package it.itba.edu.ar.junit;

import it.itba.edu.ar.model.Hashtag;
import it.itba.edu.ar.model.User;

import org.junit.Test;

public class HashtagTest {

	private Hashtag hashtag;
	private User user;
		
	/*
	 * Arguments validations
	 */
	
	@Test(expected = IllegalArgumentException.class)
	public void setCountTest(){
		hashtag.setCount(0);
	}

	
}
