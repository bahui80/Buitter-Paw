package it.itba.edu.ar.web.users;

import it.itba.edu.ar.domain.user.UserRepo;

import java.util.Arrays;
import java.util.List;

import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.spring.injection.annot.SpringBean;

public class UserInfoPanel extends Panel {

	@SpringBean
	private UserRepo userRepo;
	
	public UserInfoPanel(String id) {
		super(id);
		add(new PasswordTextField("password").setRequired(true));
		add(new PasswordTextField("password2").setRequired(true));
		add(new TextField<String>("name").setRequired(true));
		add(new TextField<String>("surname").setRequired(true));
		add(new TextArea<String>("description").setRequired(true));
		
		List<String> questions = Arrays.asList(new String[] {"What is the name of your dog?", "Who was your favourite teacher?", "Where do you live?", "Do you hate Twitter?", "What is your aunts name?"});
		add(new DropDownChoice<String>("secretQuestion", questions));
		add(new TextField<String>("secretAnswer").setRequired(true));
		add(new DropDownChoice<String>("privacy", Arrays.asList(new String[] {"Public", "Private"})));
		
	}

}
