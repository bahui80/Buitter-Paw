package it.itba.edu.ar.extra;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendMail {

	public static boolean send(String msg) {
		try {
			final Properties props = ReadProperties.loadXML("mail_configuration.xml");

			Session session = Session.getInstance(props, new javax.mail.Authenticator() {
						protected PasswordAuthentication getPasswordAuthentication() {
							return new PasswordAuthentication(props.getProperty("user"), props.getProperty("pass"));
						}
					});

			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(props.getProperty("user")));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(props.getProperty("to")));
			message.setSubject(props.getProperty("subject"));
			message.setText(msg);

			Transport.send(message);

		} catch (Exception e) {
			return false;
		}
		return true;
	}
}