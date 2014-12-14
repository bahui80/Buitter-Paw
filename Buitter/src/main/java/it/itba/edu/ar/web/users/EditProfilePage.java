package it.itba.edu.ar.web.users;

import it.itba.edu.ar.domain.user.User;
import it.itba.edu.ar.web.HomePage;
import it.itba.edu.ar.web.base.BasePage;

import java.util.List;

import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.upload.FileUpload;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;

public class EditProfilePage extends BasePage {
	private transient String password;
	@SuppressWarnings("unused")
	private transient String password2;
	private transient String name;
	private transient String surname;
	private transient String description;
	private transient String secretQuestion;
	private transient String secretAnswer;
	private transient String privacy;
	private transient List<FileUpload> photo;
	
	public EditProfilePage(final IModel<User> modelUser) {
		Form<EditProfilePage> form = new Form<EditProfilePage>("form", new CompoundPropertyModel<EditProfilePage>(this));
		
		password = modelUser.getObject().getPassword();
		password2 = modelUser.getObject().getPassword();
		name = modelUser.getObject().getName();
		surname = modelUser.getObject().getSurname();
		description = modelUser.getObject().getDescription();
		secretQuestion = modelUser.getObject().getSecretQuestion();
		secretAnswer = modelUser.getObject().getSecretAnswer();
		if(modelUser.getObject().getPrivacy() == true) {
			privacy = "Private";
		} else {
			privacy = "Public";
		}
		
		Panel panel = new UserInfoPanel("fieldsPanel");		
		form.add(panel);
		form.add(((UserInfoPanel) panel).getEqualPasswordValidator());
		
		form.add(new Button("save") {
			@Override
			public void onSubmit() {
				modelUser.getObject().setPassword(password);
				modelUser.getObject().setName(name);
				modelUser.getObject().setSurname(surname);
				modelUser.getObject().setDescription(description);
				modelUser.getObject().setSecretQuestion(secretQuestion);
				modelUser.getObject().setSecretAnswer(secretAnswer);
				if(privacy.equals("Private")) {
					modelUser.getObject().setPrivacy(true);
				} else {
					modelUser.getObject().setPrivacy(false);
				}
				if(photo != null) {
					modelUser.getObject().setPhoto(photo.get(0).getBytes());
				}
				System.out.println(privacy);
				modelUser.detach();
				setResponsePage(HomePage.class);
			}
		});
		form.add(new BookmarkablePageLink<Void>("cancel", HomePage.class));
		add(form);
	}
}
