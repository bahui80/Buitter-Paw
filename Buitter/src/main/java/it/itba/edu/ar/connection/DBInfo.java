package it.itba.edu.ar.connection;


public class DBInfo {
	
	private static DBInfo instance;
	
	private String username;
	private String password;
	private String driver;
	private String connectionString;
	
	public static synchronized DBInfo sharedInstance(){
		if(instance == null){
			instance = new DBInfo();
		}
		return instance;
	}
	
	private DBInfo(){
		
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDriver() {
		return driver;
	}

	public void setDriver(String driver) {
		this.driver = driver;
	}

	public String getConnectionString() {
		return connectionString;
	}

	public void setConnectionString(String connectionString) {
		this.connectionString = connectionString;
	}
	

}
