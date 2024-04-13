package com.ashokit.utils;


import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
@Component
public class EmailUtils {
	@Autowired
	private JavaMailSender emailSender;

	public boolean sendEmail(String to,
			String subject, 
			String text) {
		boolean isSent = false;
		try {
			MimeMessage message = emailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message);

			helper.setFrom("k.ravali034@gmail.com");
			helper.setTo(to);
			helper.setSubject(subject);
			helper.setText(text,true);


			emailSender.send(message);
			System.out.println("Mail sent successfully");

		}catch(Exception e){
			e.printStackTrace();

		}
		return isSent;
	}

}
