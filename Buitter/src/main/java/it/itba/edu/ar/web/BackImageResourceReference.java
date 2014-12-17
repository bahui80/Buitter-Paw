package it.itba.edu.ar.web;

import java.util.Random;

import org.apache.wicket.request.resource.IResource;
import org.apache.wicket.request.resource.ResourceReference;

public class BackImageResourceReference extends ResourceReference {
	private byte[] backgroundImage;
	
	public BackImageResourceReference(byte[] backgroundImage, String username) {
		// Tuve que meter un random al final porque me cacheaba las imagenes sino.
		super(BackImageResourceReference.class, "images" + username + new Random().nextInt(10000));
		this.backgroundImage = backgroundImage;
	}
	
	@Override
	public IResource getResource() {
		return new ImageResource(backgroundImage);
	}
}
