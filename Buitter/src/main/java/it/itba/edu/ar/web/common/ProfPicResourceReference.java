package it.itba.edu.ar.web.common;

import it.itba.edu.ar.web.BuitterApp;

import java.util.Random;

import org.apache.wicket.request.resource.IResource;
import org.apache.wicket.request.resource.ResourceReference;

public class ProfPicResourceReference extends ResourceReference {

	private byte[] photo;
		
	public ProfPicResourceReference(byte[] photo, String username) {
		// Tuve que meter un random al final porque me cacheaba las imagenes sino.
		super(ProfPicResourceReference.class, "images" + username + new Random().nextInt(10000));
		this.photo = photo;
	}
	
	@Override
	public IResource getResource() {
		if(photo == null) {
			return BuitterApp.NO_IMAGE.getResource();
		}
		return new ImageResource(photo);
	}
}
