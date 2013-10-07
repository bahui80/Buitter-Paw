package it.itba.edu.ar.controllers;

import it.itba.edu.ar.model.User;
import it.itba.edu.ar.services.UrlService;
import it.itba.edu.ar.services.UserService;

import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class SearchController {

	private UserService userService;
	private SimpleDateFormat formatter;

	@Autowired
	public SearchController(UserService userService,
			UrlService urlService) {
		this.userService = userService;
	}

	/*
	 * POST METHODS
	 */
	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView search_post(@RequestParam("name") String query) {
		return this.search(query);
	}
	/*
	 * GET METHODS
	 */
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView search(@RequestParam("name") String query) {
		ModelAndView mav  = new ModelAndView();
		mav.addObject("query", query);

		List<User> results = userService.search(query);
		for (User user : results) {
			user.setSimpleDateFormatter(formatter);
		}
		mav.addObject("results", results);
	
	//	req.getRequestDispatcher("WEB-INF/jsp/searchresults.jsp").forward(req,
		//		resp);
		return mav;
	}
}
