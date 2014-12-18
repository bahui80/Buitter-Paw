package it.itba.edu.ar.web;

import it.itba.edu.ar.web.base.BasePage;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.ResourceModel;

public class ErrorPage extends BasePage {
	
	public ErrorPage() {
		addToPage(new ResourceModel("404Error"), new ResourceModel("404ErrorDescription"));
	}
	
	public ErrorPage(ResourceModel error, ResourceModel errorDescription) {
		addToPage(error, errorDescription);
	}
	
	private void addToPage(ResourceModel error, ResourceModel errorDescription) {
		add(new Label("error", error));
		add(new Label("errorDescription", errorDescription));
	}
}
