package it.itba.edu.ar.web.users;

import it.itba.edu.ar.domain.user.UserRepo;
import it.itba.edu.ar.web.BuitterSession;
import it.itba.edu.ar.web.HomePage;
import it.itba.edu.ar.web.base.BasePage;

import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

public class LoginPage extends BasePage {
	@SpringBean
	private UserRepo users;
	private transient String username;
	private transient String password;
	
	public LoginPage() {
		LogoutPanel panel = new LogoutPanel("logoutPanel") {
			public boolean isVisible() {
				return BuitterSession.get().isSignedIn();
			}
		};
		
		Form<LoginPage> form = new Form<LoginPage>("loginForm", new CompoundPropertyModel<LoginPage>(this)) {
			@Override
			public boolean isVisible() {
				return !BuitterSession.get().isSignedIn();
			}
			
			@Override
			protected void onSubmit() {
				BuitterSession session = BuitterSession.get();

				if (session.signIn(username, password, users)) {
					session.setAttribute("userId", username);
					setResponsePage(HomePage.class);
				} else {
					error(getString("invalidCredentials"));
				}
			}
		};
		form.add(new TextField<String>("username").setRequired(true));
		form.add(new PasswordTextField("password"));
		form.add(new Button("signInButton", new ResourceModel("signInButton")));
		form.add(new FeedbackPanel("feedback"));
		add(panel);
		add(form);
	}
}