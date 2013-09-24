package it.itba.edu.ar.controllers;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

import it.itba.edu.ar.model.User;
import it.itba.edu.ar.services.UserService;
import it.itba.edu.ar.servlets.ServletValidationException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.DiskFileUpload;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

public class UserController {

	private UserService userService;
	private Boolean error = false;

	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}

	/*
	 * POST METHODS
	 */
	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView forgotPassword(
			@RequestParam("username") String username,
			@RequestParam("recover") String state,
			@RequestParam("answer") String answer,
			@RequestParam("password") String password,
			@RequestParam("password2") String password2) {

		ModelAndView mav = new ModelAndView();

		if (state.equals("recover")) {
			if (!username.equals("")) {
				if (userService.checkUsername(username)) {
					mav.addObject("correct_username", username);
					mav.addObject("question",
							userService.getUserByUsername(username)
									.getSecretQuestion());
				} else {
					mav.addObject("user_username", username);
					mav.addObject("error_username", "That user doesn't exist");
				}
			} else {
				mav.addObject("error_username", "You must enter an user");
			}
			// TODO: VER COMO DISPATCHEAR
			// req.getRequestDispatcher("WEB-INF/jsp/forgotpassword.jsp").forward(req,
			// resp);
			// return;
		} else {
			mav.addObject("correct_username", username);
			mav.addObject("question", userService.getUserByUsername(username)
					.getSecretQuestion());
			mav.addObject("user_answer", answer);
			if (!userService.getUserByUsername(username).getSecretAnswer()
					.equals(answer)) {
				error = true;
				mav.addObject("error_answer", "Incorrect secret answer");
			}
			RequestAttributes requestAttributes = RequestContextHolder
					.getRequestAttributes();
			HttpServletRequest req = ((ServletRequestAttributes) requestAttributes)
					.getRequest();
			checkPassword(password, password2, req);
			if (error) {
				error = false;
				// TODO: VER COMO DISPATCHEAR
				// req.getRequestDispatcher("WEB-INF/jsp/forgotpassword.jsp").forward(req,
				// resp);
				// return;
			} else {
				User user = userService.getUserByUsername(username);
				user.setPassword(password);
				userService.updateUser(user);
				// TODO: VER COMO DISPATCHEAR
				// req.getRequestDispatcher("WEB-INF/jsp/login.jsp").forward(req,
				// resp);
				// return;
			}
		}

	}

	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView login(@RequestParam("logout") String logout,
			@RequestParam("cont") String cont,
			@RequestParam("username") String username,
			@RequestParam("password") String password) {
		ModelAndView mav = new ModelAndView();
		RequestAttributes requestAttributes = RequestContextHolder
				.getRequestAttributes();
		HttpServletRequest request = ((ServletRequestAttributes) requestAttributes)
				.getRequest();
		// checkeo que no venga del formulario de logout
		if (logout != null) {
			// TODO ver como redirigir
			// resp.sendRedirect("logout");
			// return;
		}
		if (cont != null) {
			// TODO ver como redigir
			// resp.sendRedirect("/Buitter");
			// return;
		}
		// checkeo que no este loggeado ya
		String usernameCookie = (String) request.getSession().getAttribute(
				"user");
		if (usernameCookie != null) {
			this.login();
			// return;
		}

		// logueo porque no esta ni loggeado ni se quiere desloguear

		User user = userService.login(new User(username, password));

		if (user != null) {
			request.getSession().setAttribute("user", username);
			// TODO ver como redigir
			// resp.sendRedirect("/Buitter/home");
			return;
		} else {
			mav.addObject("user_username", username);
			mav.addObject("error_login", "Username or password incorrect");
			// TODO redirigir bien
			// req.getRequestDispatcher("WEB-INF/jsp/login.jsp").forward(req,
			// resp);
			// return;
		}
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView register() {
		ModelAndView mav = new ModelAndView();
		RequestAttributes requestAttributes = RequestContextHolder
				.getRequestAttributes();
		HttpServletRequest request = ((ServletRequestAttributes) requestAttributes)
				.getRequest();
		DiskFileUpload fu = new DiskFileUpload();
		List<FileItem> fileItems;

		try {
			fileItems = fu.parseRequest(request);
		} catch (FileUploadException e) {
			throw new ServletValidationException();
		}

		String username = fileItems.get(0).getString();
		String password = fileItems.get(1).getString();
		String password2 = fileItems.get(2).getString();
		String name = fileItems.get(3).getString();
		String surname = fileItems.get(4).getString();
		String description = fileItems.get(5).getString();
		String question = fileItems.get(6).getString();
		String answer = fileItems.get(7).getString();
		Timestamp creationDate = new Timestamp(0);
		String photoName = fileItems.get(8).getName();
		byte[] photo = null;

		if (!photoName.equals("")) {
			if(photoName.toLowerCase().contains("jpg") || photoName.toLowerCase().contains("jpeg") || photoName.toLowerCase().contains("png")) {
				photo = fileItems.get(8).get();
			} else {
				mav.addObject("error_photo", "File invalid. Only jpeg, jpg or png images");
				error = true;
			}
		} 

		if(request.getRequestURI().contains("register")) {
			checkUsername(username, request);
		} else {
			request.setAttribute("user_username", username);
		}
		checkPassword(password, password2, request);
		checkName(name, request);
		checkSurname(surname, request);
		checkDescription(description, request);
		request.setAttribute("user_question", question);
		checkAnswer(answer, question, request);
		
		if(!request.getRequestURI().contains("editprofile")) {
			mav.addObject("action", "register");
		} else {
			mav.addObject("action", "editprofile");
		}
		
		if (error) {
			error = false;
			//TODO cambiar redirect
			//request.getRequestDispatcher("WEB-INF/jsp/registration.jsp").forward(request, response);
		} else {
			if(!request.getRequestURI().contains("editprofile")) {
				userService.register(new User(name, surname, username, password, description, question, answer, creationDate, photo));
				request.getSession().setAttribute("user", username);
			} else {
				if(photo == null) {
					photo = userService.getUserByUsername(username).getPhoto();
				}
				userService.updateUser(new User(name, surname, username, password, description, question, answer, creationDate, photo));
			}
			//TODO redirigir
			//response.sendRedirect("/Buitter/home");
		}

	}

	/*
	 * GET METHODS
	 */
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView login() {
		ModelAndView mav = new ModelAndView();
		RequestAttributes requestAttributes = RequestContextHolder
				.getRequestAttributes();
		HttpServletRequest request = ((ServletRequestAttributes) requestAttributes)
				.getRequest();

		if (request.getSession().getAttribute("user") != null) {
			mav.addObject("error_logged_in", "error");
		}
		// TODO: ver cmo redirigi
		// req.getRequestDispatcher("WEB-INF/jsp/login.jsp").forward(req, resp);
		return mav;
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView logout() {
		RequestAttributes requestAttributes = RequestContextHolder
				.getRequestAttributes();
		HttpServletRequest request = ((ServletRequestAttributes) requestAttributes)
				.getRequest();
		request.getSession().invalidate();
		// TODO ver como redirigi
		// resp.sendRedirect("/Buitter/home");
		// return;
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView register() {
		ModelAndView mav = new ModelAndView();
		RequestAttributes requestAttributes = RequestContextHolder
				.getRequestAttributes();
		HttpServletRequest request = ((ServletRequestAttributes) requestAttributes)
				.getRequest();

		String username = (String) request.getSession().getAttribute("user");

		if (!request.getRequestURI().contains("editprofile")) {
			mav.addObject("action", "register");
			if (request.getSession().getAttribute("user") != null) {
				mav.addObject("error_logged_in", "error");
				// TODO ver como redirigir
				// resp.sendRedirect("/Buitter/login");
				// return;
			}
		} else {
			mav.addObject("action", "editprofile");
			if (username != null) {
				User user = userService.getUserByUsername(username);

				mav.addObject("user_username", user.getUsername());
				mav.addObject("user_password", user.getPassword());
				mav.addObject("user_password2", user.getPassword());
				mav.addObject("user_name", user.getName());
				mav.addObject("user_surname", user.getSurname());
				mav.addObject("user_description", user.getDescription());
				mav.addObject("user_question", user.getSecretQuestion());
				mav.addObject("user_answer", user.getSecretAnswer());
			} else {
				// TODO redirigr
				// req.getRequestDispatcher("WEB-INF/jsp/error.jsp").forward(req,
				// resp);
				// return;
			}
		}
		//TODO redirigi
//		req.getRequestDispatcher("WEB-INF/jsp/registration.jsp").forward(req,
		//		resp);
	}

	/*
	 * OTHER METHODS
	 */
	private void checkUsername(String username, HttpServletRequest request) {
		if (!username.equals("")) {
			if(username.trim().length() == 0 || !username.matches("[a-zA-Z0-9_.-]+")) {
				request.setAttribute("error_username",
						"Invalid format username");
				error = true;
			}
			if (username.length() <= 32) {
				if (userService.checkUsername(username)) {
					request.setAttribute("error_username",
							"The username already exists");
					error = true;
				}
			} else {
				request.setAttribute("error_username",
						"The username must contain up to 32 characters");
				error = true;
			}
			request.setAttribute("user_username", username);
		} else {

			request.setAttribute("error_username",
					"You must insert an username");
			error = true;
		}
	}

	private void checkPassword(String password, String password2,
			HttpServletRequest request) {
		if (!password.equals("")) {
			if(!password.matches("[a-zA-Z0-9]+")) {
				request.setAttribute("error_password",
						"Invalid password format");
				error = true;
			}
			if (password.length() > 32) {
				request.setAttribute("error_password",
						"The password must contain up to 32 characters");
				error = true;
			} else if (!password2.equals(password)) {
				request.setAttribute("error_password2",
						"The passwords must match");
				error = true;
			}
			request.setAttribute("user_password2", password2);
			request.setAttribute("user_password", password);
		} else {
			request.setAttribute("error_password", "You must insert a password");
			error = true;
		}
	}

	private void checkName(String name, HttpServletRequest request) {
		if (!name.equals("")) {
			if(name.trim().length() == 0 || !name.matches("^[\\p{L} ]+$")) {
				request.setAttribute("error_name",
						"Invalid format name");
				error = true;
			}
			if (name.length() > 32) {
				request.setAttribute("error_name",
						"The name must contain up to 32 characters");
				error = true;
			}
			request.setAttribute("user_name", name);
		} else {
			request.setAttribute("error_name", "You must insert a name");
			error = true;
		}
	}

	private void checkSurname(String surname, HttpServletRequest request) {
		if (!surname.equals("")) {
			if(surname.trim().length() == 0 || !surname.matches("^[\\p{L} ]+$")) {
				request.setAttribute("error_surname",
						"Invalid format surname");
				error = true;
			}
			if (surname.length() > 32) {
				request.setAttribute("error_surname",
						"The surname must contain up to 32 characters");
				error = true;
			}
			request.setAttribute("user_surname", surname);
		} else {
			request.setAttribute("error_surname", "You must insert a surname");
			error = true;
		}
	}

	private void checkDescription(String description, HttpServletRequest request) {
		if (!description.equals("")) {
			if(description.trim().length() == 0) {
				request.setAttribute("error_description",
						"Invalid format description");
				error = true;
			}
			if (description.length() > 140) {
				request.setAttribute("error_description",
						"The description must contain up to 140 characters");
				error = true;
			}
			request.setAttribute("user_description", description);
		} else {
			request.setAttribute("error_description",
					"You must insert a description");
			error = true;
		}
	}

	private void checkAnswer(String answer, String question, HttpServletRequest request) {
		if (!answer.equals("")) {
			if(answer.trim().length() == 0) {
				request.setAttribute("error_answer",
						"Invalid format answer");
				error = true;
			}
			if (answer.length() > 60) {
				request.setAttribute("error_answer",
						"The answer must contain up to 60 characters");
				error = true;
			}
			request.setAttribute("user_answer", answer);
			
		} else {
			request.setAttribute("error_answer",
					"You must insert a secret answer");
			error = true;
		}
		request.setAttribute("question", question);
	}
}
