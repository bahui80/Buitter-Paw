package it.itba.edu.ar.servlets;

import it.itba.edu.ar.model.Buit;
import it.itba.edu.ar.services.BuitService;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class Hashtag extends HttpServlet {

	private BuitService buitService;
	
	@Override
	public void init() throws ServletException {
		buitService = BuitService.sharedInstance();
	};
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String hashtagname = req.getParameter("name");
		List<Buit> buits = buitService.getBuitsForHashtag(hashtagname);
		it.itba.edu.ar.model.Hashtag hashtag = buitService.getHashtag(hashtagname);
				
		req.setAttribute("buits", buits);
		req.setAttribute("hashtag", hashtag);
		
		req.getRequestDispatcher("WEB-INF/jsp/hashtag.jsp").forward(req,
				resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.getRequestDispatcher("WEB-INF/jsp/hashtag.jsp").forward(req,
				resp);
	}
}
