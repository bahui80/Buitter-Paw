package it.itba.edu.ar.junit;
import it.itba.edu.ar.domain.user.User;

import java.util.Date;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

public class UserTest {

	private User user;
	//private Buit buit;
	
	@Before
	public void setUser() {
		this.user = new User("Juan Martin", "Buireo", "jbuireo", "123456789", "Vamos buitter",
				"Quien es tu profesor preferido?", "Rinaldi", new Date(), 0, false, null, null);
//		this.buit = new Buit("testeo de favoritear repetido", user, new ArrayList<Hashtag>(),new Date());
	}
	
	@Test
	public void isPrivateTest() {
		Assert.assertFalse(user.getPrivacy());
		user.setPrivacy(true);
		Assert.assertTrue(user.getPrivacy());
	}
	
	@Test
	public void addVisitTest(){
		User u = new User("Juan Martin", "Buireo", "jbuireo", "123456789", "Vamos buitter",
				"Quien es tu profesor preferido?", "Rinaldi", new Date(), 0, false, null, null);
		Assert.assertTrue(u.getVisits() == 0);
		u.addVisit();
		Assert.assertTrue(u.getVisits() == 1);
	}
	
//	@Test
//	public void followUnfollowTest(){
//		User u = new User("facumenzella", "1234");
//		Assert.assertTrue(user.getFollowing().size() == 0);
//		u.follow(u);
//		Assert.assertTrue(user.getFollowing().size() == 1);
//		u.unfollow(u);
//		Assert.assertTrue(user.getFollowing().size() == 0);
//	}
	
//	@Test
//	public void favoriteUnfavoriteTest(){
//		Assert.assertTrue(user.getFavourites().size() == 0);
//		user.favorite(buit);
//		Assert.assertTrue(user.getFavourites().size() == 1);
//		user.unfavorite(buit);
//		Assert.assertTrue(user.getFavourites().size() == 0);
//	}
	
	/*
	 * Arguments validations
	 */
	
	@Test(expected = IllegalArgumentException.class)
	public void setNameTest(){
		user.setName("nombremaslargodetreintaydoscaracteres");
	}
	@Test(expected = IllegalArgumentException.class)
	public void setSurnameTest(){
		user.setSurname("apellidomaslargodetreintaydoscaracteres");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void setUsernameTest(){
		user.setUsername("nombredeusuariomaslargodetreintaydoscaracteres");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void setPasswordTest(){
		user.setPassword("passwordmaslargodetreintaydoscaracteres");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void setDescriptionTest(){
		user.setDescription("descripcionmaslargadecientocuarentacaracteresvancuarentayochohastaaca.Ladescripciontieneelmismolargodeunbuitynomaslargo.Siseponemaslargaladescripciontiraillegalargumentexception");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void setSecretQuestionTest(){
		user.setSecretQuestion("preguntamaslargodesesentacaracteres.Igualestosetomaautomaticamentedeunalistaasiqueesraroquesepase");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void setSecretAnswerTest(){
		user.setSecretAnswer("preguntamaslargadesesentacaracteres.lapreguntapuedeserbienlargatotaltoalnoimporta");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void followNullTest(){
		user.follow(null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void unfollowNullTest(){
		user.unfollow(null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void unfollowNotFollowedTest(){
		User u = new User("Juan Martin", "Buireo", "jbuireo", "123456789", "Vamos buitter",
				"Quien es tu profesor preferido?", "Rinaldi", new Date(), 0, false, null, null);
		user.unfollow(u);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void favoriteNullTest(){
		user.favorite(null);
	}
	
//	@Test(expected = IllegalArgumentException.class)
//	public void favoriteRepeated(){	
//		user.favorite(buit);
//		user.favorite(buit);
//	}
	
	@Test(expected = IllegalArgumentException.class)
	public void unfavoriteNullTest(){
		user.unfavorite(null);
	}
	
//	@Test(expected = IllegalArgumentException.class)
//	public void unfavoriteNotFavorited(){
//		User u = new User("a", "1");
//		u.unfavorite(buit);
//	}
	
	@Test(expected = IllegalStateException.class)
	public void removeVisitTest(){
		User u = new User("Juan Martin", "Buireo", "jbuireo", "123456789", "Vamos buitter",
				"Quien es tu profesor preferido?", "Rinaldi", new Date(), 0, false, null, null);
		u.removeVisit();
	}
}
