package it.itba.edu.ar.web.buit;

import java.util.ArrayList;
import java.util.List;

import it.itba.edu.ar.domain.user.User;
import it.itba.edu.ar.domain.user.UserRepo;
import it.itba.edu.ar.web.ImageResourceReference;
import it.itba.edu.ar.web.base.BasePage;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

public class FollowersPage extends BasePage {
	@SpringBean
	private UserRepo userRepo;
	
	public FollowersPage(final PageParameters pgParameters) {
		final IModel<User> modelUser = new LoadableDetachableModel<User>() {
			@Override
			protected User load() {
				return userRepo.get(pgParameters.get("username").toString());
			}
		};
		
		setDefaultModel(modelUser);
		add(new HeaderPanel("headerPanel", modelUser));
		
		WebMarkupContainer emptyFollowersContainer = new WebMarkupContainer("emptyFollowersContainer") {
			public boolean isVisible() {
				return modelUser.getObject().getFollowers().isEmpty();
			}
		};
		
		WebMarkupContainer notEmptyFollowersContainer = new WebMarkupContainer("notEmptyFollowersContainer") {
			public boolean isVisible() {
				return !modelUser.getObject().getFollowers().isEmpty();
			}
		};
		
		final IModel<List<User>> modelFollowers = new LoadableDetachableModel<List<User>>() {
			@Override
			protected List<User> load() {
				return new ArrayList<User>(modelUser.getObject().getFollowers());
			}
		};
		
		notEmptyFollowersContainer.add(new ListView<User>("followers", modelFollowers) {
			@Override
			protected void populateItem(final ListItem<User> item) {
				item.add(new Image("followerUserImage", new ImageResourceReference(item.getModel())));
				PageParameters pgParameters = new PageParameters();
				pgParameters.add("username", item.getModelObject().getUsername());
				BookmarkablePageLink<Void> profilePageLink = new BookmarkablePageLink<Void>("profilePageLink", ProfilePage.class, pgParameters);
				profilePageLink.add(new Label("followerUserName", new PropertyModel<String>(item.getModel(), "name")));
				profilePageLink.add(new Label("followerUserSurname", new PropertyModel<String>(item.getModel(), "surname")));
				item.add(profilePageLink);
				item.add(new Label("followerUsername", new PropertyModel<String>(item.getModel(), "username")));
				item.add(new Label("followerDescription", new PropertyModel<String>(item.getModel(), "description")));
			}
			
		});
		
		emptyFollowersContainer.add(new Label("emptyFollower", new PropertyModel<String>(modelUser, "username")));
		
		add(emptyFollowersContainer);
		add(notEmptyFollowersContainer);
		
		
	}
}
