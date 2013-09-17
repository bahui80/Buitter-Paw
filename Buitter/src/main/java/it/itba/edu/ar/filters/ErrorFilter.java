package it.itba.edu.ar.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ErrorFilter implements Filter {

	public void init(FilterConfig filterConfig) throws ServletException {
		// Do nothing
	}

	public void destroy() {
		// Do nothing
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		try {
			chain.doFilter(request, response);
		} catch (RuntimeException e) {
			((HttpServletRequest) request).getRequestDispatcher("WEB-INF/jsp/error.jsp").forward(request, response);
		}
	}
}