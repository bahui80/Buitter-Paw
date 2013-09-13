package it.itba.edu.ar.servlets;

import it.itba.edu.ar.services.UserService;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class ForgotPassword extends HttpServlet {
	
	private UserService userService;
	
	@Override
	public void init() throws ServletException {
		userService = UserService.sharedInstance();
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.getRequestDispatcher("WEB-INF/jsp/forgotpassword.jsp").forward(req,
				resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String username = req.getParameter("username");
		String state = req.getParameter("recover");

		if (state.equals("recover")) {
			if (!username.equals("")) {
				if (userService.checkUsername(username)) {
					req.setAttribute("correct_username", username);
					req.setAttribute("question", "What's the name of your dog?");
			//		req.setAttribute("question", UserService.getQuestion(username));
				} else {
					req.setAttribute("error_username", "That user doesn't exist");
				}
			} else {
				req.setAttribute("error_username", "You must enter an user");
			}
			req.getRequestDispatcher("WEB-INF/jsp/forgotpassword.jsp").forward(req, resp);
		} else {
			
		}
	}
}
