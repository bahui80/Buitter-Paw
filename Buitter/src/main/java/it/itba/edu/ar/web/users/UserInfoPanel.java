package it.itba.edu.ar.web.users;

import it.itba.edu.ar.web.validator.FileExtensionValidator;

import java.util.Arrays;
import java.util.List;

import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.upload.FileUploadField;
import org.apache.wicket.markup.html.form.validation.EqualPasswordInputValidator;
import org.apache.wicket.markup.html.panel.ComponentFeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.validation.validator.PatternValidator;
import org.apache.wicket.validation.validator.StringValidator;

public class UserInfoPanel extends Panel {
	
	private EqualPasswordInputValidator equalPasswordValidator;
	
	public UserInfoPanel(String id) {
		super(id);
		
		PasswordTextField passwordTextField = new PasswordTextField("password");
		passwordTextField.setResetPassword(false);
//		passwordTextField.setRequired(true);
		passwordTextField.add(StringValidator.maximumLength(32));
		passwordTextField.add(new PatternValidator("[a-zA-Z0-9]+"));
		add(passwordTextField);
		add(new ComponentFeedbackPanel("password_error", get("password")));

		PasswordTextField passwordTextField2 = new PasswordTextField("password2");
		passwordTextField2.setResetPassword(false);
//		passwordTextField2.setRequired(true);
		passwordTextField2.add(StringValidator.maximumLength(32));
		passwordTextField2.add(new PatternValidator("[a-zA-Z0-9]+"));
		equalPasswordValidator = new EqualPasswordInputValidator(passwordTextField, passwordTextField2);
		add(passwordTextField2);
		add(new ComponentFeedbackPanel("password2_error", get("password2")));
		
		add(new EqualPasswordInputValidator(passwordTextField, passwordTextField2));
		RequiredTextField<String> nameTextField = new RequiredTextField<String>("name");
		nameTextField.add(StringValidator.maximumLength(32));
		nameTextField.add(new PatternValidator("^[\\p{L} ]+$"));
		add(nameTextField);
		add(new ComponentFeedbackPanel("name_error", get("name")));		
		
		RequiredTextField<String> surnameTextField = new RequiredTextField<String>("surname");
		surnameTextField.add(StringValidator.maximumLength(32));
		surnameTextField.add(new PatternValidator("^[\\p{L} ]+$"));
		add(surnameTextField);
		add(new ComponentFeedbackPanel("surname_error", get("surname")));
		
		
		TextArea<String> descriptionTextArea = new TextArea<String>("description");
		descriptionTextArea.setRequired(true);
		descriptionTextArea.add(StringValidator.maximumLength(140));
		add(descriptionTextArea);
		add(new ComponentFeedbackPanel("description_error", get("description")));
		
		List<String> questions = Arrays.asList(new String[] {"What is the name of your dog?", "Who was your favourite teacher?", "Where do you live?", "Do you hate Twitter?", "What is your aunts name?"});
		add(new DropDownChoice<String>("secretQuestion", questions));
		
		RequiredTextField<String> secretAnswerTextField = new RequiredTextField<String>("secretAnswer");
		secretAnswerTextField.add(StringValidator.maximumLength(60));
		add(secretAnswerTextField);
		add(new ComponentFeedbackPanel("secretAnswer_error", get("secretAnswer")));
		
		add(new DropDownChoice<String>("privacy", Arrays.asList(new String[] {"Public", "Private"})));
		
		add(new FileUploadField("photo").add(new FileExtensionValidator()));
		add(new ComponentFeedbackPanel("photo_error", get("photo")));
		
		add(new FileUploadField("backgroundImage").add(new FileExtensionValidator()));
		add(new ComponentFeedbackPanel("background_error", get("backgroundImage")));

	}
	
	public EqualPasswordInputValidator getEqualPasswordValidator() {
		return equalPasswordValidator;
	}
}
