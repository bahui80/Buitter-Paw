package it.itba.edu.ar.web.buit;

import it.itba.edu.ar.domain.buit.Buit;
import it.itba.edu.ar.domain.user.User;
import it.itba.edu.ar.web.ListBuitsPanel;
import it.itba.edu.ar.web.base.BasePage;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;

public class FavoritesPage extends BasePage {
	private IModel<User> modelUser;
	
	
	public FavoritesPage(IModel<User> modelUser) {
		this.modelUser = modelUser;
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
		
		add(new ListBuitsPanel("listBuitsPanel", modelBuit));
		
		add(emptyFavoritesContainer);
	
	}
	
	private User getUser() {
		return (User) modelUser.getObject();
	}
}
