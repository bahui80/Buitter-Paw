import it.itba.edu.ar.model.Buit;
import it.itba.edu.ar.model.Hashtag;
import it.itba.edu.ar.model.User;

import java.util.ArrayList;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

public class BuitTest {
	
	private Buit buit;
	private User user;
	
	@Before
	public void setUser() {
		this.user = new User("Juan Martin", "Buireo", "jbuireo", "123456789", "Vamos buitter",
				"Quien es tu profesor preferido?", "Rinaldi", new Date(), 0, false, null);
		this.buit = new Buit("testeo de favoritear repetido", user, new ArrayList<Hashtag>(),new Date());
	}
	
//	@Test
//	public void addRemoveRebuitTest(){
//		Assert.assertTrue(buit.getRebuits().size() == 0);
//		ReBuit rb = new ReBuit(buit, user, new Date());
//		buit.addRebuit(rb);
//		Assert.assertTrue(buit.getRebuits().size() == 1);
//	}
	
//	@Test
//	public void addRemoveFavTest(){
//		Assert.assertTrue(buit.getFavoritter().size() == 0);
//		buit.addFavorite(user);
//		Assert.assertTrue(buit.getFavoritter().size() == 1);
//		buit.removeFavorite(user);
//		Assert.assertTrue(buit.getFavoritter().size() == 0);
//	}
	
	/*
	 * Arguments validations
	 */
	
	@Test(expected = IllegalArgumentException.class)
	public void setNullMessageTest(){
		buit.setMessage(null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void setBigMessageTest(){
		String a  = "a";
		for (int i = 0; i < 510; i++) {
			a = a + "a";
		}
		buit.setMessage(a);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void setEmptyMessageTest(){
		buit.setMessage("");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void setNullUrls(){
		buit.setUrls(null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void setNullRebuits(){
		buit.setRebuits(null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void addNullRebuitTest(){
		buit.addRebuit(null);
	}
	
//	@Test(expected = IllegalArgumentException.class)
//	public void addRepeatedRebuitTest(){
//		User u = new User("a", "a");
//		ReBuit rb = new ReBuit(buit,u ,new Date());
//		buit.addRebuit(rb);
//		buit.addRebuit(rb);
//	}
	
//	@Test(expected = IllegalArgumentException.class)
//	public void addRebuitFromOtherBuitTest(){
//		User u = new User("a", "a");
//		Buit b = new Buit("testeo de asd repetido", user, new ArrayList<Hashtag>(),new Date());
//		ReBuit rb = new ReBuit(b,u ,new Date());
//		buit.addRebuit(rb);
//	}
	
	@Test(expected = IllegalArgumentException.class)
	public void addFavoriteNullTest(){
		buit.addFavorite(null);
	}
	
//	@Test(expected = IllegalArgumentException.class)
//	public void addRepeatedFavTest(){
//		buit.addFavorite(user);
//		buit.addFavorite(user);
//	}
	
	@Test(expected = IllegalArgumentException.class)
	public void removeNullFavTest(){
		buit.removeFavorite(null);
	}
	
//	@Test(expected = IllegalArgumentException.class)
//	public void removeNotFavTest(){
//		User u = new User("a", "a");
//		buit.removeFavorite(u);
//	}
}
