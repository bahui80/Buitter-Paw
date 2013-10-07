package it.itba.edu.ar.servlets;

import it.itba.edu.ar.services.BuitServiceImpl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@SuppressWarnings("serial")
public class DeleteBuit extends HttpServlet {
	private BuitServiceImpl buitService;
	
	@Override
	public void init() throws ServletException {
		buitService = BuitServiceImpl.sharedInstance();
	};
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.getRequestDispatcher("WEB-INF/jsp/error.jsp").forward(req, resp);
	}
	
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
