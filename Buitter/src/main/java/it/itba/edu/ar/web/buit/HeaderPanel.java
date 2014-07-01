package it.itba.edu.ar.web.buit;

import it.itba.edu.ar.domain.user.User;
import it.itba.edu.ar.web.ImageModel;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;


public class HeaderPanel extends Panel {

	public HeaderPanel(String id, final IModel<User> userModel) {
		super(id, userModel);
		add(new Label("name", new PropertyModel<String>(userModel, "name")));
		add(new Label("surname", new PropertyModel<String>(userModel, "surname")));
		add(new Label("username", new PropertyModel<String>(userModel, "username")));
		add(new Label("description", new PropertyModel<String>(userModel, "description")));
		add(new Image("userImage", new ImageModel(userModel))); 
		PageParameters pgParameters = new PageParameters();
		pgParameters.add("username", userModel.getObject().getUsername());
		BookmarkablePageLink<Void> profilePageLink = new BookmarkablePageLink<Void>("profilePageLink", ProfilePage.class, pgParameters);
		profilePageLink.add(new Label("totalBuits", new PropertyModel<Integer>(userModel, "buits.size()")));
		add(profilePageLink);
		BookmarkablePageLink<Void> followingPageLink = new BookmarkablePageLink<Void>("followingPageLink", FollowingPage.class, pgParameters);
		followingPageLink.add(new Label("totalFollowing", new PropertyModel<Integer>(userModel, "following.size()")));
		add(followingPageLink);
		BookmarkablePageLink<Void> followersPageLink = new BookmarkablePageLink<Void>("followersPageLink", FollowersPage.class, pgParameters);
		followersPageLink.add(new Label("totalFollowers", new PropertyModel<Integer>(userModel, "followers.size()")));
		add(followersPageLink);
	}
}
