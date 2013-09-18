package it.itba.edu.ar.servlets;

import it.itba.edu.ar.model.Buit;
import it.itba.edu.ar.services.BuitService;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class Hashtag extends BuitsHttpServlet {

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
		if(buits.isEmpty() || hashtag == null){
			req.setAttribute("error_log", "We couldn't find messages with \"#"+hashtagname+"\"");
			req.getRequestDispatcher("WEB-INF/jsp/error.jsp").forward(req, resp);
			return;
		}
		
		for(Buit buit: buits) {
			buit.setMessage(prepareBuit(buit.getMessage()));
		}	
		req.setAttribute("buits", buits);
		req.setAttribute("hashtag", hashtag);
		
		req.getRequestDispatcher("WEB-INF/jsp/hashtag.jsp").forward(req,
				resp);
		return;
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.getRequestDispatcher("WEB-INF/jsp/hashtag.jsp").forward(req,
				resp);
	}
}
