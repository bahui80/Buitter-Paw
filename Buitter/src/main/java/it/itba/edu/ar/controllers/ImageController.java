package it.itba.edu.ar.controllers;

import java.io.IOException;

import it.itba.edu.ar.model.User;
import it.itba.edu.ar.services.UserService;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ImageController {
	private UserService userService;

	@Autowired
	public ImageController(UserService userService) {
		this.userService = userService;
	}

	/*
	 * GET METHODS
	 */
	@RequestMapping(method = RequestMethod.GET)
	public void image(@RequestParam("name") String profile,
			HttpServletResponse resp) throws IOException {

		if (profile != null && userService.checkUsername(profile)) {
			User usr = userService.getUserByUsername(profile);

			if (usr.getPhoto() != null && usr.getPhoto().length > 0) {
				resp.setContentType("image");
				resp.getOutputStream().write(usr.getPhoto());
				resp.getOutputStream().flush();
				resp.getOutputStream().close();
			}
		}
	}
}
