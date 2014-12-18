package it.itba.edu.ar.web.users;

import it.itba.edu.ar.web.BuitterApp;
import it.itba.edu.ar.web.HomePage;
import it.itba.edu.ar.web.common.BuitterSession;
import it.itba.edu.ar.web.common.CookieService;
import it.itba.edu.ar.web.common.SessionProvider;

import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;

public class LogoutPanel extends Panel {
	public LogoutPanel(String id) {
		super(id);
		
		add(new BookmarkablePageLink<Void>("homePageLink", HomePage.class));
		add(new Link<Void>("logoutLink") {
			@Override
			public void onClick() {
				CookieService cookieService = ((BuitterApp) BuitterApp.get()).getCookieService();
			    cookieService.removeCookieIfPresent(SessionProvider.REMEMBER_ME_LOGIN_COOKIE);
			    cookieService.removeCookieIfPresent(SessionProvider.REMEMBER_ME_PASSWORD_COOKIE);
				BuitterSession.get().invalidate();
				setResponsePage(HomePage.class);
			}
		});
	}
}
