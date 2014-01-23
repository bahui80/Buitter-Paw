package it.itba.edu.ar.web;

import it.itba.edu.ar.domain.user.User;
import it.itba.edu.ar.domain.user.UserRepo;

import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


public class SearchController {

	private UserRepo userRepo;

	public SearchController(UserRepo userRepo) {
		this.userRepo = userRepo;
	}

	/*
	 * GET METHODS
	 */
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView search(@RequestParam(value = "name", required = false) String query) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("query", query);

		List<User> results = userRepo.search(query);
		mav.addObject("results", results);

		return mav;
	}
}
