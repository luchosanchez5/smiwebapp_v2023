package com.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;


@Component
public class EmailServiceImpl implements EmailService {
	
	@Autowired
    public JavaMailSender emailSender;
	
	
	
	@Override
	public void sendSimpleMessage(String to, String subject, String text) {
		// TODO Auto-generated method stub
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(to);
            message.setSubject(subject);
            message.setText(text);

            emailSender.send(message);
        } catch (MailException exception) {
            exception.printStackTrace();
        }
		
	}

	@Override
	public void sendSimpleMessageUsingTemplate(String to, String subject, SimpleMailMessage template,
			String... templateArgs) {
		// TODO Auto-generated method stub
        String text = String.format(template.getText(), templateArgs);  
        sendSimpleMessage(to, subject, text);
	}

	@Override
	public void sendMessageWithAttachment(String to, String subject, String text, String pathToAttachment) {
		// TODO Auto-generated method stub
        try {
            MimeMessage message = emailSender.createMimeMessage();
            // pass 'true' to the constructor to create a multipart message
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text);

            FileSystemResource file = new FileSystemResource(new File(pathToAttachment));
            helper.addAttachment("Invoice", file);

            emailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
	
	
	
	@Override
	public void sendHTMlEmail (String to, String subject, String text) throws IOException{
		

	      
	      Resource resource = new ClassPathResource("/application.properties");
	      Properties pro = PropertiesLoaderUtils.loadProperties(resource);
	      
			Properties props = new Properties();
		      props.put("mail.smtp.auth", "true");
		      props.put("mail.smtp.starttls.enable", "true");
		      props.put("mail.smtp.host",pro.getProperty("spring.mail.host"));
		      props.put("mail.smtp.port", pro.getProperty("spring.mail.port"));	 
		      props.put("mail.smtp.ssl.trust", "*");

	      // Get the Session object.
	      Session session = Session.getInstance(props,
	         new javax.mail.Authenticator() {
	            protected PasswordAuthentication getPasswordAuthentication() {
	               return new PasswordAuthentication(pro.getProperty("spring.mail.username"), pro.getProperty("spring.mail.password"));
	            }
		});

	      try {
	            // Create a default MimeMessage object.
	      Message message = new MimeMessage(session);

	   	   // Set From: header field of the header.
		   message.setFrom(new InternetAddress(pro.getProperty("spring.mail.username")));

		   // Set To: header field of the header.
		   message.setRecipients(Message.RecipientType.TO,
	              InternetAddress.parse(to));

		   // Set Subject: header field
		   message.setSubject(subject);

		   // Send the actual HTML message, as big as you like
		   message.setContent(
	              text,
	             "text/html");

		   // Send message
		   Transport.send(message);

		   System.out.println("Sent message successfully....");

	      } catch (MessagingException e) {
		   e.printStackTrace();
		   throw new RuntimeException(e);
	      }
	}	

}
