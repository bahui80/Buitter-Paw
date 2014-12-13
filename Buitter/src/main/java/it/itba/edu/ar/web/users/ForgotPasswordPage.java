package it.itba.edu.ar.web.users;


import it.itba.edu.ar.domain.user.User;
import it.itba.edu.ar.domain.user.UserRepo;
import it.itba.edu.ar.web.base.BasePage;
import it.itba.edu.ar.web.validator.SecretAnswerValidator;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.form.validation.EqualPasswordInputValidator;
import org.apache.wicket.markup.html.panel.ComponentFeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

public class ForgotPasswordPage extends BasePage {
	@SpringBean
	private UserRepo userRepo;
	
	private String username;
	private transient String password;
	private transient String password2;
	private transient String secretAnswer;
	private Form<ForgotPasswordPage> firstForm;
	private Form<ForgotPasswordPage> secondForm;
	private IModel<User> modelUser;
	
	public ForgotPasswordPage() {
		CompoundPropertyModel<ForgotPasswordPage> model = new CompoundPropertyModel<ForgotPasswordPage>(this);
		
		firstForm = new Form<ForgotPasswordPage>("firstForm", model);
		final TextField<String> secretAnswerTxtField = new TextField<String>("secretAnswer");
		secretAnswerTxtField.setRequired(true);
		final TextField<String> usernameTextField = new TextField<String>("username");
		usernameTextField.setRequired(true);
		firstForm.add(new ComponentFeedbackPanel("username_error", usernameTextField));
		firstForm.add(usernameTextField);
		firstForm.add(new Button("continue") {
			@Override
			public void onSubmit() {
				modelUser = new LoadableDetachableModel<User>() {
					@Override
					protected User load() {
						return userRepo.get(username);
					}
				};
				if(modelUser.getObject() == null) {
					usernameTextField.error(getString("InexistentUser"));
				} else {
					secondForm.add(new Label("usernameLabel", username));
					secondForm.add(new Label("question", new PropertyModel<String>(modelUser, "secretQuestion")));
					secretAnswerTxtField.add(new SecretAnswerValidator(new PropertyModel<String>(modelUser, "secretAnswer")));
				}
			}
		});
		
		
		secondForm = new Form<ForgotPasswordPage>("secondForm", model);
		PasswordTextField passwordTextField = new PasswordTextField("password");
		PasswordTextField passwordTextField2 = new PasswordTextField("password2");
		passwordTextField.setRequired(true);
		passwordTextField2.setRequired(true);
		secondForm.add(new EqualPasswordInputValidator(passwordTextField, passwordTextField2));
		secondForm.add(passwordTextField);
		secondForm.add(new ComponentFeedbackPanel("password_error", passwordTextField));
		secondForm.add(passwordTextField2);
		secondForm.add(new ComponentFeedbackPanel("password2_error", passwordTextField2));
		secondForm.add(secretAnswerTxtField);
		secondForm.add(new ComponentFeedbackPanel("secretAnswer_error", secretAnswerTxtField));
		secondForm.add(new Button("confirm") {
			@Override
			public void onSubmit() {
				modelUser.getObject().setPassword(password);
				modelUser.detach();
				setResponsePage(LoginPage.class);
			}
		});
		
		add(firstForm);
		add(secondForm);
	}
	
	@Override
	protected void onConfigure() {
		super.onConfigure();
		firstForm.setVisible(modelUser == null || modelUser.getObject() == null);
		secondForm.setVisible(modelUser != null && modelUser.getObject() != null);
	}
}
