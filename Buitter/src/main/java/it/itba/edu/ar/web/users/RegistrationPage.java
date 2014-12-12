package it.itba.edu.ar.web.users;

import it.itba.edu.ar.domain.user.UserRepo;
import it.itba.edu.ar.web.HomePage;
import it.itba.edu.ar.web.base.BasePage;

import java.util.List;

import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.form.upload.FileUpload;
import org.apache.wicket.markup.html.panel.ComponentFeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.validation.validator.PatternValidator;
import org.apache.wicket.validation.validator.StringValidator;



public class RegistrationPage extends BasePage {
	@SpringBean
	private UserRepo userRepo;
	
	private transient String username;
	private transient String password;
	private transient String password2;
	private transient String name;
	private transient String surname;
	private transient String description;
	private transient String secretQuestion = "What is the name of your dog?";
	private transient String secretAnswer;
	private transient String privacy = "Public";
	private transient List<FileUpload> photo;
	
	public RegistrationPage() {
		Form<RegistrationPage> form = new Form<RegistrationPage>("form", new CompoundPropertyModel<RegistrationPage>(this));
		
		TextField<String> usernameTextField = new TextField<String>("username");
		usernameTextField.setRequired(true);
		usernameTextField.add(StringValidator.maximumLength(32));
		usernameTextField.add(new PatternValidator("[a-zA-Z0-9]+"));
		form.add(usernameTextField);
		form.add(new ComponentFeedbackPanel("username_error", usernameTextField));
		
		form.add(new UserInfoPanel("fieldsPanel"));
		form.add(new Button("cancel") {
			@Override
			public void onSubmit() {
				setResponsePage(HomePage.class);
				super.onSubmit();
			}
		});
		form.add(new Button("save") {
			@Override
			public void onSubmit() {
				super.onSubmit();
			}
		});
		add(form);
	}
}
