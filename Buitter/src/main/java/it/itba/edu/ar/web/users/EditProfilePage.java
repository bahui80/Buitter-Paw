package it.itba.edu.ar.web.users;

import it.itba.edu.ar.domain.EntityModel;
import it.itba.edu.ar.domain.user.User;
import it.itba.edu.ar.web.HomePage;
import it.itba.edu.ar.web.base.BasePage;

import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.CompoundPropertyModel;

public class EditProfilePage extends BasePage {
	
	public EditProfilePage(User user) {
		Form<User> form = new Form<User>("form", new CompoundPropertyModel<User>(new EntityModel<User>(User.class, user)));
		
		form.add(new UserInfoPanel("fieldsPanel"));
		form.add(new Button("save") {
			@Override
			public void onSubmit() {
				System.out.println("CONFIRME");
			}
		});
		form.add(new Button("cancel") {
			@Override
			public void onSubmit() {
				setResponsePage(HomePage.class);
			}
		});
		add(form);
	}
}
