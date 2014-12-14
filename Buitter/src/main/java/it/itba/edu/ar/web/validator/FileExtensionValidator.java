package it.itba.edu.ar.web.validator;

import java.util.Arrays;
import java.util.List;

import org.apache.wicket.markup.html.form.upload.FileUpload;
import org.apache.wicket.util.lang.Classes;
import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.IValidator;
import org.apache.wicket.validation.ValidationError;

public class FileExtensionValidator implements IValidator<List<FileUpload>> {
	private final List<String> allowedFileTypes = Arrays.asList("JPG", "JPEG",
			"PNG");

	@Override
	public void validate(IValidatable<List<FileUpload>> validatable) {
		List<FileUpload> value = validatable.getValue();
		if (value != null) {
			FileUpload file = value.get(0);
			String fileName = file.getClientFileName();
			String fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1);
			if (!allowedFileTypes.contains(fileExtension.toUpperCase())) {
				ValidationError error = new ValidationError();
				error.addMessageKey(resourceKey());
				validatable.error(error);
			}
		}
	}

	protected String resourceKey() {
		return Classes.simpleName(FileExtensionValidator.class);
	}

}
