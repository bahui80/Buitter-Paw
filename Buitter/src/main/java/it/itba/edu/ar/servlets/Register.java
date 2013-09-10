package it.itba.edu.ar.servlets;

import it.itba.edu.ar.dao.UserDao;
import it.itba.edu.ar.dao.UserManager;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class Register extends HttpServlet {
	private UserDao manager = UserManager.sharedInstance();
		
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
		
		if(username != null) {
			if(username.length() > 8 && username.length() < 12) {
				if(manager.checkUserName(username)) {
					request.setAttribute("N", );
				}
			} else {
				request.setAttribute("error_username", "El nombre de usuario debe contener entre 8 y 12 caracteres");
			}
		} else {
			request.setAttribute("error_username", "Debe ingresar un nombre de usuario");
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
}
