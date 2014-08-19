package it.itba.edu.ar.web.buit;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import it.itba.edu.ar.domain.buit.Buit;
import it.itba.edu.ar.domain.buit.BuitRepo;
import it.itba.edu.ar.domain.buit.Hashtag;
import it.itba.edu.ar.domain.user.User;
import it.itba.edu.ar.web.BuitterSession;
import it.itba.edu.ar.web.DateFormatter;
import it.itba.edu.ar.web.ImageResourceReference;
import it.itba.edu.ar.web.base.BasePage;

import org.apache.wicket.datetime.markup.html.basic.DateLabel;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

public class HashtagPage extends BasePage {
	@SpringBean
	private BuitRepo buitRepo;
	
	public HashtagPage(final PageParameters pgParameters) {
		final IModel<Hashtag> modelHashtag = new LoadableDetachableModel<Hashtag>() {
			@Override
			protected Hashtag load() {
				return buitRepo.getHashtag(pgParameters.get("hashtag").toString());
			}
		};
		
		add(new Label("hashtag", new PropertyModel<String>(modelHashtag, "hashtag")));
		add(new Label("username", new PropertyModel<String>(modelHashtag, "user.username")));
		add(new DateLabel("date", new PropertyModel<Date>(modelHashtag, "date"), new DateFormatter()));
		add(new Label("hashtag2", new PropertyModel<String>(modelHashtag, "hashtag")));
		
		WebMarkupContainer emptyBuitsContainer = new WebMarkupContainer("emptyBuitsContainer") {
			public boolean isVisible() {
				return modelHashtag.getObject().getBuits().isEmpty();
			}
		};
		
		emptyBuitsContainer.add(new Label("hashtag3", new PropertyModel<String>(modelHashtag, "hashtag")));
		
		WebMarkupContainer notEmptyBuitsContainer = new WebMarkupContainer("notEmptyBuitsContainer") {
			public boolean isVisible() {
				return !modelHashtag.getObject().getBuits().isEmpty();
			}
		};	
	
		final IModel<List<Buit>> modelBuit = new LoadableDetachableModel<List<Buit>>() {
			@Override
			protected List<Buit> load() {
				List<Buit> buits = new ArrayList<Buit>();
				for(Buit buit: modelHashtag.getObject().getBuits()) {
					if(BuitterSession.get().isSignedIn() || buit.getBuitter().getPrivacy() == false) {
						buits.add(buit);
					}
				}
				return buits;
			}
		};
		
		notEmptyBuitsContainer.add(new ListView<Buit>("buits", modelBuit) {
			@Override
			protected void populateItem(final ListItem<Buit> item) {
				item.add(new Image("buitUserImage", new ImageResourceReference(new PropertyModel<User>(item.getModel(), "buitter"))));
			}
		});
		
		add(emptyBuitsContainer);
		add(notEmptyBuitsContainer);
	}
}
