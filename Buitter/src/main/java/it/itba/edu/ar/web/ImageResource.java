package it.itba.edu.ar.web;

import org.apache.wicket.request.resource.DynamicImageResource;

public class ImageResource extends DynamicImageResource {
	private byte[] image;
	
 	public ImageResource(byte[] image) {
		this.image = image;
	}

	@Override
	protected byte[] getImageData(Attributes attributes) {
		return image;
	}
	
	@Override
    public boolean equals(Object that) {
		return that instanceof ImageResource;
     }
}