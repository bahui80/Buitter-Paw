package it.itba.edu.ar.web.buit;

import it.itba.edu.ar.domain.buit.Buit;
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
	private UserRepo users;
	private transient String buitText;
	
	public ProfilePage(final PageParameters pgParameters) {
		final IModel<User> modelUser = new LoadableDetachableModel<User>() {
			@Override
			protected User load() {
				return users.get(pgParameters.get("username").toString());
			}
		};
		User user = modelUser.getObject();
		if(user == null) {
			// TODO: mostrar pagina de error (ese usuario no existe)
		}
		add(new HeaderPanel("headerPanel", modelUser));
		
		Form<ProfilePage> buitForm = new Form<ProfilePage>("buitForm", new CompoundPropertyModel<ProfilePage>(this)) {
			
			@Override
			protected void onSubmit() {
				//TODO: GUARDAR A BASE EL BUIT
				System.out.println(buitText);
			}
		};
		buitForm.add(new TextArea<String>("buitText").setRequired(true).add(new MaximumLengthValidator(140)));
		buitForm.add(new Button("buitButton", new ResourceModel("buitButton")));
		buitForm.add(new FeedbackPanel("feedback"));

		WebMarkupContainer emptyBuitsContainer = new WebMarkupContainer("emptyBuitsContainer") {
			public boolean isVisible() {
				return modelUser.getObject().getBuits().isEmpty();
			}
		};
		
		emptyBuitsContainer.add(new Label("emptyBuits", new PropertyModel<String>(modelUser, "username")));
		
		WebMarkupContainer notEmptyBuitsContainer = new WebMarkupContainer("notEmptyBuitsContainer") {
			public boolean isVisible() {
				return !modelUser.getObject().getBuits().isEmpty();
			}
		};	
		
		
		IModel<List<Buit>> modelBuit = new LoadableDetachableModel<List<Buit>>() {
			
			@Override
			protected List<Buit> load() {
				return new ArrayList<Buit>(modelUser.getObject().getBuits());
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
				Link<Void> deleteButton = new Link<Void>("deleteButton") {
					@Override
					public void onClick() {
						System.out.println("DELETE BUTTON CLICKED");
					}
				};
				Link<Void> favoriteButton = new Link<Void>("favoriteButton") {
					public void onClick() {
						System.out.println("FAVORITE BUTTON CLICKED");
					}
				};
				Link<Void> unfavoriteButton = new Link<Void>("unfavoriteButton") {
					public void onClick() {
						System.out.println("UNFAVORITE BUTTON CLICKED");
					}
				};
				Link<Void> rebuitButton = new Link<Void>("rebuitButton") {
					public void onClick() {
						System.out.println("REBUIT BUTTON CLICKED");
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
				
		user.addVisit();
		add(buitForm);
		add(emptyBuitsContainer);
		add(notEmptyBuitsContainer);
		
		// TODO: HACER LO DEL NEWBUITCONTAINER
		
		//TODO: VER BIEN ACA PORQUE ME TIRA EL PROBLEMA DE QUE NO PUEDE SERIALIAR EL USER. HAY QUE VER SI LA CLASE 
		// ESTA BIEN HECHA

	}
}
