package it.itba.edu.ar.servlets;

import it.itba.edu.ar.model.User;
import it.itba.edu.ar.services.UserService;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
		String question = request.getParameter("select");
		String answer = request.getParameter("answer");
		Date creationDate = new Date();
		
		checkUsername(username, request);
		checkPassword(password, password2, request);
		checkName(name, request);
		
		if(error) {
			
		} else {
			UserService.register(new User(name, surname, username, password, description, question, answer, creationDate, "provisorio. Aca va la foto"));
		}
/*		DiskFileUpload fu = new DiskFileUpload();
        // If file size exceeds, a FileUploadException will be thrown
//        fu.setSizeMax(100000000);

        List fileItems;
		try {
			fileItems = fu.parseRequest(request);
			
			Iterator itr = fileItems.iterator();

	        while(itr.hasNext()) {
	          FileItem fi = (FileItem)itr.next();

	          //Check if not form field so as to only handle the file inputs
	          //else condition handles the submit button input
	          if(!fi.isFormField()) {
	            System.out.println("nNAME: "+fi.getName());
	            System.out.println("SIZE: "+fi.getSize());
	            //System.out.println(fi.getOutputStream().toString());
	            File fNew= new File(fi.getName());

	            System.out.println(fNew.getAbsolutePath());
	            fi.write(fNew);
	          }
	          else {
	            System.out.println("Field ="+fi.getFieldName());
	          }
		} }catch (FileUploadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
        
      }
	
	private void checkUsername(String username, HttpServletRequest request) {
		if(username != null) {
			if(username.length() >= 6 && username.length() <= 12) {
				if(UserService.checkUsername(username)) {
					request.setAttribute("error_username", "The username already exists");
					error = true;
				}
			} else {
				request.setAttribute("error_username", "The username must contain betwenn 6 to 12 characters");
				error = true;
			}
		} else {
			request.setAttribute("error_username", "You must insert a username");
			error = true;
		}
	}
	
	private void checkPassword(String password, String password2, HttpServletRequest request) {
		if(password != null) {
			if(password.length() >= 6 && password.length() <= 12) {
				request.setAttribute("error_password", "The password must contain between 6 to 12 characters");
				error = true;
			} else if(password2 != null && !password2.equals(password)) {
				request.setAttribute("error_password2", "The passwords must match");
				error = true;
			}
		} else {
			request.setAttribute("error_password", "You must insert a password");
			error = true;
		}
	}
	
	private void checkName(String name, HttpServletRequest request) {
		
	}
}
