package it.itba.edu.ar.servlets;

import it.itba.edu.ar.model.User;
import it.itba.edu.ar.services.UserService;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.DiskFileUpload;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;

@SuppressWarnings({ "serial", "deprecation" })
public class Register extends HttpServlet {
	private Boolean error = false;
	private UserService userService;

	@Override
	public void init() throws ServletException {
		userService = UserService.sharedInstance();
	};

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String username = (String) req.getSession().getAttribute("user");
		
		if(!req.getRequestURI().contains("editprofile")) {
			req.setAttribute("action", "register");
			if(req.getSession().getAttribute("user") != null){
				req.setAttribute("error_logged_in", "error");
				resp.sendRedirect("/Buitter/login");
				return;
			}
		} else {
			req.setAttribute("action", "editprofile");
			if (username != null) {
				User user = userService.getUserByUsername(username);
				
				req.setAttribute("user_username", user.getUsername());
				req.setAttribute("user_password", user.getPassword());
				req.setAttribute("user_password2", user.getPassword());
				req.setAttribute("user_name", user.getName());
				req.setAttribute("user_surname", user.getSurname());
				req.setAttribute("user_description", user.getDescription());
				req.setAttribute("user_question", user.getSecretQuestion());
				req.setAttribute("user_answer", user.getSecretAnswer());
			} else {
				req.getRequestDispatcher("WEB-INF/jsp/error.jsp").forward(req, resp);
				return;
			}
		}
		
		req.getRequestDispatcher("WEB-INF/jsp/registration.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
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
				request.setAttribute("error_photo", "File invalid. Only jpeg, jpg or png images");
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
			request.setAttribute("action", "register");
		} else {
			request.setAttribute("action", "editprofile");
		}
		
		if (error) {
			error = false;
			request.getRequestDispatcher("WEB-INF/jsp/registration.jsp").forward(request, response);
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
			response.sendRedirect("/Buitter/home");
		}

	}

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