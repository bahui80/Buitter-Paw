package it.itba.edu.ar.web;

import it.itba.edu.ar.domain.buit.Buit;
import it.itba.edu.ar.domain.user.User;
import it.itba.edu.ar.domain.user.UserRepo;
import it.itba.edu.ar.web.base.BasePage;
import it.itba.edu.ar.web.buit.ProfilePage;

import java.util.ArrayList;
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

public class HomePage extends BasePage {
	@SpringBean	
	private UserRepo userRepo;
	
	public HomePage() {
		final BuitterSession session = BuitterSession.get();
		
		WebMarkupContainer emptyUserContainer = new WebMarkupContainer("emptyUserContainer") {
			public boolean isVisible() {
				return !session.isSignedIn();
			}
		};
		
		WebMarkupContainer notEmptyUserContainer = new WebMarkupContainer("notEmptyUserContainer") {
			public boolean isVisible() {
				return session.isSignedIn();
			}
		};
		
		IModel<List<User>> modelUsers = new LoadableDetachableModel<List<User>>() {
			@Override
			protected List<User> load() {
				List<User> userList = userRepo.getAll();
				if(userList.size() > 6) {
					return userList.subList(0, 6);
				} else {
					return userList;
				}
			}
		};
		
		IModel<List<Buit>> modelMyBuits = new LoadableDetachableModel<List<Buit>>() {
			@Override
			protected List<Buit> load() {
				return new ArrayList<Buit>(session.getUser().getMybuits());
			}
		};
		
		IModel<List<Buit>> modelFollowingBuits = new LoadableDetachableModel<List<Buit>>() {
			@Override
			protected List<Buit> load() {
				ArrayList<Buit> buits = new ArrayList<Buit>();
				for(User user: session.getUser().getFollowing()) {
					buits.addAll(user.getMybuits());
				}
				return buits;
			}
		};
		
		notEmptyUserContainer.add(new ListView<Buit>("myBuits", modelMyBuits) {
			@Override
			protected void populateItem(ListItem<Buit> item) {
				item.add(new Image("myBuitUserImage",new ImageResourceReference(new PropertyModel<User>(item.getModel(), "buitter"))));
				item.add(new Label("myBuitUsername", new PropertyModel<String>(item.getModel(), "buitter.username")));
				item.add(new DateLabel("myBuitDate", new PropertyModel<Date>(item.getModel(), "date"), new DateFormatter()));
				item.add(new Label("myBuitMessage", new MessageModel(item.getModel())).setEscapeModelStrings(false));
			}
		});
		
		notEmptyUserContainer.add(new ListView<Buit>("followingBuits", modelFollowingBuits) {
			@Override
			protected void populateItem(ListItem<Buit> item) {
				item.add(new Image("followingBuitUserImage",new ImageResourceReference(new PropertyModel<User>(item.getModel(), "buitter"))));
				item.add(new Label("followingBuitUsername", new PropertyModel<String>(item.getModel(), "buitter.username")));
				item.add(new DateLabel("followingBuitDate", new PropertyModel<Date>(item.getModel(), "date"), new DateFormatter()));
				item.add(new Label("followingBuitMessage", new MessageModel(item.getModel())).setEscapeModelStrings(false));
			}
		});
		
		emptyUserContainer.add(new ListView<User>("users", modelUsers) {
			@Override
			protected void populateItem(ListItem<User> item) {
				item.add(new Image("userImage",new ImageResourceReference(item.getModel())));
				item.add(new Label("userUsername", new PropertyModel<String>(item.getModel(), "username")));
				item.add(new Label("userDescription", new PropertyModel<String>(item.getModel(), "description")));
				PageParameters pgParameters = new PageParameters();
				pgParameters.add("username", item.getModelObject().getUsername());
				item.add(new BookmarkablePageLink<Void>("profilePageLink", ProfilePage.class, pgParameters));
			}	
		});
		
		add(emptyUserContainer);
		add(notEmptyUserContainer);
	}
}
