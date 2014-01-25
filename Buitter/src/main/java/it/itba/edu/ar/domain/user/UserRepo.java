package it.itba.edu.ar.domain.user;


import java.util.List;

public interface UserRepo {
	
	/**
	 * Busca usuarios cuyo nombre, apellido o usuario se corresponda con la consulta.
	 */
	public List<User> getAllUsersMatching(String consult);
		
	/**
	 * Realiza la búsqueda de un usuario.
	 */
	public List<User> search(String consult);
	
	/**
	 * Obtiene un usuario con un determinado username.
	 */
	public User get(String username);
	
	/**
	 * Obtiene la lista de todos las usuarios.
	 */
	public List<User> getAll();
	
	/**
	 * Almacena un nuevo usuario.
	 */
	public void add(User user);
	
	/**
	 * Sugiere usuarios a seguir
	 */
	public List<User> whoToFollow(User u);
	
}
