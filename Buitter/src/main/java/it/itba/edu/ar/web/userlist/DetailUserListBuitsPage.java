package it.itba.edu.ar.web.userlist;

import java.util.ArrayList;
import java.util.List;

import it.itba.edu.ar.domain.buit.Buit;
import it.itba.edu.ar.domain.user.User;
import it.itba.edu.ar.domain.userlist.UserList;
import it.itba.edu.ar.web.ListBuitsPanel;
import it.itba.edu.ar.web.base.BasePage;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;

public class DetailUserListBuitsPage extends BasePage {
	
	public DetailUserListBuitsPage(final IModel<UserList> modelUserList) {
		add(new DetailUserListHeaderPanel("userListHeaderPanel", modelUserList));
		
		WebMarkupContainer emptyBuitsContainer = new WebMarkupContainer("emptyBuitsContainer") {
			public boolean isVisible() {
				return modelUserList.getObject().getUsers().isEmpty();
			}
		};
		
		
		WebMarkupContainer notEmptyBuitsContainer = new WebMarkupContainer("notEmptyBuitsContainer") {
			public boolean isVisible() {
				return !modelUserList.getObject().getUsers().isEmpty();
			}
		};
		
		IModel<List<Buit>> buitsModel = new LoadableDetachableModel<List<Buit>>() {
			@Override
			protected List<Buit> load() {
				List<Buit> buits = new ArrayList<Buit>();
				modelUserList.detach();
				for(User user: modelUserList.getObject().getUsers()) {
					buits.addAll(user.getMybuits());
				}

				return buits;
			}
		};
		
		notEmptyBuitsContainer.add(new ListBuitsPanel("listBuitsPanel", buitsModel));
		buitsModel.detach();
		add(emptyBuitsContainer);
		add(notEmptyBuitsContainer);
	}
}
