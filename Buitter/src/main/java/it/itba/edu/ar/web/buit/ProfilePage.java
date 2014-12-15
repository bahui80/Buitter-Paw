package it.itba.edu.ar.web.buit;

import it.itba.edu.ar.domain.buit.Buit;
import it.itba.edu.ar.domain.buit.BuitRepo;
import it.itba.edu.ar.domain.user.User;
import it.itba.edu.ar.domain.user.UserRepo;
import it.itba.edu.ar.web.BuitterSession;
import it.itba.edu.ar.web.ListBuitsPanel;
import it.itba.edu.ar.web.base.BasePage;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.validation.validator.StringValidator.MaximumLengthValidator;

public class ProfilePage extends BasePage {
	@SpringBean	
	private UserRepo userRepo;
	@SpringBean
	private BuitRepo buitRepo;
	private transient String buitText;
	private IModel<User> modelUser;
	
	public ProfilePage(final PageParameters pgParameters) {
		modelUser = new LoadableDetachableModel<User>() {
			@Override
			protected User load() {
				return userRepo.get(pgParameters.get("username").toString());
			}
		};
			
		setDefaultModel(modelUser);
		add(new HeaderPanel("headerPanel", modelUser));

		WebMarkupContainer buitContainer = new WebMarkupContainer("buitContainer") {
			public boolean isVisible() {
				return BuitterSession.get().isSignedIn() && BuitterSession.get().getUser().equals(getUser());
			}
		};
	
		
		Form<ProfilePage> buitForm = new Form<ProfilePage>("buitForm", new CompoundPropertyModel<ProfilePage>(this)) {
			@Override
			protected void onSubmit() {
				super.onSubmit();
				buitRepo.buit(buitText, BuitterSession.get().getUser());
				buitText = null;
			}
		};
		buitForm.add(new TextArea<String>("buitText").setRequired(true).add(new MaximumLengthValidator(140)));
		buitForm.add(new Button("buitButton", new ResourceModel("buitButton")));
		buitForm.add(new FeedbackPanel("feedback"));
		buitContainer.add(buitForm);
		
		WebMarkupContainer emptyBuitsContainer = new WebMarkupContainer("emptyBuitsContainer") {
			public boolean isVisible() {
				modelUser.detach();
				return getUser().getMybuits().isEmpty();
			}
		};
		
		emptyBuitsContainer.add(new Label("emptyBuits", new PropertyModel<String>(modelUser, "username")));
		
		WebMarkupContainer notEmptyBuitsContainer = new WebMarkupContainer("notEmptyBuitsContainer") {
			public boolean isVisible() {
				modelUser.detach();
				return !getUser().getMybuits().isEmpty();
			}
		};
		
		final IModel<List<Buit>> modelBuit = new LoadableDetachableModel<List<Buit>>() {
			
			@Override
			protected List<Buit> load() {
				return new ArrayList<Buit>(getUser().getMybuits());
			}
		};
			
		notEmptyBuitsContainer.add(new ListBuitsPanel("listBuitsPanel", modelBuit));
		
		getUser().addVisit();
		add(buitContainer);
		add(emptyBuitsContainer);
		add(notEmptyBuitsContainer);

	}
	
	private User getUser() {
		return (User) modelUser.getObject();
	}
}
