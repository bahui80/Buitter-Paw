package it.itba.edu.ar.web;

import it.itba.edu.ar.domain.user.User;

import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.resource.DynamicImageResource;
import org.apache.wicket.request.resource.IResource;
import org.apache.wicket.request.resource.ResourceReference;

public class ImageModel extends AbstractReadOnlyModel<ResourceReference> {

	private IModel<User> userModel;

	public ImageModel(IModel<User> userModel) {
		super();
		this.userModel = userModel;
	}

	@Override
	public ResourceReference getObject() {
		if (userModel.getObject().getPhoto() == null) {
			return BuitterApp.NO_IMAGE;
		} else {
			return new ResourceReference("image") {

				@Override
				public IResource getResource() {
					return new DynamicImageResource() {

						@Override
						protected byte[] getImageData(Attributes attributes) {
							return userModel.getObject().getPhoto();
						}
					};
				}
			};
		}
	}
}
