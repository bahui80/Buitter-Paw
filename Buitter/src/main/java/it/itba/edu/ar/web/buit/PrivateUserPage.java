package it.itba.edu.ar.web.buit;

import it.itba.edu.ar.domain.user.User;
import it.itba.edu.ar.web.base.BasePage;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;

public class PrivateUserPage extends BasePage {
	public PrivateUserPage(IModel<User> userModel) {
		add(new Label("username", new PropertyModel<String>(userModel, "username")));
	}
}
