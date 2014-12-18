package it.itba.edu.ar.web.users;

import it.itba.edu.ar.domain.event.Event;
import it.itba.edu.ar.domain.user.User;
import it.itba.edu.ar.web.base.BasePage;
import it.itba.edu.ar.web.common.ProfPicResourceReference;

import java.util.Date;
import java.util.List;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.PropertyModel;
import org.ocpsoft.prettytime.PrettyTime;

public class ConnectPage extends BasePage {
	private IModel<User> modelUser;

	public ConnectPage(IModel<User> modelUser) {
		this.modelUser = modelUser;
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
				item.add(new Image("image", new ProfPicResourceReference(item.getModel().getObject().getFirer().getThumbnailPhoto(), item.getModel().getObject().getFirer().getUsername())));
				item.add(new Label("username", new PropertyModel<String>(item.getModel(), "firer.username")));
				PrettyTime p = new PrettyTime();
				item.add(new Label("date", p.format(new PropertyModel<Date>(item.getModel(), "date").getObject())));
				item.add(new Label("message", new PropertyModel<String>(item.getModel(), "message")));
			}
		});
		
		add(emptyEventsContainer);
	}
	
	private User getUser() {
		return (User) modelUser.getObject();
	}
}
