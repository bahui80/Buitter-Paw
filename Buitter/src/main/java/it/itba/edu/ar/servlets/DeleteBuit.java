package it.itba.edu.ar.servlets;

import it.itba.edu.ar.dao.BuitManager;
import it.itba.edu.ar.services.BuitService;
import it.itba.edu.ar.services.UserService;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@SuppressWarnings("serial")
public class DeleteBuit extends HttpServlet {
	private UserService userService;
	private BuitService buitService;
	private BuitManager manager = BuitManager.sharedInstance();
	
	@Override
	public void init() throws ServletException {
		userService = UserService.sharedInstance();
		buitService = BuitService.sharedInstance();
	};
	//TODO corregir que esta mal como se borra
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String buitId = req.getParameter("buitid");
		manager.removeBuit(Integer.valueOf(buitId));
		resp.sendRedirect("profile?name=" + req.getSession().getAttribute("user"));
	}
}