package it.itba.edu.ar.web.users;

import it.itba.edu.ar.domain.user.User;
import it.itba.edu.ar.web.ListUsersPanel;
import it.itba.edu.ar.web.base.BasePage;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;

public class BlacklistPage extends BasePage {
	private IModel<User> modelUser;
	
	public BlacklistPage(IModel<User> modelUser) {
		this.modelUser = modelUser;
		setDefaultModel(modelUser);
		
		WebMarkupContainer emptyBlacklistContainer = new WebMarkupContainer("emptyBlacklistContainer") {
			public boolean isVisible() {
				return getUser().getBlacklist().isEmpty();
			}
		};
		
		final IModel<List<User>> modelUserList = new LoadableDetachableModel<List<User>>() {
			@Override
			protected List<User> load() {
				return new ArrayList<User>(getUser().getBlacklist());
			}
		};
		
		add(new ListUsersPanel("listUsersPanel", modelUserList));
		add(emptyBlacklistContainer);	
	}
	
	private User getUser() {
		return (User) modelUser.getObject();
	}
}
