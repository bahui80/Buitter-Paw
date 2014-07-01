package it.itba.edu.ar.web.buit;

import it.itba.edu.ar.domain.user.User;
import it.itba.edu.ar.domain.user.UserRepo;
import it.itba.edu.ar.web.base.BasePage;

import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

public class ProfilePage extends BasePage {
	@SpringBean	
	private UserRepo users;
	
	
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
		
	}
}
