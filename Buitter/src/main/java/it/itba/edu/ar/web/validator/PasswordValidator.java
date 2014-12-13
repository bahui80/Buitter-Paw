package it.itba.edu.ar.web.validator;

import org.apache.wicket.util.lang.Classes;
import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.IValidator;
import org.apache.wicket.validation.ValidationError;

public class PasswordValidator implements IValidator<String> {
	private String password;
	
	public PasswordValidator(String password) {
		this.password = password;
	}
		
	@Override
	public void validate(IValidatable<String> validatable) {
		String value = validatable.getValue();
		if(password != null) {
			if(!password.equals(value)) {
				ValidationError error = new ValidationError();
				error.addMessageKey(resourceKey());
				validatable.error(error);
			}
		}
	}
	
	protected String resourceKey()	{
		return Classes.simpleName(PasswordValidator.class);
	}

}
