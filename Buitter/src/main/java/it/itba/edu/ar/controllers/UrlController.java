package it.itba.edu.ar.controllers;

import it.itba.edu.ar.services.UrlService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

public class UrlController {

	private UrlService urlService;
	
	@Autowired
	public UrlController(UrlService urlService) {
		this.urlService = urlService;
	}
	
	/*
	 * POST METHODS
	 */
	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView url(@RequestParam(value = "buiturl") String buiturl){
//		String url = urlServic
        return new ModelAndView("redirect:" + buiturl);
	}
	
}
