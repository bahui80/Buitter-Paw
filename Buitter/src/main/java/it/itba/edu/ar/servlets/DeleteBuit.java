package it.itba.edu.ar.servlets;

import it.itba.edu.ar.services.BuitService;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@SuppressWarnings("serial")
public class DeleteBuit extends HttpServlet {
	private BuitService buitService;
	
	@Override
	public void init() throws ServletException {
		buitService = BuitService.sharedInstance();
	};
	
	//TODO corregir que esta mal como se borra
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String buitId = req.getParameter("buitid");
		buitService.removeBuit(Integer.valueOf(buitId));
		resp.sendRedirect("profile?name=" + req.getSession().getAttribute("user"));
		return;
	}
}
