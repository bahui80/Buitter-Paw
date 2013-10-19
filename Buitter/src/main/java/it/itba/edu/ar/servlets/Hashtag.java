package it.itba.edu.ar.servlets;

import it.itba.edu.ar.model.Buit;
import it.itba.edu.ar.model.Url;
import it.itba.edu.ar.services.impl.BuitServiceImpl;
import it.itba.edu.ar.services.impl.UrlServiceImpl;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class Hashtag extends BuitsHttpServlet {

	private BuitServiceImpl buitService;
	private UrlServiceImpl urlService;
	private SimpleDateFormat formatter;
	
	@Override
	public void init() throws ServletException {
		buitService = BuitServiceImpl.sharedInstance();
		urlService = UrlServiceImpl.sharedInstance();
		formatter = new SimpleDateFormat("EEE, MMM d, HH:mm:ss");
	};
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String hashtagname = req.getParameter("name");
		List<Url> urls;
		List<Buit> buits = buitService.getBuitsForHashtag(hashtagname);

		it.itba.edu.ar.model.Hashtag hashtag = buitService.getHashtag(hashtagname);
		hashtag.setSimpleDateFormatter(formatter);
		if(buits.isEmpty() || hashtag == null){
			req.setAttribute("error_log", "We couldn't find messages with \"#"+hashtagname+"\"");
			req.getRequestDispatcher("WEB-INF/jsp/error.jsp").forward(req, resp);
			return;
		}
		
		for(Buit buit: buits) {
			buit.setMessage(prepareBuitHashtag(buit.getMessage()));
			buit.setSimpleDateFormatter(formatter);
			if(urlService.buitHasUrl(buit)){
				urls = urlService.urlsForBuit(buit);
				buit.setMessage(prepareBuitUrl(buit.getMessage(), urls));
			}
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
