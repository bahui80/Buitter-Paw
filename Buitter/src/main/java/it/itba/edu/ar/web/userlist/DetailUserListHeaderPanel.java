package it.itba.edu.ar.web.userlist;

import it.itba.edu.ar.domain.userlist.UserList;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;

public class DetailUserListHeaderPanel extends Panel {

	public DetailUserListHeaderPanel(String id, final IModel<UserList> modelUserList) {
		super(id);
		
		Link<Void> userListUsersLink = new Link<Void>("userListUsersLink") {
			@Override
			public void onClick() {
				setResponsePage(new DetailUserListUsersPage(modelUserList));
			}
		};
		userListUsersLink.add(new Label("usersQty", new PropertyModel<Integer>(modelUserList, "users.size()")));
		
		Link<Void> userListBuitsLink = new Link<Void>("userListBuitsLink") {
			@Override
			public void onClick() {
				setResponsePage(new DetailUserListBuitsPage(modelUserList));
			}
		};
		userListBuitsLink.add(new Label("buitsQty", new PropertyModel<Integer>(modelUserList, "totalBuits")));
		
		
		add(userListUsersLink);
		add(userListBuitsLink);
		add(new Label("name", new PropertyModel<String>(modelUserList, "name")));
		add(new Label("description", new PropertyModel<String>(modelUserList, "description")));
	}

}
