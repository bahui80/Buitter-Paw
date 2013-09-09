package it.itba.edu.ar.connection;

@SuppressWarnings("serial")
public class DatabaseException extends RuntimeException {
	public DatabaseException(String message, Throwable cause) {
		super(message, cause);
	}
}
