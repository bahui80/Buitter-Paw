package it.itba.edu.ar.web.buit;

import it.itba.edu.ar.domain.user.User;
import it.itba.edu.ar.domain.user.UserRepo;
import it.itba.edu.ar.web.BuitterSession;
import it.itba.edu.ar.web.base.BasePage;

import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.validation.validator.StringValidator.MaximumLengthValidator;

public class ProfilePage extends BasePage {
	@SpringBean	
	private UserRepo users;
	private transient String buitText;
	
	public ProfilePage(final PageParameters pgParameters) {
		IModel<User> modelUser = new LoadableDetachableModel<User>() {
			@Override
			protected User load() {
				return users.get(pgParameters.get("username").toString());
			}
		};
		User user = modelUser.getObject();
		if(user == null) {
			//mostrar pagina de error (ese usuario no existe)
		}
		add(new HeaderPanel("headerPanel", modelUser));
		
		Form<ProfilePage> buitForm =new Form<ProfilePage>("buitForm", new CompoundPropertyModel<ProfilePage>(this)) {
			@Override
			public boolean isVisible() {
				return BuitterSession.get().isSignedIn();
			}
			
			@Override
			protected void onSubmit() {
				//TODO: GUARDAR A BASE EL BUIT
				System.out.println(buitText);
			}
		};
		buitForm.add(new TextArea<String>("buitText").setRequired(true).add(new MaximumLengthValidator(140)));
		buitForm.add(new Button("buitButton", new ResourceModel("buitButton")));
		buitForm.add(new FeedbackPanel("feedback"));
		add(buitForm);
		
		user.addVisit();
		
	}
}
