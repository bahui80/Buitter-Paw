package it.itba.edu.ar.web;

import it.itba.edu.ar.domain.user.User;

import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.resource.DynamicImageResource;
import org.apache.wicket.request.resource.IResource;
import org.apache.wicket.request.resource.ResourceReference;

public class ImageModel extends AbstractReadOnlyModel<ResourceReference> {

	private transient User user;

	public ImageModel(IModel<?> userModel) {
		super();
		this.user = (User) userModel.getObject();
	}
	
	public ImageModel(User user) {
		super();
		this.user = user;
	}

	@Override
	public ResourceReference getObject() {
		if (user.getPhoto() == null) {
			return BuitterApp.NO_IMAGE;
		} else {
			return new ResourceReference("image") {

				@Override
				public IResource getResource() {
					return new DynamicImageResource() {

						@Override
						protected byte[] getImageData(Attributes attributes) {
							return user.getPhoto();
						}
					};
				}
			};
		}
	}
}
