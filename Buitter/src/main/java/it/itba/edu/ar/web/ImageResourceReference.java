package it.itba.edu.ar.web;

import it.itba.edu.ar.domain.user.User;

import java.util.Random;

import org.apache.wicket.model.IModel;
import org.apache.wicket.request.resource.DynamicImageResource;
import org.apache.wicket.request.resource.IResource;
import org.apache.wicket.request.resource.ResourceReference;

public class ImageResourceReference extends ResourceReference {

	private IModel<User> model;
		
	public ImageResourceReference(IModel<User> model) {
		super(ImageResourceReference.class, "images" + model.getObject().getId() + new Random().nextInt(100000));
		this.model = model;
	}
	
	@Override
	public IResource getResource() {
		model.detach();
		if(model.getObject().getPhoto() == null) {
			model.detach();
			return BuitterApp.NO_IMAGE.getResource();
		}
		return new DynamicImageResource() {

			@Override
			protected byte[] getImageData(Attributes attributes) {
				model.detach();
				return model.getObject().getPhoto();
			}
		};
		
	}
}
