package com.meeting.service.Impl;

import java.io.File;
import java.util.Date;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;


import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.meeting.service.MailService;

@Service
public class MailServiceImpl implements MailService{
    @Resource
	private JavaMailSender mailSender;
    @Resource
	private SimpleMailMessage mailMessage;
	@Override
	public int sendSimpleMail(String subject, String content, String toMail) {
		mailMessage.setSubject(subject);
		mailMessage.setText(content);
		mailMessage.setTo(toMail);
    	mailSender.send(mailMessage);
        mailMessage.setSentDate(new Date());
    	return 1;
		
	}
	@Override
	public int sendPictureMail(String subject, String content, String toMail, String picturePath) throws MessagingException {
		MimeMessage mailMessage = mailSender.createMimeMessage();
		MimeMessageHelper messageHelper=null;
		messageHelper = new MimeMessageHelper(mailMessage,true);
		messageHelper.setTo(toMail);
		messageHelper.setSubject(subject); 
		messageHelper.setFrom("1741022905@qq.com");
		messageHelper.setText("<html><head></head><body><h1></h1>"+"<img src=\"cid:aaa\"/></body></html>",true); 
		FileSystemResource img = new FileSystemResource(new File(picturePath)); 
		messageHelper.addInline("aaa",img); 
		mailSender.send(mailMessage);
		return 1;
	}
	@Override
	public int sendMailTakeAccessory(String subject, String content, String toMail, String accessoryPath,
			String accessoryName) throws MessagingException {
			MimeMessage mailMessage = mailSender.createMimeMessage(); 
	        MimeMessageHelper messageHelper;
			messageHelper = new MimeMessageHelper(mailMessage,true,"utf-8");
		    messageHelper.setTo(toMail);
			messageHelper.setSubject(subject); 
		    messageHelper.setText("<html><head></head><body><h1>"+content+"</h1></body></html>",true); 
		    FileSystemResource file = new FileSystemResource(new File(accessoryPath)); 
		    messageHelper.addAttachment(accessoryName,file);
		    mailSender.send(mailMessage); 
		return 1;
	}
	
	
	public int sendMailHTML(String subject, int id,String toMail) throws MessagingException {
			MimeMessage mailMessage = mailSender.createMimeMessage(); 
	        MimeMessageHelper messageHelper;
			messageHelper = new MimeMessageHelper(mailMessage,true,"utf-8");
		    messageHelper.setTo(toMail);
		    messageHelper.setFrom("1741022905@qq.com");
			messageHelper.setSubject(subject);
		   // messageHelper.setText("<img src=http://localhost:8080/meetings/meeting/attend.do?ids="+id+">",true); 
			messageHelper.setText("<img src=D:\\002.jpg>",true); 
		    mailSender.send(mailMessage); 
		
		    return 1;
	}

}