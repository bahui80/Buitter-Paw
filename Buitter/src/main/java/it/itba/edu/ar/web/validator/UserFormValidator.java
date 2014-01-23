package it.itba.edu.ar.web.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class UserFormValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return UserForm.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		UserForm userForm = (UserForm) target;
		checkUsername(userForm.getUsername(), errors);
		checkPassword(userForm.getPassword(), userForm.getPassword2(), errors);
		checkName(userForm.getName(), errors);
		checkSurname(userForm.getSurname(), errors);
		checkDescription(userForm.getDescription(), errors);
		checkAnswer(userForm.getAnswer(), errors);
		checkPhoto(userForm.getPhoto().getOriginalFilename(), errors);
	}
	
	private void checkUsername(String username, Errors errors) {
		if (!username.equals("")) {
			if (username.trim().length() == 0  || !username.matches("[a-zA-Z0-9]+")) {
				errors.rejectValue("username", "format");
			}
			if (username.length() > 32) {
				errors.rejectValue("username", "length");
			}
		} else {
			errors.rejectValue("username", "empty");
		}
	}
	
	private void checkPassword(String password, String password2, Errors errors) {
		if (!password.equals("")) {
			if (password.trim().length() == 0 || !password.matches("[a-zA-Z0-9]+")) {
				errors.rejectValue("password", "format");
			}
			if (password.length() > 32) {
				errors.rejectValue("password", "length");
			} else if (!password2.equals(password)) {
				errors.rejectValue("password2", "different");
			}
		} else {
			errors.rejectValue("password", "empty");
		}
	}
	
	private void checkName(String name, Errors errors) {
		if (!name.equals("")) {
			if (name.trim().length() == 0 || !name.matches("^[\\p{L} ]+$")) {
				errors.rejectValue("name", "format");
			}
			if (name.length() > 32) {
				errors.rejectValue("name", "length");
			}
		} else {
			errors.rejectValue("name", "empty");
		}
	}
	
	private void checkSurname(String surname, Errors errors) {
		if (!surname.equals("")) {
			if (surname.trim().length() == 0 || !surname.matches("^[\\p{L} ]+$")) {
				errors.rejectValue("surname", "format");
			}
			if (surname.length() > 32) {
				errors.rejectValue("surname", "length");
			}
		} else {
			errors.rejectValue("surname", "empty");
		}
	}
	
	private void checkDescription(String description, Errors errors) {
		if (!description.equals("")) {
			if (description.trim().length() == 0) {
				errors.rejectValue("description","format");
			}
			if (description.length() > 140) {
				errors.rejectValue("description", "length");
			}
		} else {
			errors.rejectValue("description", "empty");
		}
	}
	
	private void checkAnswer(String answer, Errors errors) {
		if (!answer.equals("")) {
			if (answer.trim().length() == 0) {
				errors.rejectValue("answer", "format");
			}
			if (answer.length() > 60) {
				errors.rejectValue("answer", "length");
			}
		} else {
			errors.rejectValue("answer", "empty");
		}
	}
	
	private void checkPhoto(String photoName, Errors errors) {
		if (!photoName.equals("")) {
			if (!(photoName.toLowerCase().contains("jpg") || photoName.toLowerCase().contains("jpeg")
					|| photoName.toLowerCase().contains("png"))) {
				errors.rejectValue("photo", "format");
			}
		}
	}
}
