package it.itba.edu.ar.web.userlist;

import it.itba.edu.ar.domain.user.User;
import it.itba.edu.ar.domain.userlist.UserList;
import it.itba.edu.ar.web.DateFormatter;
import it.itba.edu.ar.web.ImageVerified;
import it.itba.edu.ar.web.ProfPicResourceReference;
import it.itba.edu.ar.web.base.BasePage;
import it.itba.edu.ar.web.buit.ProfilePage;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.wicket.datetime.markup.html.basic.DateLabel;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

public class DetailUserListUsersPage extends BasePage {
	
	public DetailUserListUsersPage(final IModel<UserList> modelUserList) {
		add(new DetailUserListHeaderPanel("userListHeaderPanel", modelUserList));
		
		WebMarkupContainer emptyUsersContainer = new WebMarkupContainer("emptyUsersContainer") {
			public boolean isVisible() {
				return modelUserList.getObject().getUsers().isEmpty();
			}
		};

		WebMarkupContainer notEmptyUsersContainer = new WebMarkupContainer("notEmptyUsersContainer") {
			public boolean isVisible() {
				return !modelUserList.getObject().getUsers().isEmpty();
			}
		};
		
		final IModel<List<User>> modelUser = new LoadableDetachableModel<List<User>>() {
			@Override
			protected List<User> load() {
				return new ArrayList<User>(modelUserList.getObject().getUsers());
			}
		};
		
		notEmptyUsersContainer.add(new ListView<User>("users", modelUser) {
			@Override
			protected void populateItem(final ListItem<User> item) {
				item.add(new Image("image", new ProfPicResourceReference(item.getModel().getObject().getPhoto(), item.getModel().getObject().getUsername())));
				PageParameters pgParameters = new PageParameters();
				pgParameters.add("username", item.getModelObject().getUsername());
				BookmarkablePageLink<Void> profilePageLink = new BookmarkablePageLink<Void>("profilePageLink", ProfilePage.class, pgParameters);
				profilePageLink.add(new Label("name", new PropertyModel<String>(item.getModel(), "name")));
				profilePageLink.add(new Label("surname", new PropertyModel<String>(item.getModel(), "surname")));
				item.add(profilePageLink);
				item.add(new ImageVerified("imgVerified", item.getModelObject().getFollowers().size()));
				item.add(new Label("username", new PropertyModel<String>(item.getModel(), "username")));
				item.add(new Label("description", new PropertyModel<String>(item.getModel(), "description")));
				item.add(new DateLabel("date", new PropertyModel<Date>(item.getModel(), "date"), new DateFormatter()));
				item.add(new Link<User>("deleteButton", item.getModel()) {
					@Override
					public void onClick() {
						modelUserList.getObject().removeUser(item.getModelObject());
						modelUserList.detach();
						modelUser.detach();
					}
				});
			}
		});
		add(notEmptyUsersContainer);
		add(emptyUsersContainer);
	}
}
