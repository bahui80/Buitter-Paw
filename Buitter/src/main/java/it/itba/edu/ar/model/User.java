package it.itba.edu.ar.model;

import java.util.Date;

public class User {
	
	private Integer id;
	private String name;
	private String surname;
	private String username;
	private String password;
	private String description;
	private String secret_question;
	private String secret_answer;
	private Date creationDate;
	private String photo; // TODO ver que tipo

	public User(String username, String password){
		this.username = username;
		this.password = password;
	}
	
	public User(String name, String surname, String username, String password, 
			String description, String secret_question, String secret_answer, 
			Date creationDate, String photo){
		this.name = name;
		this.username = username;	
		this.surname = surname;
		this.password = password;
		this.description = description;
		this.secret_answer = secret_answer;
		this.secret_question = secret_question;
		this.creationDate = creationDate;
		//this.photo = photo;
	}
	
	public User(int id, String name, String surname, String username, String password, 
			String description, String secret_question, String secret_answer, 
			Date creationDate, String photo){
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
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
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
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String desc) {
		this.description = desc;
	}
	
	public String getSecretQuestion() {
		return secret_question;
	}

	public void setSecretQuestion(String s_question) {
		this.secret_question = s_question;
	}
	
	public String getSecretAnswer() {
		return secret_answer;
	}

	public void setSecretAnswer(String s_answer) {
		this.secret_answer = s_answer;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}
}
