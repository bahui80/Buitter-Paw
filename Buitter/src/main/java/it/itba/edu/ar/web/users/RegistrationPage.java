package it.itba.edu.ar.web.users;

import it.itba.edu.ar.domain.user.DuplicatedUserException;
import it.itba.edu.ar.domain.user.User;
import it.itba.edu.ar.domain.user.UserRepo;
import it.itba.edu.ar.web.BuitterSession;
import it.itba.edu.ar.web.HomePage;
import it.itba.edu.ar.web.base.BasePage;
import it.itba.edu.ar.web.validator.EqualTextValidator;

import java.util.Date;
import java.util.List;

import org.apache.wicket.extensions.markup.html.captcha.CaptchaImageResource;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.form.upload.FileUpload;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.panel.ComponentFeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.validation.validator.PatternValidator;
import org.apache.wicket.validation.validator.StringValidator;

public class RegistrationPage extends BasePage {
	@SpringBean
	private UserRepo userRepo;
	private CaptchaImageResource captchaImage;
	private String captchaRandom = randomString(6, 8);
	
	private transient String username;
	private transient String password;
	@SuppressWarnings("unused")
	private transient String password2;
	private transient String name;
	private transient String surname;
	private transient String description;
	private transient String secretQuestion = "What is the name of your dog?";
	private transient String secretAnswer;
	private transient String privacy = "Public";
	private transient List<FileUpload> photo;
	private transient List<FileUpload> backgroundImage;
	@SuppressWarnings("unused")
	private transient String captchaText;

	public RegistrationPage() {
		CompoundPropertyModel<RegistrationPage> model = new CompoundPropertyModel<RegistrationPage>(
				this);
		Form<RegistrationPage> form = new Form<RegistrationPage>("form", model);

		final RequiredTextField<String> usernameTextField = new RequiredTextField<String>("username");
		usernameTextField.add(StringValidator.maximumLength(32));
		usernameTextField.add(new PatternValidator("[a-zA-Z0-9]+"));
		form.add(usernameTextField);
		form.add(new ComponentFeedbackPanel("username_error", usernameTextField));

		Panel panel = new UserInfoPanel("fieldsPanel");
		form.add(panel);
		form.add(((UserInfoPanel) panel).getEqualPasswordValidator());

		IModel<String> captchaModel = new PropertyModel<String>(this, "captchaRandom");
		captchaImage = new CaptchaImageResource(captchaModel);
		form.add(new Image("captchaImage", captchaImage));
		RequiredTextField<String> captchaTextField = new RequiredTextField<String>("captchaText");
		captchaTextField.add(new EqualTextValidator(captchaModel));
		form.add(captchaTextField);
		
		form.add(new ComponentFeedbackPanel("captcha_error", captchaTextField));
		form.add(new BookmarkablePageLink<Void>("cancel", HomePage.class));
		System.out.println(captchaRandom);
		form.add(new Button("save") {
			@Override
			public void onSubmit() {
				boolean b_privacy = false;
				byte[] b_photo = null;
				byte[] b_backgroundImage = null;
				if (privacy.equals("Private")) {
					b_privacy = true;
				}
				if (photo != null) {
					b_photo = photo.get(0).getBytes();
				}
				if(backgroundImage != null) {
					b_backgroundImage = backgroundImage.get(0).getBytes();
				}
				try {
					userRepo.add(new User(name, surname, username, password,
							description, secretQuestion, secretAnswer,
							new Date(), 0, b_privacy, b_photo, b_backgroundImage));
					BuitterSession.get().signIn(username, password, userRepo);
					setResponsePage(HomePage.class);
				} catch (DuplicatedUserException e) {
					usernameTextField.error(getString(e.getClass()
							.getSimpleName(),
							new Model<DuplicatedUserException>(e)));
				}

			}
		});
		add(form);
	}
	
	private int randomInt(int min, int max) {
		return (int) (Math.random() * (max - min) + min);
	}

	private String randomString(int min, int max) {
		int num = randomInt(min, max);
		byte b[] = new byte[num];
		for (int i = 0; i < num; i++)
			b[i] = (byte) randomInt('a', 'z');
		return new String(b);
	}
}
