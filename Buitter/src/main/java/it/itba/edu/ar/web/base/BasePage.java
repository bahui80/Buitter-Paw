package it.itba.edu.ar.web.base;

import it.itba.edu.ar.web.BuitterSession;
import it.itba.edu.ar.web.HomePage;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;

public class BasePage extends WebPage {

	public BasePage() {
		add(new BookmarkablePageLink<Void>("homePageLink", HomePage.class));
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
		add(loggedContainer);
		add(notLoggedContainer);
	}
}
