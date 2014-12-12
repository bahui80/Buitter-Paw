package it.itba.edu.ar.web.users;

import it.itba.edu.ar.domain.EntityModel;
import it.itba.edu.ar.domain.event.Event;
import it.itba.edu.ar.domain.user.User;
import it.itba.edu.ar.web.DateFormatter;
import it.itba.edu.ar.web.ImageResourceReference;
import it.itba.edu.ar.web.base.BasePage;

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

public class ConnectPage extends BasePage {
	private IModel<User> modelUser;

	public ConnectPage(User user) {
		modelUser = new EntityModel<User>(User.class, user);
		setDefaultModel(modelUser);
		
		WebMarkupContainer emptyEventsContainer = new WebMarkupContainer("emptyEventsContainer") {
			public boolean isVisible() {
				return getUser().getEvents().isEmpty();
			}
		};
		
		final IModel<List<Event>> modelEvents = new LoadableDetachableModel<List<Event>>() {
			@Override
			protected List<Event> load() {
				return getUser().getEvents();
			}
		};
		
		add(new ListView<Event>("events", modelEvents) {
			@Override
			protected void populateItem(ListItem<Event> item) {
				item.add(new Image("image", new ImageResourceReference(new PropertyModel<User>(item.getModel(), "firer"))));
				item.add(new Label("username", new PropertyModel<String>(item.getModel(), "firer.username")));
				item.add(new DateLabel("date", new PropertyModel<Date>(item.getModel(), "date"), new DateFormatter()));
				item.add(new Label("message", new PropertyModel<String>(item.getModel(), "message")));
			}
		});
		
		add(emptyEventsContainer);
	}
	
	private User getUser() {
		return (User) modelUser.getObject();
	}
}
