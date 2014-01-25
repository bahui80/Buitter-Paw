package it.itba.edu.ar.web.users;

import it.itba.edu.ar.web.HomePage;
import it.itba.edu.ar.web.base.BasePage;

import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.PropertyModel;

public class LoginPage extends BasePage {
	private String value;
	
	public LoginPage() {
		Form<Void> form = new Form<Void>("loginForm");
		form.add(new TextField<String>("username", new PropertyModel<String>(this, "value")));
		form.add(new Button("signInButton") {
			@Override
			public void onSubmit() {
				setResponsePage(HomePage.class);
			}
		});
		add(form);
	}
}
