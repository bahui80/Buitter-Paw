package it.itba.edu.ar.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import it.itba.edu.ar.domain.buit.Buit;
import it.itba.edu.ar.domain.user.User;
import it.itba.edu.ar.web.base.BasePage;

import org.apache.wicket.datetime.markup.html.basic.DateLabel;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.PropertyModel;

public class HomePage extends BasePage {
	public HomePage() {
		final BuitterSession session = BuitterSession.get();
		
		WebMarkupContainer notEmptyUserContainer = new WebMarkupContainer("notEmptyUserContainer") {
			public boolean isVisible() {
				return session.isSignedIn();
			}
		};	
		
		IModel<List<Buit>> modelMyBuits = new LoadableDetachableModel<List<Buit>>() {
			@Override
			protected List<Buit> load() {
				return new ArrayList<Buit>(session.getUser().getMybuits());
			}
		};
		
		notEmptyUserContainer.add(new ListView<Buit>("myBuits", modelMyBuits) {
			@Override
			protected void populateItem(ListItem<Buit> item) {
				item.add(new Image("myBuitUserImage",new ImageResourceReference(new PropertyModel<User>(item.getModel(), "buitter"))));
				item.add(new Label("myBuitUsername", new PropertyModel<String>(item.getModel(), "buitter.username")));
				item.add(new DateLabel("myBuitDate", new PropertyModel<Date>(item.getModel(), "date"), new DateFormatter()));
				item.add(new Label("myBuitMessage", new MessageModel(item.getModel())).setEscapeModelStrings(false));
			}
		});
		
		add(notEmptyUserContainer);
	}
}
