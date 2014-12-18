package it.itba.edu.ar.junit;
import it.itba.edu.ar.domain.buit.Hashtag;

import org.junit.Before;
import org.junit.Test;

public class HashtagTest {

	private Hashtag hashtag;
		
	/*
	 * Arguments validations
	 */
	
	@Before
	public void setHashtag() {
		this.hashtag = new Hashtag("pepe", 3);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testRemoveBuit(){
		hashtag.removeBuit(null);
	}	
}
