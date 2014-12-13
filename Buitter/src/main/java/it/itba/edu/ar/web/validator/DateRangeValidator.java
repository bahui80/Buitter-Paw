package it.itba.edu.ar.web.validator;

import java.util.Date;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.form.validation.IFormValidator;
import org.apache.wicket.util.lang.Classes;
import org.apache.wicket.validation.ValidationError;

public class DateRangeValidator implements IFormValidator {
	private final FormComponent<?>[] components;
	
	public DateRangeValidator(FormComponent<?> formComponent1, FormComponent<?> formComponent2) {
		if (formComponent1 == null) {
			throw new IllegalArgumentException("argument formComponent1 cannot be null");
		}
		if (formComponent2 == null) {
			throw new IllegalArgumentException("argument formComponent2 cannot be null");
		}
		components = new FormComponent[] { formComponent1, formComponent2 };
	}

	@Override
	public FormComponent<?>[] getDependentFormComponents() {
		return components;
	}

	@Override
	public void validate(Form<?> form) {
		final FormComponent<?> formComponent1 = components[0];
		final FormComponent<?> formComponent2 = components[1];
		
		Date fromDate = (Date) formComponent1.getConvertedInput();
		Date toDate = (Date) formComponent2.getConvertedInput();
		
		if (fromDate.compareTo(toDate) > 0) {
			ValidationError error = new ValidationError();
			error.addMessageKey(resourceKey());
			formComponent2.error(error);
		}
		
	}
	
	protected String resourceKey()	{
		return Classes.simpleName(DateRangeValidator.class);
	}
}
