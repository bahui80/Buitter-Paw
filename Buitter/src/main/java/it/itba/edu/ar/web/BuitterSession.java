package it.itba.edu.ar.web;

import it.itba.edu.ar.domain.user.User;
import it.itba.edu.ar.domain.user.UserRepo;

import org.apache.wicket.Session;
import org.apache.wicket.protocol.http.WebSession;
import org.apache.wicket.request.Request;

public class BuitterSession extends WebSession {

	private String username;

	public static BuitterSession get() {
		return (BuitterSession) Session.get();
	}

	public BuitterSession(Request request) {
		super(request);
	}

	public String getUsername() {
		return username;
	}

	/*
	 * TODO Tengo que crear el converter para que reciba directamente un user. Si todo esta bien, es decir,
	 * el usuario esta bien logueado, tengo que guardarme el username por cualquier cosa ( a lo mejor me conviene
	 * guardarme el usuario entero).
	 */
	public boolean signIn(String username, String password, UserRepo users) {
		User user = users.get(username);
		boolean valid = users.login(user, password);
		if(valid == true) {
			
		}
		return valid;
	}

	public boolean isSignedIn() {
		return username != null;
	}

	public void signOut() {
        invalidate();
        clear();
	}
}
