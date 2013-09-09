package it.itba.edu.ar.servlets;

import it.itba.edu.ar.dao.UserDao;
import it.itba.edu.ar.dao.UserManager;
import it.itba.edu.ar.model.User;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class Login extends HttpServlet{

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		
		UserDao userDao = UserManager.sharedInstance();
		User user = userDao.login(new User(username,password));
		
		if(user != null){
			req.getSession().setAttribute("user", user);
			req.getRequestDispatcher("/WEB-INF/jsp/index.jsp").forward(req, resp);
		}else{
			req.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(req, resp);
		}
	}
}
