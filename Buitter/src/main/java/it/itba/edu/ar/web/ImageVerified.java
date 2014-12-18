package it.itba.edu.ar.web;

import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.model.IModel;

public class ImageVerified extends Image {
	private IModel<Integer> modelQty;
	
	public ImageVerified(String id, IModel<Integer> modelQty) {
		super(id, BuitterApp.VERIFIED_IMAGE);
		this.modelQty = modelQty;
	}
	
	@Override
	public boolean isVisible() {
		return modelQty.getObject() >= Integer.parseInt(getString("verifiedFollowersAmount"));
	}

}
