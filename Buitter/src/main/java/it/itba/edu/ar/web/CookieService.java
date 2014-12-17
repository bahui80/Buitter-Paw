package it.itba.edu.ar.web;

import org.apache.wicket.util.cookies.CookieDefaults;
import org.apache.wicket.util.cookies.CookieUtils;

public class CookieService extends CookieUtils {
	private static final int EXPIRATION_COOKIE_DURATION_IN_SECONDS = 604800;
	
	public CookieService() {
		CookieDefaults options = new CookieDefaults();
		options.setMaxAge(EXPIRATION_COOKIE_DURATION_IN_SECONDS);
		new CookieService(options);
	}
	
	private CookieService(CookieDefaults options) {
		super(options);
	}
	
    public String loadCookie(String key) {
        String value;
        
    	if (key == null) {
            return null;
        }
 
    	value = super.load(key);

        return value;
    }
 
    public void saveCookie(String cookieName, String cookieValue) {
    	
    	if(cookieName != null && cookieValue != null) {
    		
    		super.save(cookieName, cookieValue);
    	}
    }
 
    public void removeCookieIfPresent(String cookieName) {
        String cookie = loadCookie(cookieName);
        
        if(cookie != null) {
        	super.remove(cookieName);
        }
    }
}