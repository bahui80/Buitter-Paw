package it.itba.edu.ar.web.common;

import it.itba.edu.ar.domain.buit.Buit;

import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;

public class MessageModel extends AbstractReadOnlyModel<String> {
	
	private IModel<Buit> buitModel;
	
	public MessageModel(IModel<Buit> buitModel) {
		super();
		this.buitModel = buitModel;
	}
	
	@Override
	public String getObject() {
		String message = buitModel.getObject().getMessage();
		message = ViewControllerHelper.prepareBuitHashtag(message, buitModel);
		message = ViewControllerHelper.prepareBuitUrl(message, buitModel);
		message = ViewControllerHelper.prepareBuitUser(message, buitModel);
	
		return message;
	}
}
