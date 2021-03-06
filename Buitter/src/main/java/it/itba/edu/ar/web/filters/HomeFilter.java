package it.itba.edu.ar.web.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HomeFilter implements Filter {

	public void init(FilterConfig filterConfig) throws ServletException {
		// Do nothing...
		
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		
		if(req.getRequestURI().toString().equals("/Buitter/"))
			resp.sendRedirect("/Buitter/web");
		else
			chain.doFilter(req, resp);
	}

	public void destroy() {
		// Do nothing..
		
	}

}
