package it.itba.edu.ar.web.validator;

import java.util.Date;

import org.apache.wicket.model.IModel;
import org.apache.wicket.util.lang.Classes;
import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.IValidator;
import org.apache.wicket.validation.ValidationError;

public class DateRangeValidator implements IValidator<Date> {
	private IModel<Date> dateModel;
	
	public DateRangeValidator(IModel<Date> dateModel) {
		this.dateModel = dateModel;
	}

	@Override
	public void validate(IValidatable<Date> validatable) {
		Date value = validatable.getValue();
		if(dateModel.getObject() != null) {
			if(dateModel.getObject().compareTo(value) < 0) {
				ValidationError error = new ValidationError();
				error.addMessageKey(resourceKey());
				validatable.error(error);
			}
		}
	}
	
	protected String resourceKey()	{
		return Classes.simpleName(DateRangeValidator.class);
	}
}
