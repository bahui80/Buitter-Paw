package it.itba.edu.ar.repo;

import it.itba.edu.ar.model.User;

import java.util.List;
import java.util.Set;

public interface UserRepo {
	
	/**
	 * Decide si la información de logueo es correcta para un usuario.
	 */
	public boolean login(User user, String password);
	
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
