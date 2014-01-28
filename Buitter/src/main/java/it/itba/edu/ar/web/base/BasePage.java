package it.itba.edu.ar.web.base;

import it.itba.edu.ar.web.BuitterSession;
import it.itba.edu.ar.web.HomePage;
import it.itba.edu.ar.web.buit.ProfilePage;
import it.itba.edu.ar.web.users.LoginPage;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.PropertyModel;

public class BasePage extends WebPage {

	public BasePage() {
		/*
		 * Logged bar actions
		 */
		WebMarkupContainer loggedContainer = new WebMarkupContainer("logged") {
			public boolean isVisible() {
				return BuitterSession.get().isSignedIn();
			}
		};
		
		loggedContainer.add(new Link<Void>("logoutLink") {
			public void onClick() {
				BuitterSession.get().invalidate();
			}
		});
		BookmarkablePageLink<Void> profilePageLink = new BookmarkablePageLink<Void>("profilePageLink", ProfilePage.class);
		profilePageLink.add(new Label("currentUser", new PropertyModel<String>(BuitterSession.get().getUserModel(), "username")));
		loggedContainer.add(profilePageLink);
		
		/*
		 * Not logged bar actions
		 */
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
		
		
		add(new BookmarkablePageLink<Void>("homePageLink", HomePage.class));
		add(loggedContainer);
		add(notLoggedContainer);
	}
}
