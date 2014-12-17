package it.itba.edu.ar.web;

import org.apache.wicket.markup.html.image.Image;

public class ImageVerified extends Image {
	private int qty;
	
	public ImageVerified(String id, Integer qty) {
		super(id, BuitterApp.VERIFIED_IMAGE);
		this.qty = qty;
	}
	
	@Override
	public boolean isVisible() {
		return qty >= Integer.parseInt(getString("verifiedFollowersAmount"));
	}

}
