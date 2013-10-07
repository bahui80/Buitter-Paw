package it.itba.edu.ar.controllers;

import it.itba.edu.ar.model.Hashtag;
import it.itba.edu.ar.model.User;
import it.itba.edu.ar.services.BuitService;
import it.itba.edu.ar.services.UrlService;
import it.itba.edu.ar.services.UserService;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {

	private BuitService buitService;
	private UserService userService;
	private long DAY_IN_MS = 1000 * 60 * 60 * 24;
	private static final int trendingQuantity = 10;

	@Autowired
	public HomeController(BuitService buitService, UserService userService,
			UrlService urlService) {
		this.buitService = buitService;
		this.userService = userService;
	}
	
	/*
	 * POST METHODS
	 */
	@RequestMapping(method = RequestMethod.POST)
	public String home_post(@RequestParam("time") String time){
		return "redirect:home";
	}
	
	/*
	 * GET METHODS
	 */
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView home(@RequestParam(value = "time", required = false) String time) {
		ModelAndView mav = new ModelAndView();
		int default_time = 7;

		if (time != null) {
			try {
				int aux = Integer.parseInt(time);
				if (aux != 7 && aux != 30 && aux != 1) {
					// req.getRequestDispatcher("error.jsp").forward(req, resp);
					return mav;
				}
				default_time = aux;
			} catch (NumberFormatException e) {

			}
		}

		Date tdate = new Date(System.currentTimeMillis()
				- (default_time * DAY_IN_MS));
		List<Hashtag> trendingTopics = buitService.trendingTopics(tdate,
				trendingQuantity);

		mav.addObject("trending", trendingTopics);

		List<User> userList = userService.search("");
		if (userList.size() > 5) {
			userList = userList.subList(0, 5);
		}
		mav.addObject("userlist", userList);
		mav.addObject("selectedTime", default_time);

		// req.getRequestDispatcher("index.jsp").forward(req, resp);
		return mav;
	}

}
