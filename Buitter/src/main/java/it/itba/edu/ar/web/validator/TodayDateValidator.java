package it.itba.edu.ar.web.validator;

import java.util.Date;

import org.apache.wicket.util.lang.Classes;
import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.IValidator;
import org.apache.wicket.validation.ValidationError;

public class TodayDateValidator implements IValidator<Date> {

	@Override
	public void validate(IValidatable<Date> validatable) {
		Date value = validatable.getValue();
		if(value != null) {
			if(value.compareTo(new Date()) > 0) {
				ValidationError error = new ValidationError();
				error.addMessageKey(resourceKey());
				validatable.error(error);
			}
		}
	}

	protected String resourceKey()	{
		return Classes.simpleName(TodayDateValidator.class);
	}
}
