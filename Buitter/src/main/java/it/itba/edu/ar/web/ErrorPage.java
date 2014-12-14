package it.itba.edu.ar.web;

import it.itba.edu.ar.web.base.BasePage;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.ResourceModel;

public class ErrorPage extends BasePage {
	public ErrorPage(ResourceModel error, ResourceModel errorDescription) {
		if(error == null && errorDescription == null) {
			error = new ResourceModel("404Error");
			errorDescription = new ResourceModel("404ErrorDescription");
		}
		add(new Label("error", error));
		add(new Label("errorDescription", errorDescription));
	}
}
