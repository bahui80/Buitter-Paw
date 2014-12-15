package it.itba.edu.ar.web.validator;

import org.apache.wicket.model.IModel;
import org.apache.wicket.util.lang.Classes;
import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.IValidator;
import org.apache.wicket.validation.ValidationError;

public class EqualTextValidator implements IValidator<String> {
	private IModel<String> model;
	
	public EqualTextValidator(IModel<String> model) {
		this.model = model;
	}
	
	@Override
	public void validate(IValidatable<String> validatable) {
		String value = validatable.getValue();
		if(value != null) {
			if(!value.equals(model.getObject())) {
				ValidationError error = new ValidationError();
				error.addMessageKey(resourceKey());
				validatable.error(error);
			}
		}
	}
	
	protected String resourceKey()	{
		return Classes.simpleName(EqualTextValidator.class);
	}

}
