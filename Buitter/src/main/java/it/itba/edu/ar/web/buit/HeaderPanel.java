package it.itba.edu.ar.web.buit;

import it.itba.edu.ar.domain.user.User;
import it.itba.edu.ar.web.BuitterSession;
import it.itba.edu.ar.web.ImageModel;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;


public class HeaderPanel extends Panel {

	public HeaderPanel(String id, final IModel<User> userModel) {
		super(id, userModel);

		final BuitterSession session = BuitterSession.get();
		
		add(new Label("name", new PropertyModel<String>(userModel, "name")));
		add(new Label("surname", new PropertyModel<String>(userModel, "surname")));
		add(new Label("username", new PropertyModel<String>(userModel, "username")));
		add(new Label("description", new PropertyModel<String>(userModel, "description")));
		add(new Image("userImage", new ImageModel(userModel))); 
		
		PageParameters pgParameters = new PageParameters();
		pgParameters.add("username", userModel.getObject().getUsername());
		
		BookmarkablePageLink<Void> profilePageLink = new BookmarkablePageLink<Void>("profilePageLink", ProfilePage.class, pgParameters);
		profilePageLink.add(new Label("totalBuits", new PropertyModel<Integer>(userModel, "mybuits.size()")));
		add(profilePageLink);
		
		BookmarkablePageLink<Void> followingPageLink = new BookmarkablePageLink<Void>("followingPageLink", FollowingPage.class, pgParameters);
		followingPageLink.add(new Label("totalFollowing", new PropertyModel<Integer>(userModel, "following.size()")));
		add(followingPageLink);
		
		BookmarkablePageLink<Void> followersPageLink = new BookmarkablePageLink<Void>("followersPageLink", FollowersPage.class, pgParameters);
		followersPageLink.add(new Label("totalFollowers", new PropertyModel<Integer>(userModel, "followers.size()")));
		add(followersPageLink);
		
		add(new Label("totalVisits", new PropertyModel<Integer>(userModel, "visits")));
		
		WebMarkupContainer followContainer = new WebMarkupContainer("followContainer") {
			public boolean isVisible() {
				return session.isSignedIn() && !session.getUser().getUsername().equals(userModel.getObject().getUsername()) && !session.getUser().getFollowing().contains(userModel.getObject());
			}
		};
		followContainer.add(new Link<Void>("follow") {
			
			public void onClick() {
				userModel.getObject().follow(session.getUser());
				// redirigir a donde estaba
			}
		});
		
		
		WebMarkupContainer unfollowContainer = new WebMarkupContainer("unfollowContainer") {
			public boolean isVisible() {
				return session.isSignedIn() && !session.getUser().getUsername().equals(userModel.getObject().getUsername()) && session.getUser().getFollowing().contains(userModel.getObject());
			}
		};
		unfollowContainer.add(new Link<Void>("unfollow") {
			
			public void onClick() {
				userModel.getObject().unfollow(session.getUser());
				// redirigir a donde estaba
			}
		});
		
		
		WebMarkupContainer editProfileContainer = new WebMarkupContainer("editProfileContainer") {
			public boolean isVisible() {
				return session.isSignedIn() && session.getUser().getUsername().equals(userModel.getObject().getUsername());
			}
		};
		editProfileContainer.add(new Link<Void>("edit") {
			
			public void onClick() {
				// redirigir a editar
			}
		});
		
		add(followContainer);
		add(unfollowContainer);
		add(editProfileContainer);
	}
}
