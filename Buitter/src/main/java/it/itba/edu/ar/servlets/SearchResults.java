package it.itba.edu.ar.servlets;

import it.itba.edu.ar.model.User;
import it.itba.edu.ar.services.BuitServiceImpl;
import it.itba.edu.ar.services.UserServiceImpl;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class SearchResults extends HttpServlet {

	private UserServiceImpl userService;
	private BuitServiceImpl buitService;
	private SimpleDateFormat formatter;


	@Override
	public void init() throws ServletException {
		userService = UserServiceImpl.sharedInstance();
		buitService = BuitServiceImpl.sharedInstance();
		formatter =  new SimpleDateFormat("EEE, MMM d, HH:mm:ss");
	};

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		String query = req.getParameter("name");
		req.setAttribute("query", query);

		List<User> results = userService.search(query);
		for (User user : results) {
			user.setSimpleDateFormatter(formatter);
		}
		req.setAttribute("results", results);

		req.getRequestDispatcher("WEB-INF/jsp/searchresults.jsp").forward(req,
				resp);
		return;
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.getRequestDispatcher("WEB-INF/jsp/searchresults.jsp").forward(req,
				resp);
		return;
	}
}
