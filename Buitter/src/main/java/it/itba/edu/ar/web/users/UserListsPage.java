package it.itba.edu.ar.web.users;

import it.itba.edu.ar.domain.user.User;
import it.itba.edu.ar.domain.userlist.DuplicatedListException;
import it.itba.edu.ar.domain.userlist.UserList;
import it.itba.edu.ar.domain.userlist.UserListRepo;
import it.itba.edu.ar.web.base.BasePage;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.ComponentFeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.validation.validator.StringValidator.MaximumLengthValidator;

public class UserListsPage extends BasePage {
	@SpringBean
	private UserListRepo userListRepo;
	
	private IModel<User> modelUser;
	private String listName;
	private String listDescription;
	
	public UserListsPage(final IModel<User> modelUser) {
		this.modelUser = modelUser;
		setDefaultModel(modelUser);
		
		WebMarkupContainer emptyListsContainer = new WebMarkupContainer("emptyListsContainer") {
			public boolean isVisible() {
				return getUser().getMyUserLists().isEmpty();
			}
		};
		final Form<UserListsPage> form = new Form<UserListsPage>("form", new CompoundPropertyModel<UserListsPage>(this));
		final RequiredTextField<String> nameTxtField = new RequiredTextField<String>("listName");
		nameTxtField.add(new MaximumLengthValidator(32));
		form.add(nameTxtField);
		form.add(new ComponentFeedbackPanel("name_error", nameTxtField));
		form.add(new TextField<String>("listDescription"));
		form.setVisible(false);
		form.add(new Button("add") {
			@Override
			public void onSubmit() {
				try {
					userListRepo.add(new UserList(modelUser.getObject(), listName, listDescription));
					listName = null;
					listDescription = null;
					form.setVisible(false);
				} catch (DuplicatedListException e) {
					nameTxtField.error(getString(e.getClass().getSimpleName(), new Model<DuplicatedListException>(e)));
				}	
			}
		});
		
		add(new Link<Void>("showForm") {
			@Override
			public void onClick() {
				form.setVisible(true);
			}
		});
		
		final IModel<List<UserList>> userListModel = new LoadableDetachableModel<List<UserList>>() {
			@Override
			protected List<UserList> load() {
				return new ArrayList<UserList>(modelUser.getObject().getMyUserLists());
			}
		};
		add(new ListView<UserList>("lists", userListModel) {
			@Override
			protected void populateItem(final ListItem<UserList> item) {
				Link<Void> detailListPage = new Link<Void>("detailListPage") {
					@Override
					public void onClick() {
						
					}
				};
				detailListPage.add(new Label("name", new PropertyModel<String>(item.getModel(), "name")));		
				item.add(detailListPage);
				item.add(new Label("description", new PropertyModel<String>(item.getModel(), "description")));
				item.add(new Label("members", new PropertyModel<String>(item.getModel(), "users.size()")));
				item.add(new Link<Void>("deleteList") {
					@Override
					public void onClick() {
						userListRepo.deleteList(item.getModelObject());
					}
				});
			}
		});
		
		add(form);
		add(emptyListsContainer);
	}
	
	private User getUser() {
		return (User) modelUser.getObject();
	}
}
