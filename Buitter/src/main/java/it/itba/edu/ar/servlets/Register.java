package it.itba.edu.ar.servlets;

import it.itba.edu.ar.model.User;
import it.itba.edu.ar.services.UserService;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
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
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		if(req.getSession().getAttribute("user") != null){
			req.setAttribute("error_logged_in", "error");
			resp.sendRedirect("/Buitter/login");
		}
		
		req.getRequestDispatcher("WEB-INF/jsp/registration.jsp").forward(req,
				resp);
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
		Timestamp creationDate = new Timestamp(new Date().getTime());
		String photoName = fileItems.get(8).getName();
		byte[] photo = null;

		if (!photoName.equals("")) {
			photo = fileItems.get(8).get();
//			FileOutputStream fos = new FileOutputStream("/home/juan/Escritorio/jaja.jpg");
//			fos.write(photo);
//			fos.close();
		}

		// If file size exceeds, a FileUploadException will be thrown
		// fu.setSizeMax(100000000);
		// try {
		// while(itr.hasNext()) {
		// FileItem fi = (FileItem)itr.next();

		// Check if not form field so as to only handle the file inputs
		// else condition handles the submit button input
		// if(!fi.isFormField()) {specified byte array starting
		// System.out.println("NAME: "+fi.getName());
		// System.out.println("SIZE: "+fi.getSize());
		// System.out.println(fi.getOutputStream().toString());
		// File fNew = new File(fi.getName());

		// System.out.println(fNew.getAbsolutePath());
		// fi.write(fNew);
		// DE ACA
		// FileInputStream fis = new FileInputStream(fNew);
		// PreparedStatement ps =
		// conn.prepareStatement("INSERT INTO images VALUES (?, ?)");
		// ps.setString(1, file.getName());
		// ps.setBinaryStream(2, fis, file.length());
		// ps.executeUpdate();
		// ps.close();
		// fis.close();
		// A ACA LA CONVERSION DE LA FOTO

		// }
		// }

		checkUsername(username, request);
		checkPassword(password, password2, request);
		checkName(name, request);
		checkSurname(surname, request);
		checkDescription(description, request);
		checkAnswer(answer, request);

		if (error) {
			error = false;
			request.getRequestDispatcher("WEB-INF/jsp/registration.jsp").forward(request, response);
		} else {
			userService.register(new User(name, surname, username, password,
					description, question, answer, creationDate, photo));
			request.getRequestDispatcher("index.jsp").forward(request, response);
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

	private void checkAnswer(String answer, HttpServletRequest request) {
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
	}
}