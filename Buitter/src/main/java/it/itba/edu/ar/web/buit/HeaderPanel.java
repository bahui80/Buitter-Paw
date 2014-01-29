package it.itba.edu.ar.web.buit;

import it.itba.edu.ar.domain.EntityModel;
import it.itba.edu.ar.domain.user.User;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;


public class HeaderPanel extends Panel {

	public HeaderPanel(String id, User user) {
		super(id);
		IModel<User> userModel = new EntityModel<User>(User.class, user);
		add(new Label("name", new PropertyModel<String>(userModel, "name")));
		add(new Label("surname", new PropertyModel<String>(userModel, "surname")));
		add(new Label("username", new PropertyModel<String>(userModel, "username")));
		add(new Label("description", new PropertyModel<String>(userModel, "description")));
	}
}
