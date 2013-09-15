package it.itba.edu.ar.servlets;

import it.itba.edu.ar.model.User;
import it.itba.edu.ar.services.UserService;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class ForgotPassword extends HttpServlet {
	
	private UserService userService;
	private Boolean error = false;
	
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
		String answer = req.getParameter("answer");
		String password = req.getParameter("password");
		String password2 = req.getParameter("password2");
	
		if (state.equals("recover")) {
			if (!username.equals("")) {
				if (userService.checkUsername(username)) {
					req.setAttribute("correct_username", username);
					req.setAttribute("question", userService.getUserByUsername(username).getSecretQuestion());
				} else {
					req.setAttribute("user_username", username);
					req.setAttribute("error_username", "That user doesn't exist");
				}
			} else {
				req.setAttribute("error_username", "You must enter an user");
			}
			req.getRequestDispatcher("WEB-INF/jsp/forgotpassword.jsp").forward(req, resp);
		} else {
			req.setAttribute("correct_username", username);
			req.setAttribute("question", userService.getUserByUsername(username).getSecretQuestion());
			req.setAttribute("user_answer", answer);
			if(!userService.getUserByUsername(username).getSecretAnswer().equals(answer)) {
				error = true;
				req.setAttribute("error_answer", "Incorrect secret answer");
			}
			checkPassword(password, password2, req);
			if(error) {
				error = false;
				req.getRequestDispatcher("WEB-INF/jsp/forgotpassword.jsp").forward(req, resp);
			} else {
				User user = userService.getUserByUsername(username);
				user.setPassword(password);
				userService.updateUser(user);
				req.getRequestDispatcher("WEB-INF/jsp/login.jsp").forward(req, resp);
			}
		}
	}
	
	private void checkPassword(String password, String password2, HttpServletRequest request) {
		if(!password.equals("")) {
			if(password.length() > 32) {
				request.setAttribute("error_password", "The password must contain up to 32 characters");
				error = true;
			} else if(!password2.equals(password)) {
				request.setAttribute("error_password2", "The passwords must match");
				error = true;
			}
			request.setAttribute("user_password2", password2);
			request.setAttribute("user_password", password);
		} else {
			request.setAttribute("error_password", "You must insert a new password");
			error = true;
		}
	}
}
