package it.itba.edu.ar.web.buit;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.wicket.datetime.markup.html.basic.DateLabel;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.PropertyModel;

import it.itba.edu.ar.domain.EntityModel;
import it.itba.edu.ar.domain.buit.Buit;
import it.itba.edu.ar.domain.user.User;
import it.itba.edu.ar.web.DateFormatter;
import it.itba.edu.ar.web.HomePage;
import it.itba.edu.ar.web.ImageResourceReference;
import it.itba.edu.ar.web.MessageModel;
import it.itba.edu.ar.web.base.BasePage;

public class FavoritesPage extends BasePage {
	private IModel<User> modelUser;
	
	
	public FavoritesPage(User user) {
		if(user == null) {
			setResponsePage(HomePage.class);
		}
		modelUser = new EntityModel<User>(User.class, user);
		setDefaultModel(modelUser);
		
		WebMarkupContainer emptyFavoritesContainer = new WebMarkupContainer("emptyFavoritesContainer") {
			public boolean isVisible() {
				return getUser().getFavourites().isEmpty();
			}
		};
		

		final IModel<List<Buit>> modelBuit = new LoadableDetachableModel<List<Buit>>() {
			@Override
			protected List<Buit> load() {
				return new ArrayList<Buit>(getUser().getFavourites());
			}
		};
		
		add(new ListView<Buit>("favorites", modelBuit) {
			@Override
			protected void populateItem(ListItem<Buit> item) {
				item.add(new Image("image", new ImageResourceReference(new PropertyModel<User>(item.getModel(), "buitter"))));
				item.add(new Label("username", new PropertyModel<String>(item.getModel(), "buitter.username")));
				item.add(new DateLabel("date", new PropertyModel<Date>(item.getModel(), "date"), new DateFormatter()));
				item.add(new Label("message", new MessageModel(item.getModel())).setEscapeModelStrings(false));
			}
		});
		
		add(emptyFavoritesContainer);
	}
	
	private User getUser() {
		return (User) modelUser.getObject();
	}
}
