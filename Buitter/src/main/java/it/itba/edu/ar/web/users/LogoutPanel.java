package it.itba.edu.ar.web.users;

import it.itba.edu.ar.web.BuitterSession;
import it.itba.edu.ar.web.HomePage;

import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;

public class LogoutPanel extends Panel {
	public LogoutPanel(String id) {
		super(id);
		
		add(new BookmarkablePageLink<Void>("homePageLink", HomePage.class));
		add(new Link<Void>("logoutLink") {
			public void onClick() {
				BuitterSession.get().invalidate();
			}
		});
	}
}
