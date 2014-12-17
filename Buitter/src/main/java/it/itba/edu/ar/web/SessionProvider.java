package it.itba.edu.ar.web;

import it.itba.edu.ar.domain.user.UserRepo;

import org.apache.wicket.injection.Injector;
import org.apache.wicket.protocol.http.WebSession;
import org.apache.wicket.request.Request;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.springframework.util.Assert;

public class SessionProvider {
	@SpringBean
	private UserRepo userRepo;
	private CookieService cookieService;
	public static final String REMEMBER_ME_LOGIN_COOKIE = "username";
	public static final String REMEMBER_ME_PASSWORD_COOKIE = "password";
	public static final int REMEMBER_ME_DURATION_IN_DAYS = 7;
	
    public SessionProvider(CookieService cookieService) {
        this.cookieService = cookieService;
        Injector.get().inject(this);
        Assert.state(userRepo != null, "Can't inject users!");
    }

    public WebSession createNewSession(Request request) {
        BuitterSession session = new BuitterSession(request);
        
        String usernameCookie = cookieService.loadCookie(REMEMBER_ME_LOGIN_COOKIE);
        String passwordCookie = cookieService.loadCookie(REMEMBER_ME_PASSWORD_COOKIE);
             
        if(usernameCookie != null && passwordCookie != null) {
        	session.signIn(usernameCookie, passwordCookie, userRepo);     	
        }

        return session;
    }
}