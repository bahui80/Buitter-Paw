package it.itba.edu.ar.servlets;

import it.itba.edu.ar.model.Buit;
import it.itba.edu.ar.model.User;
import it.itba.edu.ar.services.BuitService;
import it.itba.edu.ar.services.UserService;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class MyBuits extends BuitsHttpServlet {
	
	private UserService userService;
	private BuitService buitService;
	
	
	@Override
	public void init() throws ServletException {
		userService = UserService.sharedInstance();
		buitService = BuitService.sharedInstance();
	};
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String profile = request.getParameter("name");
		
		if(profile != null && userService.checkUsername(profile)) {
			User usr = userService.getUserByUsername(profile);
			List<Buit> buits = buitService.getUserBuits(usr);
			for(Buit buit: buits) {
				buit.setMessage(prepareBuit(buit.getMessage()));
			}
			if (usr.getPhoto() != null && usr.getPhoto().length > 0) {
				FileOutputStream fos = new FileOutputStream("src/main/webapp/img/photo.jpg");
				fos.write(usr.getPhoto());
				fos.close();
			}
			request.setAttribute("buits", buits);
			request.setAttribute("user_info", usr);
		} else {
			request.setAttribute("user_error", "That user doesn't exist");
		}
		request.getRequestDispatcher("WEB-INF/jsp/mybuits.jsp").forward(request, response);
	}
	
	
	//TODO: ACA NOSE QUE TOCAR BAHUI O JAVI, DSP FIJENSE
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String buit = request.getParameter("buit");
		List<String> hashTags;
		
		if(buit.equals("")) {
			request.setAttribute("error_buit", "Your buit is empty");
			response.sendRedirect("profile?name=" + request.getSession().getAttribute("user"));
			//request.getRequestDispatcher("WEB-INF/jsp/mybuits.jsp").forward(request, response);
		} else {
			hashTags = getHashTags(buit);
			for(String hash: hashTags) {
				//TODO llamar al servicio que me guarda los hashtags en la tabla si no existen
			}
			buitService.buit(new Buit(buit, userService.getUserByUsername((String)request.getSession().getAttribute("user")), new Date()));
			response.sendRedirect("profile?name=" + request.getSession().getAttribute("user"));
		}
	}
}
