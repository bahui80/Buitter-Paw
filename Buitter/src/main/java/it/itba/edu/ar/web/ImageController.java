package it.itba.edu.ar.web;

import it.itba.edu.ar.domain.user.User;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

public class ImageController {

	/*
	 * GET METHODS
	 */
	@RequestMapping(method = RequestMethod.GET)
	public void image(@RequestParam("name") User usr, HttpServletResponse resp) throws IOException {

		if (usr != null) {
			if (usr.getPhoto() != null && usr.getPhoto().length > 0) {
				resp.setContentType("image");
				resp.getOutputStream().write(usr.getPhoto());
				resp.getOutputStream().flush();
				resp.getOutputStream().close();
			}
		}
	}
}
