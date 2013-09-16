package it.itba.edu.ar.servlets;

import it.itba.edu.ar.dao.UserManager;
import it.itba.edu.ar.model.User;
import it.itba.edu.ar.services.UserService;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class EditProfile extends HttpServlet {
	private Boolean error = false;
	private UserService userService;
	
	@Override
	public void init() throws ServletException {
		userService = UserService.sharedInstance();
	};
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		if (req.getSession().getAttribute("user") != null) {

			User user = (User) req.getSession().getAttribute("user");

			req.setAttribute("password", user.getPassword());
			req.setAttribute("password2", user.getPassword());
			req.setAttribute("name", user.getName());
			req.setAttribute("surname", user.getSurname());
			req.setAttribute("description", user.getDescription());
			req.setAttribute("question", user.getSecretQuestion());
			req.setAttribute("answer", user.getSecretAnswer());
			req.setAttribute("photo", user.getPhoto());

			req.getRequestDispatcher("/WEB-INF/jsp/editprofile.jsp").forward(
					req, resp);
		}

	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		User user = (User) request.getSession().getAttribute("user");

		String password = request.getParameter("password");
		String password2 = request.getParameter("password2");
		String name = request.getParameter("name");
		String surname = request.getParameter("surname");
		String description = request.getParameter("description");
		String answer = request.getParameter("answer");
		String photo = request.getParameter("photo");

		if (!password.equals(user.getPassword())
				&& checkPassword(password, password2, request)) {
			user.setPassword(password);
		}
		if (!name.equals(user.getName()) && checkName(name, request)) {
			user.setName(name);
		}
		if (!surname.equals(user.getSurname())
				&& checkSurname(surname, request)) {
			user.setSurname(surname);
		}
		if (!description.equals(user.getDescription())
				&& checkDescription(description, request)) {
			user.setDescription(description);
		}
		if (!answer.equals(user.getSecretAnswer())
				&& checkAnswer(answer, request)) {
			user.setSecretAnswer(answer);
		}
		if (!photo.equals(user.getPhoto())) {
			user.setPhoto(photo);
		}

		if (error) {
			request.getRequestDispatcher("WEB-INF/jsp/editprofile.jsp")
					.forward(request, response);
		} else {
			userService.updateUser(user);
			request.getSession().setAttribute("user", user);
		}
		/*
		 * DiskFileUpload fu = new DiskFileUpload(); // If file size exceeds, a
		 * FileUploadException will be thrown // fu.setSizeMax(100000000);
		 * 
		 * List fileItems; try { fileItems = fu.parseRequest(request);
		 * 
		 * Iterator itr = fileItems.iterator();
		 * 
		 * while(itr.hasNext()) { FileItem fi = (FileItem)itr.next();
		 * 
		 * //Check if not form field so as to only handle the file inputs //else
		 * condition handles the submit button input if(!fi.isFormField()) {
		 * System.out.println("nNAME: "+fi.getName());
		 * System.out.println("SIZE: "+fi.getSize());
		 * //System.out.println(fi.getOutputStream().toString()); File fNew= new
		 * File(fi.getName());
		 * 
		 * System.out.println(fNew.getAbsolutePath()); fi.write(fNew); } else {
		 * System.out.println("Field ="+fi.getFieldName()); } } }catch
		 * (FileUploadException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); } catch (Exception e) { // TODO Auto-generated
		 * catch block e.printStackTrace(); }
		 */

	}

	private boolean checkPassword(String password, String password2,
			HttpServletRequest request) {
		if (!password.equals("")) {
			if (password.length() > 32) {
				request.setAttribute("error_password",
						"The password must contain up to 32 characters");
				error = true;
				return false;
			} else if (!password2.equals("") && !password2.equals(password)) {
				request.setAttribute("error_password2",
						"The passwords must match");
				error = true;
				return false;
			}
		} else {
			request.setAttribute("error_password", "You must insert a password");
			error = true;
			return false;
		}
		return true;
	}

	private boolean checkName(String name, HttpServletRequest request) {
		if (!name.equals("")) {
			if (name.length() > 32) {
				request.setAttribute("error_name",
						"The name must contain up to 32 characters");
				error = true;
				return false;
			}
		} else {
			request.setAttribute("error_name", "You must insert a name");
			error = true;
			return false;
		}
		return true;
	}

	private boolean checkSurname(String surname, HttpServletRequest request) {
		if (!surname.equals("")) {
			if (surname.length() > 32) {
				request.setAttribute("error_surname",
						"The surname must contain up to 32 characters");
				error = true;
				return false;
			}
		} else {
			request.setAttribute("error_surname", "You must insert a surname");
			error = true;
			return false;
		}
		return true;
	}

	private boolean checkDescription(String description,
			HttpServletRequest request) {
		if (!description.equals("")) {
			if (description.length() > 140) {
				request.setAttribute("error_description",
						"The description must contain up to 140 characters");
				error = true;
				return false;
			}
		} else {
			request.setAttribute("error_description",
					"You must insert a description");
			error = true;
			return false;
		}
		return true;
	}

	private boolean checkAnswer(String answer, HttpServletRequest request) {
		if (!answer.equals("")) {
			if (answer.length() > 60) {
				request.setAttribute("error_answer",
						"The answer must contain up to 60 characters");
				error = true;
				return false;
			}
		} else {
			request.setAttribute("error_answer",
					"You must insert a secret answer");
			error = true;
			return false;
		}
		return true;
	}
}
