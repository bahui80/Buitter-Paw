package it.itba.edu.ar.web;

import it.itba.edu.ar.domain.user.User;
import it.itba.edu.ar.web.buit.ProfilePage;

import java.util.Date;
import java.util.List;

import org.apache.wicket.datetime.markup.html.basic.DateLabel;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

public class ListUsersPanel extends Panel {
	
	public ListUsersPanel(String id, final IModel<List<User>> modelUser) {
		super(id);
		
		add(new ListView<User>("users", modelUser) {
			@Override
			protected void populateItem(ListItem<User> item) {
				item.add(new Image("image", new ImageResourceReference(item.getModel())));
				PageParameters pgParameters = new PageParameters();
				pgParameters.add("username", item.getModelObject().getUsername());
				BookmarkablePageLink<Void> profilePageLink = new BookmarkablePageLink<Void>("profilePageLink", ProfilePage.class, pgParameters);
				profilePageLink.add(new Label("name", new PropertyModel<String>(item.getModel(), "name")));
				profilePageLink.add(new Label("surname", new PropertyModel<String>(item.getModel(), "surname")));
				item.add(profilePageLink);
				item.add(new Label("username", new PropertyModel<String>(item.getModel(), "username")));
				item.add(new Label("description", new PropertyModel<String>(item.getModel(), "description")));
				item.add(new DateLabel("date", new PropertyModel<Date>(item.getModel(), "date"), new DateFormatter()));
			}
		});
	}
}
