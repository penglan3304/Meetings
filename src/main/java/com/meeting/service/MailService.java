package com.meeting.service;
import javax.mail.MessagingException;


public interface MailService {
	/**
	 * 
	 * @param subject 邮件主题
	 * @param content 邮件内容
	 * @param toMail  收件人邮箱
	 * @return
	 */
	public int sendSimpleMail(String subject,String content,String toMail);
	/**
	 * 
	 * @param subject 邮件主题
	 * @param content 邮件内容
	 * @param toMail  收件人邮箱
	 * @param picturePath 图片地址
	 * @return
	 */
	public int sendPictureMail(String subject,String content,String toMail,String picturePath) throws MessagingException;
	
	/**
	 * 
	 * @param subject  邮件主题
	 * @param content  邮件内容
	 * @param toMail   收件人邮箱
	 * @param accessoryPath  附件路径
	 * @param accessoryName  附件名
	 * @return
	 * @throws MessagingException
	 */
	public int sendMailTakeAccessory(String subject,String content,
			String toMail,String accessoryPath,String accessoryName) throws MessagingException;
	
	
	public int sendMailHTML(String subject,int id,String toMail) throws MessagingException;
}

