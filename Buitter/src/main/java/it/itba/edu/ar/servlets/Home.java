package it.itba.edu.ar.servlets;

import it.itba.edu.ar.model.Hashtag;
import it.itba.edu.ar.model.User;
import it.itba.edu.ar.services.BuitServiceImpl;
import it.itba.edu.ar.services.UserServiceImpl;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class Home extends BuitsHttpServlet {

	private BuitServiceImpl buitService;
	private UserServiceImpl userService;
	private long DAY_IN_MS = 1000 * 60 * 60 * 24;
	private static final int trendingQuantity  = 10;

	@Override
	public void init() throws ServletException {
		super.init();
		buitService = BuitServiceImpl.sharedInstance();
		userService = UserServiceImpl.sharedInstance();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		int default_time = 7;
		
		String time = req.getParameter("time");
		if (time != null) {
			try {
				int aux = Integer.parseInt(time);
				if(aux!= 7 && aux != 30 && aux != 1){
					req.getRequestDispatcher("error.jsp").forward(req, resp);
					return;
				}
				default_time = aux;
			} catch (NumberFormatException e) {

			}
		}

		Date tdate = new Date(System.currentTimeMillis()
				- (default_time * DAY_IN_MS));
		List<Hashtag> trendingTopics = buitService.trendingTopics(tdate, trendingQuantity);

		req.setAttribute("trending", trendingTopics);

		List<User> userList = userService.search("");
		if (userList.size() > 5) {
			userList = userList.subList(0, 5);
		}
		req.setAttribute("userlist", userList);
		req.setAttribute("selectedTime", default_time);

		req.getRequestDispatcher("index.jsp").forward(req, resp);
		return;
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		this.doGet(req, resp);
		return;
	}

}
