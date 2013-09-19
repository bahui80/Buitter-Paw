package it.itba.edu.ar.connection;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ServletListener implements ServletContextListener {

	public void contextInitialized(ServletContextEvent sce) {
		ServletContext sc = sce.getServletContext();
		 
    	String driver = sc.getInitParameter("driver");
    	String connectionString = sc.getInitParameter("connectionString");
    	String password = sc.getInitParameter("password");
    	String username = sc.getInitParameter("username");	
    	
    	DBInfo info = DBInfo.sharedInstance();
    	info.setConnectionString(connectionString);
    	info.setDriver(driver);
    	info.setPassword(password);
    	info.setUsername(username);

	}

	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		
	}

}
