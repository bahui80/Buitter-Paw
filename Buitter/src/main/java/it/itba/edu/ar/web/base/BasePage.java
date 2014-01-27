package it.itba.edu.ar.web.base;

import it.itba.edu.ar.web.BuitterSession;
import it.itba.edu.ar.web.HomePage;
import it.itba.edu.ar.web.users.LoginPage;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.Link;

public class BasePage extends WebPage {

	public BasePage() {
		WebMarkupContainer loggedContainer = new WebMarkupContainer("logged") {
			public boolean isVisible() {
				return BuitterSession.get().isSignedIn();
			}
		};
		
		WebMarkupContainer notLoggedContainer = new WebMarkupContainer("notLogged") {
			public boolean isVisible() {
				return !BuitterSession.get().isSignedIn();
			}
		};
		
		notLoggedContainer.add(new Link<Void>("loginPageLink") {
			public void onClick() {
				setResponsePage(LoginPage.class);
			}
		});
		
		notLoggedContainer.add(new Link<Void>("registrationPageLink") {
			public void onClick() {
//				setResponsePage(RegistrationPage.class);
			}
		});
		
		loggedContainer.add(new Link<Void>("logoutLink") {
			public void onClick() {
				BuitterSession.get().invalidate();
			}
		});
		
		add(new BookmarkablePageLink<Void>("homePageLink", HomePage.class));
		add(loggedContainer);
		add(notLoggedContainer);
	}
}
