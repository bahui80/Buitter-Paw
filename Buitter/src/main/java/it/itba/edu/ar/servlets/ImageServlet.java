package it.itba.edu.ar.servlets;

import it.itba.edu.ar.model.User;
import it.itba.edu.ar.services.impl.UserServiceImpl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class ImageServlet extends HttpServlet {
	private UserServiceImpl userService;
	
	@Override
	public void init() throws ServletException {
		userService = UserServiceImpl.sharedInstance();
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
		throws ServletException, IOException {
		String profile = req.getParameter("name");
		
		if(profile != null && userService.checkUsername(profile)) {
			User usr = userService.getUserByUsername(profile);

			if (usr.getPhoto() != null && usr.getPhoto().length > 0) {
				resp.setContentType("image");
				resp.getOutputStream().write(usr.getPhoto());
				resp.getOutputStream().flush();
				resp.getOutputStream().close();
				return;
			}
		}
	}
}


