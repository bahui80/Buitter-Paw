package it.itba.edu.ar.web.validator;

import java.io.IOException;
import java.util.Date;

import it.itba.edu.ar.domain.user.User;

import org.springframework.web.multipart.MultipartFile;

public class UserForm {
	
	private String name;
	private String surname;
	private String username;
	private String password;
	private String password2;
	private String description;
	private String question;
	private String answer;
	private String privacy;
	private MultipartFile photo;
	
	public UserForm() {
		
	}
	
	public UserForm(User user) {
		setName(user.getName());
		setSurname(user.getSurname());
		setUsername(user.getUsername());
		setPassword(user.getPassword());
		setPassword2(user.getPassword());
		setDescription(user.getDescription());
		setQuestion(user.getSecretQuestion());
		setAnswer(user.getSecretAnswer());
		if(user.getPrivacy() == false) {
			setPrivacy("Public");
		} else {
			setPrivacy("Private");
		}
	}
	
	public MultipartFile getPhoto() {
		return photo;
	}

	public void setPhoto(MultipartFile photo) {
		this.photo = photo;
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

	public void setDescription(String description) {
		this.description = description;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getPassword2() {
		return password2;
	}

	public void setPassword2(String password2) {
		this.password2 = password2;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public String getPrivacy() {
		return privacy;
	}

	public void setPrivacy(String privacy) {
		this.privacy = privacy;
	}
	
	public User build() throws IOException {
		boolean b_privacy = false;
		byte[] b_photo = null;
		if(privacy.equals("Private")) {
			b_privacy = true;
		}
		if(photo.getSize() != 0) {
			b_photo = photo.getBytes();
		}
		return new User(name, surname, username, password, description, question, answer, new Date(), 0, b_privacy, b_photo);
	}
	
	public void update(User user) throws IOException {
		user.setDescription(description);
		user.setName(name);
		user.setSecretAnswer(answer);
		user.setSurname(surname);
		user.setPassword(password);
		user.setSecretQuestion(question);
		if(privacy.equals("Public")) {
			user.setPrivacy(false);
		} else {
			user.setPrivacy(true);
		}
		if(photo.getSize() != 0) {
			user.setPhoto(photo.getBytes());
		}
	}
}
