package it.itba.edu.ar.servlets;

import it.itba.edu.ar.dao.BuitManager;
import it.itba.edu.ar.model.Buit;
import it.itba.edu.ar.model.User;
import it.itba.edu.ar.services.BuitService;
import it.itba.edu.ar.services.UserService;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class MyBuits extends HttpServlet	 {
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String profile = request.getParameter("name");
		BuitManager manager = BuitManager.sharedInstance(); //TODO cambiar por el servicio
		
		if(profile != null && UserService.checkUsername(profile)){
			List<Buit> buits = manager.getUserBuits(profile);
			request.setAttribute("buits", buits);
		} else {
			request.setAttribute("user_error", "That user doesn't exist");
		}
		request.getRequestDispatcher("WEB-INF/jsp/mybuits.jsp").forward(request, response);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String buit = request.getParameter("buit");
		
		if(buit.equals("")) {
			request.setAttribute("error_buit", "Your buit is empty");
		} else {
			buit = BuitService.parseBuit(buit);
			BuitService.buit(new Buit(buit, ((User)request.getSession().getAttribute("user")).getUsername(), new Date()), (User) request.getSession().getAttribute("user"));
			//TODO PARSEAR BUIT Y GUARDARLO
		}
	}
}
