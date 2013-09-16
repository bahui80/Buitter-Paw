package it.itba.edu.ar.servlets;

import it.itba.edu.ar.services.BuitService;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import it.itba.edu.ar.model.Hashtag;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class Home extends BuitsHttpServlet{
	
	private BuitService buitService;
	private long DAY_IN_MS = 1000 * 60 * 60 * 24;

	@Override
	public void init() throws ServletException {
		super.init();
		buitService = BuitService.sharedInstance();
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		
		
		Date tdate = new Date(System.currentTimeMillis() - (7 * DAY_IN_MS));
		
		List<Hashtag> trendingTopics = buitService.trendingTopics(tdate);
		
		req.setAttribute("trending", trendingTopics);
		
		req.getRequestDispatcher("index.jsp").forward(req,resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doPost(req, resp);
	}
	
}
