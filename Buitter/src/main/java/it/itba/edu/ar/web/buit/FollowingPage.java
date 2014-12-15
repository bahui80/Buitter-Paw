package it.itba.edu.ar.web.buit;

import it.itba.edu.ar.domain.user.User;
import it.itba.edu.ar.domain.user.UserRepo;
import it.itba.edu.ar.web.ListUsersPanel;
import it.itba.edu.ar.web.base.BasePage;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

public class FollowingPage extends BasePage {
	@SpringBean
	private UserRepo userRepo;
	
	public FollowingPage(final PageParameters pgParameters) {
		final IModel<User> modelUser = new LoadableDetachableModel<User>() {
			@Override
			protected User load() {
				return userRepo.get(pgParameters.get("username").toString());
			}
		};
		
		setDefaultModel(modelUser);
		add(new HeaderPanel("headerPanel", modelUser));
		
		WebMarkupContainer emptyFollowingsContainer = new WebMarkupContainer("emptyFollowingsContainer") {
			public boolean isVisible() {
				return modelUser.getObject().getFollowing().isEmpty();
			}
		};
		
		WebMarkupContainer notEmptyFollowingContainer = new WebMarkupContainer("notEmptyFollowingsContainer") {
			public boolean isVisible() {
				return !modelUser.getObject().getFollowing().isEmpty();
			}
		};
		
		final IModel<List<User>> modelFollowing = new LoadableDetachableModel<List<User>>() {
			@Override
			protected List<User> load() {
				return new ArrayList<User>(modelUser.getObject().getFollowing());
			}
		};
		
		notEmptyFollowingContainer.add(new ListUsersPanel("listUsersPanel", modelFollowing));
		
		emptyFollowingsContainer.add(new Label("emptyFollowing", new PropertyModel<String>(modelUser, "username")));
		
		add(emptyFollowingsContainer);
		add(notEmptyFollowingContainer);
		
	}
}
