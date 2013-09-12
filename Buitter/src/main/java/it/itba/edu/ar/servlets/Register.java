package it.itba.edu.ar.servlets;

import it.itba.edu.ar.model.User;
import it.itba.edu.ar.services.UserService;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;

@SuppressWarnings("serial")
public class Register extends HttpServlet {
	private Boolean error = false;
		
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("WEB-INF/jsp/registration.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String password2 = request.getParameter("password2");
		String name = request.getParameter("name");
		String surname = request.getParameter("surname");
		String description = request.getParameter("description");
		String question = request.getParameter("question");
		String answer = request.getParameter("answer");
		Date creationDate = new Date();
		
	/*	DiskFileUpload fu = new DiskFileUpload();
        // If file size exceeds, a FileUploadException will be thrown
//      fu.setSizeMax(100000000);
		
		List fileItems;
		try {
			fileItems = fu.parseRequest(request);
			Iterator itr = fileItems.iterator();

	        while(itr.hasNext()) {
	        	FileItem fi = (FileItem)itr.next();

	        	//Check if not form field so as to only handle the file inputs
	        	//else condition handles the submit button input
	        	if(!fi.isFormField()) {
	        		System.out.println("NAME: "+fi.getName());
	        		System.out.println("SIZE: "+fi.getSize());
	        		//System.out.println(fi.getOutputStream().toString());
	        		File fNew = new File(fi.getName());

	        		System.out.println(fNew.getAbsolutePath());
	        		fi.write(fNew);
	        	}
	        }
		} catch (Exception e) {
			e.printStackTrace();
		} */
		
		checkUsername(username, request);
		checkPassword(password, password2, request);
		checkName(name, request);
		checkSurname(surname, request);
		checkDescription(description, request);
		checkAnswer(answer, request);
		
		
		if(error) {
			request.getRequestDispatcher("WEB-INF/jsp/registration.jsp").forward(request, response);
		} else {
			UserService.register(new User(name, surname, username, password, description, question, answer, creationDate, "provisorio. Aca va la foto"));
		}
        
      }
	
	private void checkUsername(String username, HttpServletRequest request) {
		if(username != null) {
			if(username.length() <= 32) {
				if(UserService.checkUsername(username)) {
					request.setAttribute("error_username", "The username already exists");
					error = true;
				}
			} else {
				request.setAttribute("error_username", "The username must contain up to 32 characters");
				error = true;
			}
		} else {
			request.setAttribute("error_username", "You must insert an username");
			error = true;
		}
	}
	
	private void checkPassword(String password, String password2, HttpServletRequest request) {
		if(password != null) {
			if(password.length() > 32) {
				request.setAttribute("error_password", "The password must contain up to 32 characters");
				error = true;
			} else if(!password2.equals("") && !password2.equals(password)) {
				request.setAttribute("error_password2", "The passwords must match");
				error = true;
			}
		} else {
			request.setAttribute("error_password", "You must insert a password");
			error = true;
		}
	}
	
	private void checkName(String name, HttpServletRequest request) {
		if(name != null) {
			if(name.length() > 32) {
				request.setAttribute("error_name", "The name must contain up to 32 characters");
				error = true;
			}
		} else {
			request.setAttribute("error_name", "You must insert a name");
			error = true;
		}
	}
	
	private void checkSurname(String surname, HttpServletRequest request) {
		if(surname != null) {
			if(surname.length() > 32) {
				request.setAttribute("error_surname", "The surname must contain up to 32 characters");
				error = true;
			}
		} else {
			request.setAttribute("error_surname", "You must insert a surname");
			error = true;
		}
	}
	
	private void checkDescription(String description, HttpServletRequest request) {
		if(description != null) {
			if(description.length() > 140) {
				request.setAttribute("error_description", "The description must contain up to 140 characters");
				error = true;
			}
		} else {
			request.setAttribute("error_description", "You must insert a description");
			error = true;
		}
	}
	
	private void checkAnswer(String answer, HttpServletRequest request) {
		if(answer != null) {
			if(answer.length() > 60) {
				request.setAttribute("error_answer", "The answer must contain up to 60 characters");
				error = true;
			}
		} else {
			request.setAttribute("error_answer", "You must insert a secret answer");
			error = true;
		}
	}
}