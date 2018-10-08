package com.example.demo.component.mail;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;

import javax.annotation.Resource;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import com.example.demo.util.comm.DateUtil;
import com.example.demo.util.comm.StringUtils;

@Component
public class MailComponent {

	@Resource(name="logMailSender")
	private JavaMailSender mailSender;
	
	@Value("${mail.log.receiver}")
	private String logReceiverMail;
	
	@Value("${mail.log.sender.username}")
	private String logSenderMail;
	
	@Value("${app.name}")
	private String appName;
	
	@Value("${spring.profiles}")
	private String profiles;
	
	/**
	 * 将错误发送到开发者邮箱
	 * @param e
	 */
	public void mailErr(Throwable ex, String param) {
		try {
			String serverIp = "";
			try {
				serverIp = InetAddress.getLocalHost().getHostAddress().toString();
			} catch (UnknownHostException e1) {}
			
			if (StringUtils.isNotBlank(serverIp) && !serverIp.contains("127.")
					&& !serverIp.contains("192.") && !serverIp.contains("localhost")) 
			{
				String subject = "[".concat(profiles).concat("] <").concat(appName).concat(">").concat(" Error report");
				StringBuilder sb = new StringBuilder(); 
				sb.append("异常时间: "+ DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss") +"<br/>");
				sb.append("异常信息: " + ex.getMessage() + "<br/>");
				try {
					StackTraceElement[] stackTrace = ex.getStackTrace();
					for (StackTraceElement stackTraceElement : stackTrace) {
						String string = stackTraceElement.toString();
						//if (string.indexOf("xglory") >= 0) {
							sb.append(StringUtils.escapeHTMLTags(string) + "<br>");
						//}
					}
				} catch (Exception e) {}
				sb.append("参数：" + param);
				String text = sb.toString();
				
				new MimeMessageMailThread(mailSender, logSenderMail, logReceiverMail.split(","), subject, text)
				.start();
			}
		} catch (Exception e2) {
			
		}
	}
	
	/**
	 * 将错误发送到开发者邮箱
	 * @param e
	 */
	public void mailErr(Exception ex) {
		try {
			String serverIp = "";
			try {
				serverIp = InetAddress.getLocalHost().getHostAddress().toString();
			} catch (UnknownHostException e1) {}
			
			if (StringUtils.isNotBlank(serverIp) && !serverIp.contains("127.0.0.1")
					&& !serverIp.contains("192.168.") && !serverIp.contains("localhost")) 
			{
				String subject = "[".concat(profiles).concat("] <").concat(appName).concat(">").concat(" Error report");
				StringBuilder sb = new StringBuilder(); 
				sb.append("异常时间: "+ DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss") +"<br/>");
				sb.append("异常信息: " + ex.getMessage() + "<br/>");
				try {
					StackTraceElement[] stackTrace = ex.getStackTrace();
					for (StackTraceElement stackTraceElement : stackTrace) {
						String string = stackTraceElement.toString();
						//if (string.indexOf("xglory") >= 0) {
							sb.append(StringUtils.escapeHTMLTags(string) + "<br>");
						//}
					}
				} catch (Exception e) {}
				String text = sb.toString();
				
				new MimeMessageMailThread(mailSender, logSenderMail, logReceiverMail.split(","), subject, text)
				.start();
			}
		} catch (Exception e2) {
			
		}
	}
	
	public void mailErr(String text) {
		try {
			String serverIp = "";
			try {
				serverIp = InetAddress.getLocalHost().getHostAddress().toString();
			} catch (UnknownHostException e1) {}
			
			if (StringUtils.isNotBlank(serverIp) && !serverIp.contains("127.0.0.1")
					&& !serverIp.contains("192.168.") && !serverIp.contains("localhost")) 
			{
				String subject = "[".concat(profiles).concat("] <").concat(appName).concat(">").concat(" Error report");				
				new MimeMessageMailThread(mailSender, logSenderMail, logReceiverMail.split(","), subject, text)
				.start();
			}
		} catch (Exception e2) {
			
		}
	}
	
	public void mailErr(String title, String text) {
		try {
			String serverIp = "";
			try {
				serverIp = InetAddress.getLocalHost().getHostAddress().toString();
			} catch (UnknownHostException e1) {}
			
			if (StringUtils.isNotBlank(serverIp) && !serverIp.contains("127.0.0.1")
					&& !serverIp.contains("192.168.") && !serverIp.contains("localhost")) 
			{
				String subject = "[".concat(profiles).concat("] <").concat(appName).concat(">").concat(" Error report");
				subject = title + subject;
				new MimeMessageMailThread(mailSender, logSenderMail, logReceiverMail.split(","), subject, text)
				.start();
			}
		} catch (Exception e2) {
			
		}
	}
	
	/**
	 * 简单文本邮件线程
	 */
	private static class TextMailThread extends Thread{
		private static final Logger log = LoggerFactory.getLogger(TextMailThread.class);
		
		private JavaMailSender mailSender;
		private String from;
		private String[] to;
		private String subject;
		private String text;
		
		public TextMailThread(JavaMailSender mailSender, String from, String[] to, String subject, String text) {
			super();
			this.mailSender = mailSender;
			this.from = from;
			this.to = to;
			this.subject = subject;
			this.text = text;
		}

		public void run() {
			SimpleMailMessage message = new SimpleMailMessage();
			message.setFrom(from);
			message.setTo(to);
			message.setSubject(subject);
			message.setText(text);
			try {
				mailSender.send(message);
			}catch(Exception e) {
				log.error(e.getMessage(), e);
			}
			
		}
	}
	
	/**
	 * 多媒体信息邮件线程，支持html格式
	 */
	private static class MimeMessageMailThread extends Thread{
		private static final Logger log = LoggerFactory.getLogger(TextMailThread.class);
		
		private JavaMailSender mailSender;
		private String from;
		private String[] to;
		private String subject;
		private String text;
		
		public MimeMessageMailThread(JavaMailSender mailSender, String from, String[] to, String subject, String text) {
			super();
			this.mailSender = mailSender;
			this.from = from;
			this.to = to;
			this.subject = subject;
			this.text = text;
		}

		public void run() {
			try {
				MimeMessage mimeMessage = mailSender.createMimeMessage();
				
				MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
				helper.setFrom(from);
				helper.setTo(to);
				helper.setSubject(subject);
				helper.setText(text, true);
				
				mailSender.send(mimeMessage);
			}catch(Exception e) {
				log.error(e.getMessage(), e);
			}
		}
	}
	
	public static void main_(String[] args) {
		String str = null;
		try {
			System.out.println(str.charAt(0));
			//System.out.println(1/0);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println(e.fillInStackTrace().getMessage());
			e.printStackTrace();
			StringWriter sw = new StringWriter(); 
            e.printStackTrace(new PrintWriter(sw, true)); 
            String strs = sw.toString(); 
            System.out.println("--------------------------美丽的分割线----------------------"); 
            System.out.println(strs); 
		}
	}

}
