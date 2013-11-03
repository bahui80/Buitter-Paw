package it.itba.edu.ar.web;

import it.itba.edu.ar.model.Buit;
import it.itba.edu.ar.model.FollowedEvent;
import it.itba.edu.ar.model.Hashtag;
import it.itba.edu.ar.model.User;
import it.itba.edu.ar.repo.BuitRepo;
import it.itba.edu.ar.repo.UserRepo;

import java.util.Date;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class BuitController {

	private BuitRepo buitRepo;
	private UserRepo userRepo;

	@Autowired
	public BuitController(BuitRepo buitRepo, UserRepo userRepo) {
		this.buitRepo = buitRepo;
		this.userRepo = userRepo;
	}
	
	/*
	 * POST METHODS
	 */
	
	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView favorite(@RequestParam("buitid") Buit fav, HttpSession session) {
		ModelAndView mav = new ModelAndView();
		User user  = userRepo.get((String) session.getAttribute("user"));
		user.getFavourites().add(fav);

		user.removeVisit();
		mav.setViewName("redirect:../profile/" + session.getAttribute("user"));
		return mav;
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView follow(@RequestParam("username") User userToFollow, HttpSession session) {
		ModelAndView mav = new ModelAndView();
		
		User user = userRepo.get((String) session.getAttribute("user"));
		// TODO VER como manejar el tema que ya estoy siguiendo al usuario
		if(session.getAttribute("user") == null || user.getUsername().equals(userToFollow.getUsername())) {
			// TODO manejar el error. No puede un usuario no logueado seguir a alguien. Tampoco puede autoseguirse un usuario logueado
		}
		
		userToFollow.getEvents().add(new FollowedEvent(new Date(), user));
		
		userToFollow.follow(user);
		userToFollow.removeVisit();
		mav.setViewName("redirect:../profile/" + userToFollow.getUsername());
		return mav;
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView unfollow(@RequestParam("username") User userToUnfollow, HttpSession session) {
		ModelAndView mav = new ModelAndView();
		
		User user = userRepo.get((String) session.getAttribute("user"));
		// TODO VER como manejar el tema que no estoy siguiendo al usuario
		if(session.getAttribute("user") == null || session.getAttribute("user").equals(userToUnfollow.getUsername())) {
			// TODO manejar el error. No puede un usuario no logueado dejar de seguir a alguien. Tampoco puede autoseguirse un usuario logueado
		}
		userToUnfollow.unfollow(user);
		userToUnfollow.removeVisit();
		mav.setViewName("redirect:../profile/" + userToUnfollow.getUsername());
		return mav;
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView rebuit(@RequestParam("buitid") Buit rebuit, HttpSession session) {
		ModelAndView mav = new ModelAndView();
		User user  = userRepo.get((String) session.getAttribute("user"));
		
		buitRepo.rebuit(rebuit, user);
		
		mav.setViewName("redirect:../profile/" + session.getAttribute("user"));
		return mav;
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView delete(@RequestParam("buitid") Buit buit, HttpSession session ) {
		ModelAndView mav = new ModelAndView();
		
		if(buit == null || !buit.getBuitter().getUsername().equals(session.getAttribute("user"))) {
			mav.setViewName("error");
			return mav;
		}
		buitRepo.removeBuit(buit);
		buit.getBuitter().removeVisit();
		mav.setViewName("redirect:../profile/" + session.getAttribute("user"));
		return mav;
	}

	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView buit(@RequestParam("buit") String message, HttpSession session) {
		ModelAndView mav = new ModelAndView();
		User user  = userRepo.get((String) session.getAttribute("user"));
		
		if (message.trim().isEmpty()) {
			mav.addObject("error_buit", "Your buit can't be empty");
		} else if (message.length() > 140) {
			mav.addObject("error_buit", "Buits can have up to 140 characters");
		} else {
			buitRepo.buit(message, user);
		}
		
		user.removeVisit();
		mav.setViewName("redirect:../profile/" + session.getAttribute("user"));
		return mav;
	}

	/*
	 * GET METHODS
	 */
	
	// TODO AGREGAR CHEQUEOS DE VALIDACION
	@RequestMapping(value = "/buit/following/{user}", method = RequestMethod.GET)
	public ModelAndView following(@PathVariable User user) {
		ModelAndView mav = new ModelAndView("buit/following");
		mav.addObject("user_info", user);
		return mav;
	}
	
	// TODO AGREGAR CHEQUEOS DE VALIDACION
	@RequestMapping(value = "/buit/followers/{user}", method = RequestMethod.GET)
	public ModelAndView followers(@PathVariable User user) {
		ModelAndView mav = new ModelAndView("buit/followers");
		mav.addObject("user_info", user);
		return mav;
	}
	
	@RequestMapping(value = "/buit/profile/{usr}", method = RequestMethod.GET)
	public ModelAndView profile(@PathVariable User usr) {
		ModelAndView mav = new ModelAndView("buit/profile");
	
		if (usr != null) {
			Set<Buit> buits = usr.getBuits();
					
			for (Buit buit : buits) {
				buit.setMessage(ViewControllerHelper.prepareBuitHashtag(buit.getMessage(),buit.getHashtags(), "profile"));
				buit.setMessage(ViewControllerHelper.prepareBuitUrl(buit.getMessage(), buit.getUrls()));
				buit.setMessage(ViewControllerHelper.prepareBuitUser(buit.getMessage(), buit.getMentionedBuitters(), "profile"));
			}
			mav.addObject("buits", buits);
			mav.addObject("user_info", usr);
			usr.addVisit();
		} else {
			mav.addObject("error_log", "We couldn't find an account for that user");
			mav.setViewName("error");
		}
		return mav;
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView hashtag(@RequestParam("name") Hashtag hashtag, @RequestParam("name") String hashtagName) {
		ModelAndView mav = new ModelAndView();
	
		if (hashtag == null) {
			mav.addObject("error_log", "Sorry, #" + hashtagName + " doesn't exist");
			mav.setViewName("error");
			return mav;
		}

		for (Buit buit : hashtag.getBuits()) {
			buit.setMessage(ViewControllerHelper.prepareBuitHashtag(buit.getMessage(),buit.getHashtags(), "hashtag"));
			buit.setMessage(ViewControllerHelper.prepareBuitUrl(buit.getMessage(), buit.getUrls()));
		}
		mav.addObject("buits", hashtag.getBuits());
		mav.addObject("hashtag", hashtag);
		mav.addObject("hashtagName",hashtagName);

		return mav;
	}
	
	// TODO AGREGAR CHEQUEOS DE VALIDACION
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView connect(HttpSession session) {
		ModelAndView mav = new ModelAndView();
		User user  = userRepo.get((String) session.getAttribute("user"));
		mav.addObject("events", user.getEvents());
		return mav;
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView favorites(HttpSession session) {
		ModelAndView mav = new ModelAndView();
		String username;
		User user;
		
		if((username = (String) session.getAttribute("user")) == null) {
			mav.setViewName("error");
			return mav;
		}
		
		user = userRepo.get(username);
		
		for(Buit buit: user.getFavourites()) {
			buit.setMessage(ViewControllerHelper.prepareBuitHashtag(buit.getMessage(),buit.getHashtags(), "favorites"));
			buit.setMessage(ViewControllerHelper.prepareBuitUrl(buit.getMessage(), buit.getUrls()));
			buit.setMessage(ViewControllerHelper.prepareBuitUser(buit.getMessage(), buit.getMentionedBuitters(), "favorites"));
		}
		
		mav.addObject("favorites", user.getFavourites());		
		return mav;
	}

}
