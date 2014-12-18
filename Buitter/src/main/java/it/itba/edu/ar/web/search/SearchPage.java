package it.itba.edu.ar.web.search;

import it.itba.edu.ar.domain.user.User;
import it.itba.edu.ar.domain.user.UserRepo;
import it.itba.edu.ar.web.ListUsersPanel;
import it.itba.edu.ar.web.base.BasePage;

import java.util.List;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.ResourceModel;
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
		
		add(new Label("emptyQuery", new ResourceModel("registeredUsers")).setVisible(searchText == null));
		add(new Label("notEmptyQuery", new ResourceModel("peopleResults")).setVisible(searchText != null));
		add(new Label("searchText", searchText).setVisible(searchText != null));

		WebMarkupContainer emptyUsersContainer = new WebMarkupContainer("emptyUsersContainer") {
			public boolean isVisible() {
				return getUsers().isEmpty();
			}
		};
		emptyUsersContainer.add(new Label("emptyQuery2", new ResourceModel("noUsersRegistered")).setVisible(searchText == null));
		emptyUsersContainer.add(new Label("notEmptyQuery2", new ResourceModel("noPeopleResults")).setVisible(searchText != null));
		emptyUsersContainer.add(new Label("searchText2", searchText + ".").setVisible(searchText != null));
		add(emptyUsersContainer);
		
		
		WebMarkupContainer notEmptyUsersContainer = new WebMarkupContainer("notEmptyUsersContainer") {
			public boolean isVisible() {
				return !getUsers().isEmpty();
			}
		};
		notEmptyUsersContainer.add(new ListUsersPanel("listUsersPanel", usersModel));
		add(notEmptyUsersContainer);
		
	}
	
	private List<User> getUsers() {
		return usersModel.getObject();
	}
}
