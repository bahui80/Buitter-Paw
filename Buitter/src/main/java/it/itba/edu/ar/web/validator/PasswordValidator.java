package it.itba.edu.ar.web.validator;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.form.validation.IFormValidator;
import org.apache.wicket.util.lang.Classes;
import org.apache.wicket.validation.ValidationError;

public class PasswordValidator implements IFormValidator {

//	@Override
//	public void validate(IValidatable<String> validatable) {
//		String value = validatable.getValue();
//		if(password != null) {
//			if(!password.equals(value)) {
//				ValidationError error = new ValidationError();
//				error.addMessageKey(resourceKey());
//				validatable.error(error);
//			}
//		}
//	}
	

	@Override
	public FormComponent<?>[] getDependentFormComponents() {
		return null;
	}

	@Override
	public void validate(Form<?> form) {
		String password = (String) form.get("password").getDefaultModelObject();
		String password2 = (String) form.get("password2").getDefaultModelObject();
		if(password != null) {
			if(!password.equals(password2)) {
				ValidationError error = new ValidationError();
				error.addMessageKey(resourceKey());
			}
		}
	}

	protected String resourceKey()	{
		return Classes.simpleName(PasswordValidator.class);
	}
}
