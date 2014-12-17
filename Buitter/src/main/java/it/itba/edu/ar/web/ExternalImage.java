package it.itba.edu.ar.web;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.WebComponent;
import org.apache.wicket.model.Model;

public class ExternalImage extends WebComponent {

    public ExternalImage(String id, String imageUrl) {
        super(id);
        add(AttributeModifier.replace("src", new Model<String>(imageUrl)));
    }
    
    @Override
    protected void onComponentTag(ComponentTag tag) {
    	super.onComponentTag(tag);
    	checkComponentTag(tag, "img");
    }
}
