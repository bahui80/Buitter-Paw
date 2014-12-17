package it.itba.edu.ar.web;

import it.itba.edu.ar.domain.buit.Buit;
import it.itba.edu.ar.domain.buit.BuitRepo;

import java.util.Date;
import java.util.List;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.ocpsoft.prettytime.PrettyTime;

public class ListBuitsPanel extends Panel {
	@SpringBean
	private BuitRepo buitRepo;
	
	public ListBuitsPanel(String id, final IModel<List<Buit>> modelBuit) {
		super(id);
		
		add(new ListView<Buit>("buits", modelBuit) {
			@Override
			protected void populateItem(final ListItem<Buit> item) {
				item.add(new Image("image", new ProfPicResourceReference(item.getModel().getObject().getBuitter().getPhoto(), item.getModel().getObject().getBuitter().getUsername())));
				item.add(new Label("username", new PropertyModel<String>(item.getModel(), "buitter.username")));
				PrettyTime p = new PrettyTime();
				item.add(new Label("date", p.format(new PropertyModel<Date>(item.getModel(), "date").getObject())));
				item.add(new Label("message", new MessageModel(item.getModel())).setEscapeModelStrings(false));
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
				Link<Buit> favoriteButton = new Link<Buit>("favoriteButton", item.getModel()) {
					public void onClick() {
						getModelObject().addFavorite(BuitterSession.get().getUser());
						modelBuit.detach();
					}
				};
				Link<Buit> unfavoriteButton = new Link<Buit>("unfavoriteButton", item.getModel()) {
					public void onClick() {
						getModelObject().removeFavorite(BuitterSession.get().getUser());
						modelBuit.detach();
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
	}
}
