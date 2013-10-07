package it.itba.edu.ar.controllers;

import it.itba.edu.ar.model.User;
import it.itba.edu.ar.services.UserService;
import it.itba.edu.ar.servlets.ServletValidationException;

import java.sql.Timestamp;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.DiskFileUpload;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

@Controller
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
			@RequestParam(value = "username") String username,
			@RequestParam(value = "recover", required = false) String state,
			@RequestParam(value = "answer", required = false) String answer,
			@RequestParam(value = "password", required = false) String password,
			@RequestParam(value = "password2", required = false) String password2) {

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
			} else {
				User user = userService.getUserByUsername(username);
				user.setPassword(password);
				userService.updateUser(user);

				return new ModelAndView("redirect:login");
			}
		}
		return mav;
	}

	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView login(
			@RequestParam(value = "logout", required = false) String logout,
			@RequestParam(value = "cont", required = false) String cont,
			@RequestParam("username") String username,
			@RequestParam("password") String password, HttpSession session) {
		ModelAndView mav = new ModelAndView();
		RequestAttributes requestAttributes = RequestContextHolder
				.getRequestAttributes();
		HttpServletRequest request = ((ServletRequestAttributes) requestAttributes)
				.getRequest();
		// checkeo que no venga del formulario de logout
		if (logout != null) {
			return new ModelAndView("redirect:logout");
		}
		if (cont != null) {
			return new ModelAndView("redirect:../home/home");
		}
		// checkeo que no este loggeado ya
		String usernameCookie = (String) session.getAttribute("user");
		if (usernameCookie != null) {
			return new ModelAndView("redirect:login");
		}

		// logueo porque no esta ni loggeado ni se quiere desloguear

		User user = userService.login(new User(username, password));

		if (user != null) {
			session.setAttribute("user", username);
			return new ModelAndView("redirect:../home/home");
		} else {
			mav.addObject("user_username", username);
			mav.addObject("error_login", "Username or password incorrect");

			return new ModelAndView("redirect:login");
		}
	}

	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView registration(
			@RequestParam(value = "a", required = false) String a,
			HttpSession session) {
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
			if (photoName.toLowerCase().contains("jpg")
					|| photoName.toLowerCase().contains("jpeg")
					|| photoName.toLowerCase().contains("png")) {
				photo = fileItems.get(8).get();
			} else {
				mav.addObject("error_photo",
						"File invalid. Only jpeg, jpg or png images");
				error = true;
			}
		}

		if (request.getRequestURI().contains("register")) {
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

		if (!request.getRequestURI().contains("editprofile")) {
			mav.addObject("action", "register");
		} else {
			mav.addObject("action", "editprofile");
		}

		if (error) {
			error = false;
			return mav;
		} else {
			if (!request.getRequestURI().contains("editprofile")) {
				userService.register(new User(name, surname, username,
						password, description, question, answer, creationDate,
						photo));
				session.setAttribute("user", username);
			} else {
				if (photo == null) {
					photo = userService.getUserByUsername(username).getPhoto();
				}
				userService.updateUser(new User(name, surname, username,
						password, description, question, answer, creationDate,
						photo));
			}
			return new ModelAndView("redirect:../home/home");
		}

	}

	/*
	 * GET METHODS
	 */
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView login(HttpSession session) {
		ModelAndView mav = new ModelAndView();
		RequestAttributes requestAttributes = RequestContextHolder
				.getRequestAttributes();
		HttpServletRequest request = ((ServletRequestAttributes) requestAttributes)
				.getRequest();

		if (session.getAttribute("user") != null) {
			mav.addObject("error_logged_in", "error");
		}
		return mav;
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView forgotPassword() {
		return new ModelAndView();
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView logout(HttpSession session) {
		RequestAttributes requestAttributes = RequestContextHolder
				.getRequestAttributes();
		HttpServletRequest request = ((ServletRequestAttributes) requestAttributes)
				.getRequest();
		session.invalidate();

		return new ModelAndView("redirect:../home/home");
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView editprofile(HttpSession session) {
		ModelAndView mav = new ModelAndView();
		
		String username = (String) session.getAttribute("user");
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
			
			mav.setViewName("user/registration");
		} else {

			mav.setViewName("error");
		}
		return mav;
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView registration(HttpSession session) {
		ModelAndView mav = new ModelAndView();
		RequestAttributes requestAttributes = RequestContextHolder
				.getRequestAttributes();
		HttpServletRequest request = ((ServletRequestAttributes) requestAttributes)
				.getRequest();

		String username = (String) session.getAttribute("user");

		if (!request.getRequestURI().contains("editprofile")) {
			mav.addObject("action", "registration");
			if (session.getAttribute("user") != null) {
				mav.addObject("error_logged_in", "error");
				return new ModelAndView("redirect:login");
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

				mav.setViewName("error");
				return mav;
			}
		}

		return mav;
	}

	/*
	 * OTHER METHODS
	 */
	private void checkUsername(String username, HttpServletRequest request) {
		if (!username.equals("")) {
			if (username.trim().length() == 0
					|| !username.matches("[a-zA-Z0-9_.-]+")) {
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
			if (!password.matches("[a-zA-Z0-9]+")) {
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
			if (name.trim().length() == 0 || !name.matches("^[\\p{L} ]+$")) {
				request.setAttribute("error_name", "Invalid format name");
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
			if (surname.trim().length() == 0
					|| !surname.matches("^[\\p{L} ]+$")) {
				request.setAttribute("error_surname", "Invalid format surname");
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
			if (description.trim().length() == 0) {
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

	private void checkAnswer(String answer, String question,
			HttpServletRequest request) {
		if (!answer.equals("")) {
			if (answer.trim().length() == 0) {
				request.setAttribute("error_answer", "Invalid format answer");
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
