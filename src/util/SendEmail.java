package util;

import java.util.Properties;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendEmail {

	/**
	 * 发送激活邮件
	 * 
	 * @param to
	 * @throws AddressException
	 * @throws MessagingException
	 */
	public void sendEmail(String to) throws AddressException, MessagingException {

		Properties properties = new Properties();

		// 开启debug调试
		properties.setProperty("mail.debug", "true");
		// 发送服务器需要身份验证
		properties.setProperty("mail.smtp.auth", "true");
		// 设置邮件服务器主机名
		properties.setProperty("mail.host", "smtp.163.com");
		// 发送邮件协议名称
		properties.setProperty("mail.transport.protocol", "smtp");

		// 设置发件人收件人
		String send = "邮箱账号";
		String password = "邮箱密码";
		String subject = "账号激活";

		Session session = Session.getInstance(properties);
		Message message = new MimeMessage(session);

		// 邮件标题
		message.setSubject(subject);
		StringBuilder builder = new StringBuilder();

		// 邮件内容
		builder.append("点击链接激活：http://localhost:8080/WebChat/activateUser?email=" + to);
		message.setText(builder.toString());
		message.setFrom(new InternetAddress(send));

		Transport transport = session.getTransport();
		transport.connect("smtp.163.com", send, password);

		transport.sendMessage(message, new Address[] { new InternetAddress(to) });
		transport.close();

	}

}
