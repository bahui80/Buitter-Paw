package it.itba.edu.ar.web;

import it.itba.edu.ar.domain.EntityModel;
import it.itba.edu.ar.domain.user.User;
import it.itba.edu.ar.domain.userlist.UserList;
import it.itba.edu.ar.web.buit.ProfilePage;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.wicket.datetime.markup.html.basic.DateLabel;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

public class ListUsersPanel extends Panel {
	public ListUsersPanel(String id, final IModel<List<User>> modelUser) {
		super(id);
		
		add(new ListView<User>("users", modelUser) {
			@Override
			protected void populateItem(final ListItem<User> item) {
				item.add(new Image("image", new ProfPicResourceReference(item.getModel().getObject().getPhoto(), item.getModel().getObject().getUsername())));
				PageParameters pgParameters = new PageParameters();
				pgParameters.add("username", item.getModelObject().getUsername());
				BookmarkablePageLink<Void> profilePageLink = new BookmarkablePageLink<Void>("profilePageLink", ProfilePage.class, pgParameters);
				profilePageLink.add(new Label("name", new PropertyModel<String>(item.getModel(), "name")));
				profilePageLink.add(new Label("surname", new PropertyModel<String>(item.getModel(), "surname")));
				item.add(profilePageLink);
				item.add(new ImageVerified("imgVerified", item.getModelObject().getFollowers().size()));
				item.add(new Label("username", new PropertyModel<String>(item.getModel(), "username")));
				item.add(new Label("description", new PropertyModel<String>(item.getModel(), "description")));
				item.add(new DateLabel("date", new PropertyModel<Date>(item.getModel(), "date"), new DateFormatter()));
				
				Link<User> addBlacklistButton = new Link<User>("addBlacklistButton", item.getModel()) {
					public void onClick() {
						item.getModel().getObject().blacklistUser(BuitterSession.get().getUser());
					}
				};
				Link<User> removeBlacklistButton = new Link<User>("removeBlacklistButton", item.getModel()) {
					public void onClick() {
						item.getModel().getObject().unBlacklistUser(BuitterSession.get().getUser());
						modelUser.detach();
					}
				};
				
				IModel<List<UserList>> modelUserList = new LoadableDetachableModel<List<UserList>>() {
					@Override
					protected List<UserList> load() {
						return new ArrayList<UserList>(BuitterSession.get().getUser().getMyUserLists());
					}
				};
				
				IModel<UserList> modelUserList2 = new EntityModel<UserList>(UserList.class);
				
				DropDownChoice<UserList> listchoices = new DropDownChoice<UserList>("lists", modelUserList2, modelUserList) {
					@Override
					protected void onSelectionChanged(UserList newSelection) {
						super.onSelectionChanged(newSelection);
						newSelection.addUser(item.getModelObject());
					}
					
					@Override
					public boolean isVisible() {
						return BuitterSession.get().isSignedIn() && !BuitterSession.get().getUser().equals(item.getModelObject()) && (!item.getModelObject().isBlacklisted(BuitterSession.get().getUser()) || item.getModelObject().getPrivacy() == false);
					}
					
					
					@Override
					protected boolean wantOnSelectionChangedNotifications() {
						return true;
					}
				};
				listchoices.setChoiceRenderer(new ChoiceRenderer<UserList>("name"));
				item.add(listchoices);
				addBlacklistButton.setVisible(BuitterSession.get().isSignedIn() && !BuitterSession.get().getUser().equals(item.getModelObject()) && !BuitterSession.get().getUser().getBlacklist().contains(item.getModelObject()));
				removeBlacklistButton.setVisible(BuitterSession.get().isSignedIn() && !BuitterSession.get().getUser().equals(item.getModelObject()) && BuitterSession.get().getUser().getBlacklist().contains(item.getModelObject()));
				
				item.add(removeBlacklistButton);
				item.add(addBlacklistButton);
			}
		});
	}
}
