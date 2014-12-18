package it.itba.edu.ar.web.users;

import it.itba.edu.ar.domain.user.UserRepo;
import it.itba.edu.ar.web.BuitterApp;
import it.itba.edu.ar.web.HomePage;
import it.itba.edu.ar.web.base.BasePage;
import it.itba.edu.ar.web.common.BuitterSession;
import it.itba.edu.ar.web.common.CookieService;
import it.itba.edu.ar.web.common.SessionProvider;

import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

public class LoginPage extends BasePage {
	@SpringBean
	private UserRepo users;
	private transient String username;
	private transient String password;
	private transient Boolean rememberMe;
	
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
		            if(rememberMe) {
		                CookieService cookieService = ((BuitterApp) BuitterApp.get()).getCookieService();
		                cookieService.saveCookie(SessionProvider.REMEMBER_ME_LOGIN_COOKIE, username);
		                cookieService.saveCookie(SessionProvider.REMEMBER_ME_PASSWORD_COOKIE, password);
		            }
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
		form.add(new CheckBox("rememberMe", new PropertyModel<Boolean>(this, "rememberMe")));
		form.add(new Link<Void>("forgotPasswordLink") {
			@Override
			public void onClick() {
				setResponsePage(ForgotPasswordPage.class);
			}
		});
		add(panel);
		add(form);
	}
}
