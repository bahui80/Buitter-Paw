package it.itba.edu.ar.servlets;

import it.itba.edu.ar.model.User;
import it.itba.edu.ar.services.UserService;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class Login extends HttpServlet{

	private UserService userService;
	
	@Override
	public void init() throws ServletException {
		userService = UserService.sharedInstance();
	};
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.getRequestDispatcher("WEB-INF/jsp/login.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		
		User user = userService.login(new User(username,password));
		
		if(user != null){
			req.getSession().setAttribute("user", username);
			resp.sendRedirect("/Buitter/home");
		} else {
			req.setAttribute("user_username", username);
			req.setAttribute("error_login", "Username or password incorrect");
			req.getRequestDispatcher("WEB-INF/jsp/login.jsp").forward(req, resp);
		}
	}
}
