package it.itba.edu.ar.model;

import java.sql.Timestamp;

public class User {
	
	private Integer id;
	private String name;
	private String surname;
	private String username;
	private String password;
	private String description;
	private String secret_question;
	private String secret_answer;
	private Timestamp creationDate;
	private byte[] photo; // TODO ver que tipo

	public User(String username, String password){
		if(username == null || username.length() > 32 || password == null || password.length() > 32)
			throw new IllegalArgumentException();
		this.username = username;
		this.password = password;
	}
	
	//TODO FALTA VER DE QUE TIPO VA LA FOTO
	
	public User(String name, String surname, String username, String password, 
			String description, String secret_question, String secret_answer, 
			Timestamp creationDate, byte[] photo){
		if(username == null || username.length() > 32 || password == null || password.length() > 32 
				|| description == null || description.length() > 140 || secret_question == null 
				|| secret_question.length() > 60 || secret_answer == null 
				|| secret_answer.length() > 60 || creationDate == null)
			throw new IllegalArgumentException();
		
		this.name = name;
		this.username = username;	
		this.surname = surname;
		this.password = password;
		this.description = description;
		this.secret_answer = secret_answer;
		this.secret_question = secret_question;
		this.creationDate = creationDate;
		this.photo = photo;
	}
	
	public User(int id, String name, String surname, String username, String password, 
			String description, String secret_question, String secret_answer, 
			Timestamp creationDate, byte[] photo){
		if(id == 0 || username == null || username.length() > 32 || password == null || password.length() > 32 
				|| description == null || description.length() > 140 || secret_question == null 
				|| secret_question.length() > 60 || secret_answer == null 
				|| secret_answer.length() > 60 || creationDate == null)
			throw new IllegalArgumentException();
		
		this.id = id;
		this.name = name;
		this.username = username;	
		this.surname = surname;
		this.password = password;
		this.description = description;
		this.secret_answer = secret_answer;
		this.secret_question = secret_question;
		this.creationDate = creationDate;
		this.photo = photo;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		if(id == 0)
			throw new IllegalArgumentException();
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		if(name == null || name.length() > 32)
			throw new IllegalArgumentException();
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		if(surname == null || surname.length() > 32)
			throw new IllegalArgumentException();
		this.surname = surname;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		if(username == null || username.length() > 32)
			throw new IllegalArgumentException();
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		if(password == null || password.length() > 32)
			throw new IllegalArgumentException();
		this.password = password;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String desc) {
		if(desc == null || desc.length() > 140)
			throw new IllegalArgumentException();
		this.description = desc;
	}
	
	public String getSecretQuestion() {
		return secret_question;
	}

	public void setSecretQuestion(String s_question) {
		if(s_question == null || s_question.length() > 60)
			throw new IllegalArgumentException();
		this.secret_question = s_question;
	}
	
	public String getSecretAnswer() {
		return secret_answer;
	}

	public void setSecretAnswer(String s_answer) {
		if(s_answer == null || s_answer.length() > 60)
			throw new IllegalArgumentException();
		this.secret_answer = s_answer;
	}

	public Timestamp getCreationDate() {
		return creationDate;
	}

	public byte[] getPhoto() {
		return photo;
	}

	public void setPhoto(byte[] photo) {
		if(photo == null) {
			throw new IllegalArgumentException();
		}
		this.photo = photo;
	}
}
