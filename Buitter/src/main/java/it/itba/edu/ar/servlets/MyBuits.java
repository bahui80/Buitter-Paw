package it.itba.edu.ar.servlets;

import it.itba.edu.ar.model.Buit;
import it.itba.edu.ar.model.Hashtag;
import it.itba.edu.ar.model.Url;
import it.itba.edu.ar.model.User;
import it.itba.edu.ar.services.BuitService;
import it.itba.edu.ar.services.UrlService;
import it.itba.edu.ar.services.UserService;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class MyBuits extends BuitsHttpServlet {
	
	private UserService userService;
	private BuitService buitService;
	private UrlService urlService;
	private SimpleDateFormat formatter;
	
	@Override
	public void init() throws ServletException {
		userService = UserService.sharedInstance();
		buitService = BuitService.sharedInstance();
		urlService = UrlService.sharedInstance();
		formatter = formatter = new SimpleDateFormat("EEE, MMM d, HH:mm:ss");
	};
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String profile = request.getParameter("name");
		List<Url> urls;
		
		if(profile != null && userService.checkUsername(profile)) {
			User usr = userService.getUserByUsername(profile);
			usr.setSimpleDateFormatter(formatter);
			List<Buit> buits = buitService.getUserBuits(usr);
			for(Buit buit: buits) {
				buit.setSimpleDateFormatter(formatter);
				buit.setMessage(prepareBuitHashtag(buit.getMessage()));
				if(urlService.buitHasUrl(buit)){
					urls = urlService.urlsForBuit(buit);
					buit.setMessage(prepareBuitUrl(buit.getMessage(), urls));
				}
			}
			request.setAttribute("buits", buits);
			request.setAttribute("user_info", usr);
		} else {
			request.setAttribute("error_log", "We couldn't find an account named \""+profile+"\"");
			request.getRequestDispatcher("WEB-INF/jsp/error.jsp").forward(request, response);
			return;
		}
		request.getRequestDispatcher("WEB-INF/jsp/mybuits.jsp").forward(request, response);
		return;
	}
	
	
	//TODO: ACA NOSE QUE TOCAR BAHUI O JAVI, DSP FIJENSE
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String buit = request.getParameter("buit");
		List<String> hashTags;
		List<String> urls;
		
		if(buit.trim().isEmpty()) {
			request.setAttribute("error_buit", "Your buit is empty");
			response.sendRedirect("profile?name=" + request.getSession().getAttribute("user"));
			return;
			//request.getRequestDispatcher("WEB-INF/jsp/mybuits.jsp").forward(request, response);
		} else {
			//primero buittea
			Buit buitAux = buitService.buit(new Buit(buit, userService.getUserByUsername((String)request.getSession().getAttribute("user")), new Timestamp(0)));
			//busco hashtags
			hashTags = getHashTags(buit);
			//busco urls
			urls = getUrls(buit);
			for(String hash: hashTags) {
				//por cada hashtag lo agrego con su buit
				String username = (String) request.getSession().getAttribute("user");
				User user = userService.getUserByUsername(username);
				Hashtag hashtag = new Hashtag(hash, new Timestamp(0), user);
				buitService.addHashtag(hashtag,buitAux);
			}
			for(String url: urls) {
				//por cada url la agrego
				urlService.insertUrl(new Url(url, buitAux.getId()));
			}
			response.sendRedirect("profile?name=" + request.getSession().getAttribute("user"));
			return;
		}
	}
}