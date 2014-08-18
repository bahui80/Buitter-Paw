package it.itba.edu.ar.web.buit;

import java.util.Date;

import it.itba.edu.ar.domain.buit.BuitRepo;
import it.itba.edu.ar.domain.buit.Hashtag;
import it.itba.edu.ar.web.DateFormatter;
import it.itba.edu.ar.web.base.BasePage;

import org.apache.wicket.datetime.markup.html.basic.DateLabel;
import org.apache.wicket.markup.html.basic.Label;
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
	}
}
