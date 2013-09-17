package it.itba.edu.ar.servlets;

import it.itba.edu.ar.model.Hashtag;
import it.itba.edu.ar.model.User;
import it.itba.edu.ar.services.BuitService;
import it.itba.edu.ar.services.UserService;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class Home extends BuitsHttpServlet {

	private BuitService buitService;
	private UserService userService;
	private long DAY_IN_MS = 1000 * 60 * 60 * 24;

	@Override
	public void init() throws ServletException {
		super.init();
		buitService = BuitService.sharedInstance();
		userService = UserService.sharedInstance();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		int default_time = 7;

		String time = req.getParameter("time");
		if (time != null) {
			try {
				default_time = Integer.parseInt(time);
			} catch (NumberFormatException e) {

			}
		}

		Date tdate = new Date(System.currentTimeMillis()
				- (default_time * DAY_IN_MS));
		List<Hashtag> trendingTopics = buitService.trendingTopics(tdate);

		req.setAttribute("trending", trendingTopics);

		List<User> userList = userService.search("");
		if (userList.size() > 5) {
			userList = userList.subList(0, 5);
		}
		req.setAttribute("userlist", userList);

		req.getRequestDispatcher("index.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		req.getRequestDispatcher("index.jsp").forward(req, resp);
	}

}
