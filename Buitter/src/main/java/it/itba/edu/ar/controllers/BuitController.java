package it.itba.edu.ar.controllers;

import it.itba.edu.ar.model.Buit;
import it.itba.edu.ar.model.Hashtag;
import it.itba.edu.ar.model.Url;
import it.itba.edu.ar.model.User;
import it.itba.edu.ar.services.BuitService;
import it.itba.edu.ar.services.UrlService;
import it.itba.edu.ar.services.UserService;
import it.itba.edu.ar.servlets.ViewControllerHelper;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class BuitController {

	private BuitService buitService;
	private UserService userService;
	private UrlService urlService;
	private final SimpleDateFormat formatter = new SimpleDateFormat(
			"EEE, MMM d, HH:mm:ss");

	/*
	 * POST METHODS
	 */

	@Autowired
	public BuitController(BuitService buitService, UserService userService,
			UrlService urlService) {
		this.buitService = buitService;
		this.userService = userService;
		this.urlService = urlService;
	}

	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView delete(@RequestParam("buitid") int buitid, HttpSession session ) {
		ModelAndView mav = new ModelAndView();
		buitService.removeBuit(buitid);
		// TODO: VER COMO GARCHA REDIRIGIR
		// resp.sendRedirect("profile?name=" +
		// req.getSession().getAttribute("user"));
		mav.setViewName("redirect:profile?name=" + session.getAttribute("user"));
		return mav;
	}

	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView buit(@RequestParam("buit") String buit, HttpSession session) {
		ModelAndView mav = new ModelAndView();

		List<String> hashTags;
		List<String> urls;

		if (buit.trim().isEmpty()) {
			mav.addObject("error_buit", "Your buit is empty");
			// TODO: REDIRIGIR A BUITCONTROLLER CON GET
			// response.sendRedirect("profile?name=" +
			// request.getSession().getAttribute("user"));
			// return;
			// request.getRequestDispatcher("WEB-INF/jsp/mybuits.jsp").forward(request,
			// response);
		} else if (buit.length() > 140) {
			mav.addObject("error_buit", "Buits can have up to 140 characters");
			// TODO: REDIRIGIR A BUITCONTROLLER CON GET
			// response.sendRedirect("profile?name=" +
			// request.getSession().getAttribute("user"));
		} else {
			// primero buittea
			Buit buitAux = buitService.buit(new Buit(buit, userService
					.getUserByUsername((String) session.getAttribute("user")), new Timestamp(0)));
			// busco hashtags
			hashTags = ViewControllerHelper.getHashTags(buit);
			// busco urls
			urls = ViewControllerHelper.getUrls(buit);
			for (String hash : hashTags) {
				// por cada hashtag lo agrego con su buit
				String username = (String) session.getAttribute(
						"user");
				User user = userService.getUserByUsername(username);
				Hashtag hashtag = new Hashtag(hash, new Timestamp(0), user, 0);
				buitService.addHashtag(hashtag, buitAux);
			}
			for (String url : urls) {
				// por cada url la agrego
//				urlService.insertUrl(new Url(url, buitAux.getId()));
			}
			// TODO: VER A QUE GARCHA REDIRIGIR
			// response.sendRedirect("profile?name="
			// + request.getSession().getAttribute("user"));
			
		}
		mav.setViewName("redirect:profile?name=" + session.getAttribute("user"));
		return mav;
	}

	/*
	 * GET METHODS
	 */
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView profile(@RequestParam("name") String profile) {
		ModelAndView mav = new ModelAndView();

		List<Url> urls;

		if (profile != null && userService.checkUsername(profile)) {
			User usr = userService.getUserByUsername(profile);
//			usr.setSimpleDateFormatter(formatter);
			List<Buit> buits = buitService.getUserBuits(usr);
			for (Buit buit : buits) {
//				buit.setSimpleDateFormatter(formatter);
				buit.setMessage(ViewControllerHelper.prepareBuitHashtag(buit
						.getMessage()));
				if (urlService.buitHasUrl(buit)) {
					urls = urlService.urlsForBuit(buit);
					buit.setMessage(ViewControllerHelper.prepareBuitUrl(
							buit.getMessage(), urls));
				}
			}
			mav.addObject("buits", buits);
			mav.addObject("user_info", usr);
		} else {
			mav.addObject("error_log", "We couldn't find an account named \""
					+ profile + "\"");
			// TODO: VER COMO REDIRIGIR
			// request.getRequestDispatcher("WEB-INF/jsp/error.jsp").forward(
			// request, response);
			// return;
			return new ModelAndView("home");
		}
		// TODO: VER COMO REDIRIGIR
		// request.getRequestDispatcher("WEB-INF/jsp/mybuits.jsp").forward(
		// request, response);
		// return;
		return mav;
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView hashtag(@RequestParam("name") String hashtagname) {
		ModelAndView mav = new ModelAndView();

		List<Url> urls;
		List<Buit> buits = buitService.getBuitsForHashtag(hashtagname);

		Hashtag hashtag = buitService.getHashtag(hashtagname);
		if (buits.isEmpty() || hashtag == null) {
			// TODO: VER COMO GARCHA REDIRIGIR
			// req.setAttribute("error_log",
			// "We couldn't find messages with \"#"+hashtagname+"\"");
			// req.getRequestDispatcher("WEB-INF/jsp/error.jsp").forward(req,
			// resp);
			// return;
			return new ModelAndView("../error");
		}
//		hashtag.setSimpleDateFormatter(formatter);

		for (Buit buit : buits) {
			buit.setMessage(ViewControllerHelper.prepareBuitHashtag(buit
					.getMessage()));
//			buit.setSimpleDateFormatter(formatter);
			if (urlService.buitHasUrl(buit)) {
				urls = urlService.urlsForBuit(buit);
				buit.setMessage(ViewControllerHelper.prepareBuitUrl(
						buit.getMessage(), urls));
			}
		}
		mav.addObject("buits", buits);
		mav.addObject("hashtag", hashtag);

		// TODO: VER COMO GARCHA REDIRIGIR
		// req.getRequestDispatcher("WEB-INF/jsp/hashtag.jsp").forward(req,
		// resp);
		// return;
		return mav;
	}
}
