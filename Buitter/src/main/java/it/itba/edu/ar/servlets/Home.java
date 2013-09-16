package it.itba.edu.ar.servlets;

import it.itba.edu.ar.services.BuitService;
import it.itba.edu.ar.model.Hashtag;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

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
		
		int default_time = 7;
		
		String time = req.getParameter("time");
		if(time != null){
			try{
				default_time = Integer.parseInt(time);
			}catch (NumberFormatException e){
				
			}
		}
		
		Date tdate = new Date(System.currentTimeMillis() - (default_time * DAY_IN_MS));
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
