package it.itba.edu.ar.web;

import it.itba.edu.ar.domain.EntityModel;
import it.itba.edu.ar.domain.user.User;
import it.itba.edu.ar.domain.user.UserRepo;

import org.apache.wicket.Session;
import org.apache.wicket.model.IModel;
import org.apache.wicket.protocol.http.WebSession;
import org.apache.wicket.request.Request;

public class BuitterSession extends WebSession {
	
	private IModel<User> userModel = new EntityModel<User>(User.class);

	public static BuitterSession get() {
		return (BuitterSession) Session.get();
	}

	public BuitterSession(Request request) {
		super(request);
	}

	public User getUser() {
		return userModel.getObject();
	}

	public boolean signIn(String username, String password, UserRepo users) {
		User user = users.get(username);
		if(user != null && user.checkPassword(password)) {
			this.userModel.setObject(user);
			return true;
		}
		return false;
	}

	public boolean isSignedIn() {
		return userModel.getObject() != null;
	}

	public void signOut() {
        invalidate();
        clear();
	}
	
	@Override
	public void detach() {
		super.detach();
		userModel.detach();
	}
}
