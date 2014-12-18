package it.itba.edu.ar.web;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.WebComponent;
import org.apache.wicket.model.IModel;

public class ExternalImage extends WebComponent {

	private IModel<String> modelUrl;
	
    public ExternalImage(String id, IModel<String> modelUrl) {
        super(id);
        this.modelUrl = modelUrl;
        add(AttributeModifier.replace("src", this.modelUrl.getObject()));
    }
    
    @Override
    protected void onComponentTag(ComponentTag tag) {
    	super.onComponentTag(tag);
    	checkComponentTag(tag, "img");
    }
}
