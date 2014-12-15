package it.itba.edu.ar.web.buit;

import it.itba.edu.ar.domain.buit.Buit;
import it.itba.edu.ar.domain.buit.BuitRepo;
import it.itba.edu.ar.domain.buit.Hashtag;
import it.itba.edu.ar.web.BuitterSession;
import it.itba.edu.ar.web.DateFormatter;
import it.itba.edu.ar.web.ErrorPage;
import it.itba.edu.ar.web.ListBuitsPanel;
import it.itba.edu.ar.web.base.BasePage;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.wicket.datetime.markup.html.basic.DateLabel;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.ResourceModel;
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
		
		if(modelHashtag.getObject() == null) {
			redirectToInterceptPage(new ErrorPage(new ResourceModel("hashtagError"), new ResourceModel("hashtagErrorDescription")));
		}
		
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
		
	
		IModel<List<Buit>> modelBuit = new LoadableDetachableModel<List<Buit>>() {
			@Override
			protected List<Buit> load() {
				List<Buit> buits = new ArrayList<Buit>();
				modelHashtag.detach();
				for(Buit buit: modelHashtag.getObject().getBuits()) {
					if(buit.getBuitter().getPrivacy() == false || (BuitterSession.get().isSignedIn() && !buit.getBuitter().isBlacklisted(BuitterSession.get().getUser()))) {
						buits.add(buit);
					}
				}
				return buits;
			}
		};
		
		Panel listBuitsPanel = new ListBuitsPanel("listBuitsPanel", modelBuit) {
			public boolean isVisible() {
				return !modelHashtag.getObject().getBuits().isEmpty();
			}
		};
		add(listBuitsPanel);
		add(emptyBuitsContainer);
	}
}
