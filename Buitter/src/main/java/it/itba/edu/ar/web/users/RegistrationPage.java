package it.itba.edu.ar.web.users;

import it.itba.edu.ar.domain.user.DuplicatedUserException;
import it.itba.edu.ar.domain.user.User;
import it.itba.edu.ar.domain.user.UserRepo;
import it.itba.edu.ar.web.BuitterSession;
import it.itba.edu.ar.web.HomePage;
import it.itba.edu.ar.web.base.BasePage;

import java.util.Date;
import java.util.List;

import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.form.upload.FileUpload;
import org.apache.wicket.markup.html.panel.ComponentFeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.Model;
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
		CompoundPropertyModel<RegistrationPage> model = new CompoundPropertyModel<RegistrationPage>(this);
		Form<RegistrationPage> form = new Form<RegistrationPage>("form", model);
		
		final TextField<String> usernameTextField = new TextField<String>("username");
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
			}
		});
		form.add(new Button("save") {
			@Override
			public void onSubmit() {
				boolean b_privacy = false;
				byte[] b_photo = null;
				if(privacy.equals("Private")) {
					b_privacy = true;
				}
				if(photo != null) {
					b_photo = photo.get(0).getBytes();
				}
				try {
					userRepo.add(new User(name, surname, username, password, description, secretQuestion, secretAnswer, new Date(), 0, b_privacy, b_photo));
					BuitterSession.get().signIn(username, password, userRepo);
					setResponsePage(HomePage.class);
				} catch(DuplicatedUserException e) {
					usernameTextField.error(getString(e.getClass().getSimpleName(), new Model<DuplicatedUserException>(e)));
				}
				
			}
		});
		add(form);
	}
}
