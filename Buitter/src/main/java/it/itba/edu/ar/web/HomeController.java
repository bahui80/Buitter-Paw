package it.itba.edu.ar.web;

import it.itba.edu.ar.domain.buit.Buit;
import it.itba.edu.ar.domain.buit.BuitRepo;
import it.itba.edu.ar.domain.buit.Hashtag;
import it.itba.edu.ar.domain.user.User;
import it.itba.edu.ar.domain.user.UserRepo;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {

	private BuitRepo buitRepo;
	private UserRepo userRepo;
	private long DAY_IN_MS = 1000 * 60 * 60 * 24;
	private static final int trendingQuantity = 10;

	@Autowired
	public HomeController(BuitRepo buitRepo, UserRepo userRepo) {
		this.buitRepo = buitRepo;
		this.userRepo = userRepo;
	}

	/*
	 * GET METHODS
	 */
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView home(@RequestParam(value = "time", required = false) Integer time, HttpSession session) {
		ModelAndView mav = new ModelAndView();
		
		// DE ACA 
		if(session.getAttribute("user") != null) {
			User user = userRepo.get((String) session.getAttribute("user"));
			mav.addObject("recommendations", userRepo.whoToFollow(user));
			for(Buit buit: user.getBuits()) {
				buit.setMessage(ViewControllerHelper.prepareBuitHashtag(buit.getMessage(),buit.getHashtags(), "home"));
				buit.setMessage(ViewControllerHelper.prepareBuitUrl(buit.getMessage(), buit.getUrls()));
				buit.setMessage(ViewControllerHelper.prepareBuitUser(buit.getMessage(), buit.getMentionedBuitters(), "home"));
			}
			mav.addObject("user_info", user);
		}	
		
		// A ACA VERLO MUY BIEN
		
		int default_time = 7;

		if (time != null) {
			if (time != 7 && time != 30 && time != 1) {
				mav.setViewName("error");
				return mav;
			}
			default_time = time;
		}

		Date tdate = new Date(System.currentTimeMillis() - (default_time * DAY_IN_MS));
		List<Hashtag> trendingTopics = buitRepo.getHashtagsSinceDate(tdate,	trendingQuantity);

		mav.addObject("trending", trendingTopics);

		List<User> userList = userRepo.getAll();
		if (userList.size() > 5) {
			userList = userList.subList(0, 5);
		}
		mav.addObject("userlist", userList);
		mav.addObject("selectedTime", default_time);

		return mav;
	}
}
