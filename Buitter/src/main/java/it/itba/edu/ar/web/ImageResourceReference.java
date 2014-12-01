package it.itba.edu.ar.web;

import it.itba.edu.ar.domain.user.User;

import org.apache.wicket.model.IModel;
import org.apache.wicket.request.resource.DynamicImageResource;
import org.apache.wicket.request.resource.IResource;
import org.apache.wicket.request.resource.ResourceReference;
import org.apache.wicket.util.time.Time;

public class ImageResourceReference extends ResourceReference {

	private IModel<User> model;
		
	public ImageResourceReference(IModel<User> model) {
		super(ImageResourceReference.class, "images" + model.getObject().getId());
		this.model = model;
	}
	
	@Override
	public IResource getResource() {
		if(model.getObject().getPhoto() == null) {
			return BuitterApp.NO_IMAGE.getResource();
		}
		return new DynamicImageResource() {

			@Override
			protected byte[] getImageData(Attributes attributes) {
				return model.getObject().getPhoto();
			}
			
			@Override
			protected synchronized void setLastModifiedTime(Time time) {
				super.setLastModifiedTime(Time.now());
			}
		};
		
	}
}
