package it.itba.edu.ar.web.search;

import it.itba.edu.ar.domain.user.User;
import it.itba.edu.ar.domain.user.UserRepo;
import it.itba.edu.ar.web.DateFormatter;
import it.itba.edu.ar.web.ImageResourceReference;
import it.itba.edu.ar.web.base.BasePage;
import it.itba.edu.ar.web.buit.ProfilePage;

import java.util.Date;
import java.util.List;

import org.apache.wicket.datetime.markup.html.basic.DateLabel;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

public class SearchPage extends BasePage {
	@SpringBean
	private UserRepo userRepo;
	private IModel<List<User>> usersModel;
	
	public SearchPage(final String searchText) {
		usersModel = new LoadableDetachableModel<List<User>>() {
			@Override
			protected List<User> load() {
				return userRepo.search(searchText);
			}
		};
		
		add(new Label("emptyQuery", "Registered users").setVisible(searchText == null));
		add(new Label("notEmptyQuery", "People results for ").setVisible(searchText != null));
		add(new Label("searchText", searchText).setVisible(searchText != null));

		WebMarkupContainer emptyUsersContainer = new WebMarkupContainer("emptyUsersContainer") {
			public boolean isVisible() {
				return getUsers().isEmpty();
			}
		};
		emptyUsersContainer.add(new Label("emptyQuery2", "There are no registered users so far.").setVisible(searchText == null));
		emptyUsersContainer.add(new Label("notEmptyQuery2", "No people results for ").setVisible(searchText != null));
		emptyUsersContainer.add(new Label("searchText2", searchText + ".").setVisible(searchText != null));
		add(emptyUsersContainer);
		
		
		WebMarkupContainer notEmptyUsersContainer = new WebMarkupContainer("notEmptyUsersContainer") {
			public boolean isVisible() {
				return !getUsers().isEmpty();
			}
		};
		notEmptyUsersContainer.add(new ListView<User>("users", usersModel) {
			@Override
			protected void populateItem(ListItem<User> item) {
				item.add(new Image("userImage", new ImageResourceReference(item.getModel())));
				PageParameters pgParameters = new PageParameters();
				pgParameters.add("username", item.getModelObject().getUsername());
				BookmarkablePageLink<Void> profilePageLink = new BookmarkablePageLink<Void>("profilePageLink", ProfilePage.class, pgParameters);
				profilePageLink.add(new Label("userUserName", new PropertyModel<String>(item.getModel(), "name")));
				profilePageLink.add(new Label("userUserSurname", new PropertyModel<String>(item.getModel(), "surname")));
				item.add(profilePageLink);
				item.add(new Label("userUsername", new PropertyModel<String>(item.getModel(), "username")));
				item.add(new DateLabel("registeredDate", new PropertyModel<Date>(item.getModel(), "date"), new DateFormatter()));
			}
		});
		add(notEmptyUsersContainer);
		
	}
	
	private List<User> getUsers() {
		return usersModel.getObject();
	}
}
