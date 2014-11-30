package it.itba.edu.ar.web.buit;

import it.itba.edu.ar.domain.buit.Buit;
import it.itba.edu.ar.domain.buit.BuitRepo;
import it.itba.edu.ar.domain.user.User;
import it.itba.edu.ar.domain.user.UserRepo;
import it.itba.edu.ar.web.BuitterSession;
import it.itba.edu.ar.web.DateFormatter;
import it.itba.edu.ar.web.ImageResourceReference;
import it.itba.edu.ar.web.MessageModel;
import it.itba.edu.ar.web.base.BasePage;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.wicket.datetime.markup.html.basic.DateLabel;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.validation.validator.StringValidator.MaximumLengthValidator;

public class ProfilePage extends BasePage {
	@SpringBean	
	private UserRepo userRepo;
	@SpringBean
	private BuitRepo buitRepo;
	private transient String buitText;
	private IModel<User> modelUser;
	
	public ProfilePage(final PageParameters pgParameters) {
		modelUser = new LoadableDetachableModel<User>() {
			@Override
			protected User load() {
				return userRepo.get(pgParameters.get("username").toString());
			}
		};
		
//		if(getUser() == null) {
//			// TODO: mostrar pagina de error (ese usuario no existe)
//		}
		setDefaultModel(modelUser);
		add(new HeaderPanel("headerPanel", modelUser));
		
		Form<ProfilePage> buitForm = new Form<ProfilePage>("buitForm", new CompoundPropertyModel<ProfilePage>(this)) {
			@Override
			protected void onSubmit() {
				buitRepo.buit(buitText, BuitterSession.get().getUser());
				buitText = null;
			}
		};
		buitForm.add(new TextArea<String>("buitText").setRequired(true).add(new MaximumLengthValidator(140)));
		buitForm.add(new Button("buitButton", new ResourceModel("buitButton")));
		buitForm.add(new FeedbackPanel("feedback"));

		WebMarkupContainer emptyBuitsContainer = new WebMarkupContainer("emptyBuitsContainer") {
			public boolean isVisible() {
				modelUser.detach();
				return getUser().getMybuits().isEmpty();
			}
		};
		
		emptyBuitsContainer.add(new Label("emptyBuits", new PropertyModel<String>(modelUser, "username")));
		
		WebMarkupContainer notEmptyBuitsContainer = new WebMarkupContainer("notEmptyBuitsContainer") {
			public boolean isVisible() {
				modelUser.detach();
				return !getUser().getMybuits().isEmpty();
			}
		};	
		
		final IModel<List<Buit>> modelBuit = new LoadableDetachableModel<List<Buit>>() {
			
			@Override
			protected List<Buit> load() {
				return new ArrayList<Buit>(getUser().getMybuits());
			}
		};
			
		notEmptyBuitsContainer.add(new ListView<Buit>("buits", modelBuit) {
			@Override
			protected void populateItem(final ListItem<Buit> item) {
				item.add(new Image("buitUserImage", new ImageResourceReference(new PropertyModel<User>(item.getModel(), "buitter"))));
				item.add(new Label("buitUserUsername", new PropertyModel<String>(item.getModel(), "buitter.username")));
				item.add(new DateLabel("buitDate", new PropertyModel<Date>(item.getModel(), "date"), new DateFormatter()));
				item.add(new Label("buitMessage", new MessageModel(item.getModel())).setEscapeModelStrings(false));
				WebMarkupContainer rebuitTextContainer = new WebMarkupContainer("rebuitTextContainer") {
					public boolean isVisible() {
						return item.getModelObject().getIsrebuit();
					}
				};
				rebuitTextContainer.add(new Label("rebuitedText", new PropertyModel<String>(item.getModel(), "user.username")));
				item.add(rebuitTextContainer);
				Link<Buit> deleteButton = new Link<Buit>("deleteButton", item.getModel()) {
					public void onClick() {
						buitRepo.removeBuit(getModelObject(), BuitterSession.get().getUser());
						modelBuit.detach();
					}
				};
				Link<Void> favoriteButton = new Link<Void>("favoriteButton") {
					public void onClick() {
						item.getModelObject().addFavorite(BuitterSession.get().getUser());
					}
				};
				Link<Void> unfavoriteButton = new Link<Void>("unfavoriteButton") {
					public void onClick() {
						item.getModelObject().removeFavorite(BuitterSession.get().getUser());
					}
				};
				Link<Void> rebuitButton = new Link<Void>("rebuitButton") {
					public void onClick() {
						buitRepo.rebuit(item.getModelObject(), BuitterSession.get().getUser());
					}
				};
				deleteButton.setVisible(BuitterSession.get().isSignedIn() && item.getModelObject().getBuitter().equals(BuitterSession.get().getUser()));
				favoriteButton.setVisible(BuitterSession.get().isSignedIn() && !BuitterSession.get().getUser().getFavourites().contains(item.getModelObject()));
				unfavoriteButton.setVisible(BuitterSession.get().isSignedIn() && BuitterSession.get().getUser().getFavourites().contains(item.getModelObject()));
				rebuitButton.setVisible(BuitterSession.get().isSignedIn() && !item.getModelObject().getBuitter().equals(BuitterSession.get().getUser()));
				item.add(deleteButton);
				item.add(favoriteButton);
				item.add(unfavoriteButton);
				item.add(rebuitButton);
			}
		});
		
//		user.addVisit();
		add(buitForm);
		add(emptyBuitsContainer);
		add(notEmptyBuitsContainer);
		
		// TODO: HACER LO DEL NEWBUITCONTAINER

	}
	
	private User getUser() {
		return (User) modelUser.getObject();
	}
}
