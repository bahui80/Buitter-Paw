package it.itba.edu.ar.web;

import it.itba.edu.ar.domain.buit.Buit;

import java.util.Date;
import java.util.List;

import org.apache.wicket.datetime.markup.html.basic.DateLabel;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;

public class HomePageListBuitsPanel extends Panel {

	public HomePageListBuitsPanel(String id, IModel<List<Buit>> modelBuit) {
		super(id);
		
		add(new ListView<Buit>("buits", modelBuit) {
			@Override
			protected void populateItem(ListItem<Buit> item) {
				item.add(new Image("image", new ProfPicResourceReference(item.getModel().getObject().getBuitter().getPhoto(), item.getModel().getObject().getBuitter().getUsername())));
				item.add(new Label("username", new PropertyModel<String>(item.getModel(), "buitter.username")));
				item.add(new DateLabel("date", new PropertyModel<Date>(item.getModel(), "date"), new DateFormatter()));
				item.add(new Label("message", new MessageModel(item.getModel())).setEscapeModelStrings(false));
			}
		});
	}
}
