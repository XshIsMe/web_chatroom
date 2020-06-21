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
	 * ���ͼ����ʼ�
	 * 
	 * @param to
	 * @throws AddressException
	 * @throws MessagingException
	 */
	public void sendEmail(String to) throws AddressException, MessagingException {

		Properties properties = new Properties();

		// ����debug����
		properties.setProperty("mail.debug", "true");
		// ���ͷ�������Ҫ�����֤
		properties.setProperty("mail.smtp.auth", "true");
		// �����ʼ�������������
		properties.setProperty("mail.host", "smtp.163.com");
		// �����ʼ�Э������
		properties.setProperty("mail.transport.protocol", "smtp");

		// ���÷������ռ���
		String send = "�����˺�";
		String password = "��������";
		String subject = "�˺ż���";

		Session session = Session.getInstance(properties);
		Message message = new MimeMessage(session);

		// �ʼ�����
		message.setSubject(subject);
		StringBuilder builder = new StringBuilder();

		// �ʼ�����
		builder.append("������Ӽ��http://localhost:8080/WebChat/activateUser?email=" + to);
		message.setText(builder.toString());
		message.setFrom(new InternetAddress(send));

		Transport transport = session.getTransport();
		transport.connect("smtp.163.com", send, password);

		transport.sendMessage(message, new Address[] { new InternetAddress(to) });
		transport.close();

	}

}
