package it.itba.edu.ar.web.buit;

import it.itba.edu.ar.domain.user.User;
import it.itba.edu.ar.domain.user.UserRepo;
import it.itba.edu.ar.web.base.BasePage;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
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
		
		
		emptyFollowersContainer.add(new Label("emptyFollower", new PropertyModel<String>(modelUser, "username")));
		
		add(emptyFollowersContainer);
		add(notEmptyFollowersContainer);
		
		
	}
}
