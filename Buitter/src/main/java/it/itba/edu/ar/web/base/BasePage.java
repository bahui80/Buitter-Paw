package it.itba.edu.ar.web.base;

import it.itba.edu.ar.domain.publicity.Publicity;
import it.itba.edu.ar.domain.publicity.PublicityRepo;
import it.itba.edu.ar.domain.user.User;
import it.itba.edu.ar.domain.user.UserRepo;
import it.itba.edu.ar.web.BuitterApp;
import it.itba.edu.ar.web.BuitterSession;
import it.itba.edu.ar.web.CookieService;
import it.itba.edu.ar.web.ExternalImage;
import it.itba.edu.ar.web.HomePage;
import it.itba.edu.ar.web.SessionProvider;
import it.itba.edu.ar.web.buit.FavoritesPage;
import it.itba.edu.ar.web.buit.ProfilePage;
import it.itba.edu.ar.web.search.SearchPage;
import it.itba.edu.ar.web.userlist.UserListsPage;
import it.itba.edu.ar.web.users.BlacklistPage;
import it.itba.edu.ar.web.users.ConnectPage;
import it.itba.edu.ar.web.users.EditProfilePage;
import it.itba.edu.ar.web.users.LoginPage;
import it.itba.edu.ar.web.users.RegistrationPage;
import it.itba.edu.ar.web.users.StatsPage;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.wicket.extensions.ajax.markup.html.autocomplete.AutoCompleteTextField;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.pages.RedirectPage;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

public class BasePage extends WebPage {
	@SpringBean
	private UserRepo userRepo;
	@SpringBean 
	private PublicityRepo publicityRepo;
	private transient String searchText;

	public BasePage() {
		final BuitterSession session = BuitterSession.get();
		/*
		 * Logged bar actions
		 */
		WebMarkupContainer loggedContainer = new WebMarkupContainer("logged") {
			public boolean isVisible() {
				return session.isSignedIn();
			}
		};
		
		final IModel<Publicity> publicityModel = new LoadableDetachableModel<Publicity>() {
			@Override
			protected Publicity load() {
				return publicityRepo.getRandomPublicity();
			}
		};
		Link<Void> publicityLink = new Link<Void>("publicityLink") {
			@Override
			public void onClick() {
				if (publicityModel.getObject() != null) {
					setResponsePage(new RedirectPage(publicityModel.getObject().getUrl()));
				} else {
					setResponsePage(HomePage.class);
				}
			}
		};
		publicityLink.add(new ExternalImage("publicity", new PropertyModel<String>(publicityModel, "imageUrl")));
		add(publicityLink);
		
		loggedContainer.add(new Link<Void>("logoutLink") {
			public void onClick() {
				CookieService cookieService = ((BuitterApp) BuitterApp.get()).getCookieService();
			    cookieService.removeCookieIfPresent(SessionProvider.REMEMBER_ME_LOGIN_COOKIE);
			    cookieService.removeCookieIfPresent(SessionProvider.REMEMBER_ME_PASSWORD_COOKIE);
				session.invalidate();
				setResponsePage(HomePage.class);
			}
		});
		PageParameters pgParameters = new PageParameters();
		if(session.isSignedIn()) {
			pgParameters.add("username", session.getUser().getUsername());
		}
		BookmarkablePageLink<Void> profilePageLink = new BookmarkablePageLink<Void>("profilePageLink", ProfilePage.class, pgParameters);
		profilePageLink.add(new Label("currentUser", new PropertyModel<String>(session.getUserModel(), "username")));
		loggedContainer.add(profilePageLink);
		
		/*
		 * Not logged bar actions
		 */
		WebMarkupContainer notLoggedContainer = new WebMarkupContainer("notLogged") {
			public boolean isVisible() {
				return !session.isSignedIn();
			}
		};
		
		notLoggedContainer.add(new Link<Void>("loginPageLink") {
			public void onClick() {
				setResponsePage(LoginPage.class);
			}
		});
		
		notLoggedContainer.add(new Link<Void>("registrationPageLink") {
			public void onClick() {
				setResponsePage(RegistrationPage.class);
			}
		});
		
		loggedContainer.add(new Link<Void>("userListsPageLink") {
			@Override
			public void onClick() {
				setResponsePage(new UserListsPage(session.getUserModel()));
			}
		});
		
		loggedContainer.add(new Link<Void>("blacklistPageLink") {
			@Override
			public void onClick() {
				setResponsePage(new BlacklistPage(session.getUserModel()));
			}
		});
		
		loggedContainer.add(new Link<Void>("favoritesPageLink") {
			@Override
			public void onClick() {
				setResponsePage(new FavoritesPage(session.getUserModel()));
			}
		});
		
		loggedContainer.add(new Link<Void>("statsLink") {
			@Override
			public void onClick() {
				setResponsePage(new StatsPage(session.getUserModel()));
			}
		});
		
		loggedContainer.add(new Link<Void>("editProfileLink") {
			@Override
			public void onClick() {
				setResponsePage(new EditProfilePage(session.getUserModel()));
			}
		});
		
		Link<Void> connectPageLink = new Link<Void>("connectPageLink") {
			@Override
			public void onClick() {
				setResponsePage(new ConnectPage(session.getUserModel()));
			}
		};
		connectPageLink.add(new Label("qtyNotif", new PropertyModel<Integer>(session.getUserModel(), "events.size()")));
		loggedContainer.add(connectPageLink);
		
		Form<Void> searchForm = new Form<Void>("searchForm") {
			@Override
			protected void onSubmit() {
				super.onSubmit();
				setResponsePage(new SearchPage(searchText));
			}
		};
		add(new BookmarkablePageLink<Void>("homePageLink", HomePage.class));
		
		searchForm.add(new AutoCompleteTextField<String>("searchText", new PropertyModel<String>(this, "searchText")) {
			@Override
			protected Iterator<String> getChoices(String input) {
				List<String> coincidences = new ArrayList<String>();
				List<User> users = userRepo.getAll();
				for(User user: users) {
					if(user.getUsername().toUpperCase().startsWith(input.toUpperCase())) {
						coincidences.add(user.getUsername());
					}
				}
				return coincidences.iterator();
			}
		});
		add(searchForm);
		add(loggedContainer);
		add(notLoggedContainer);
	}
}
