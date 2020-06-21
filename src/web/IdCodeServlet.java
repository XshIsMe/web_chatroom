package web;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/idCode")

public class IdCodeServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		// ͼƬ����
		int hight = 60;
		int width = 160;

		// ����ͼƬ
		BufferedImage bufferedImage = new BufferedImage(width, hight, BufferedImage.TYPE_INT_RGB);

		// ���ñ���ɫ
		Graphics graphics = bufferedImage.getGraphics();
		graphics.setColor(Color.WHITE);
		graphics.fillRect(0, 0, width, hight);

		// ����������ɫ
		Graphics2D graphics2d = (Graphics2D) bufferedImage.getGraphics();
		graphics2d.setColor(Color.GRAY);

		// ��������
		graphics2d.setFont(new Font("����", Font.PLAIN, 30));
		String content = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890";

		// �����ĸ��� --- �����content�г�ȡ�ĸ���
		Random random = new Random();
		int x = 15;
		int y = 45;

		// ���������֤��
		StringBuffer str = new StringBuffer();

		// ѭ���Ĵ�
		for (int i = 0; i < 4; i++) {

			int index = random.nextInt(content.length());

			// letter ��֤������
			char letter = content.charAt(index);

			// ����֤�����ݴ浽str��
			str.append(content.charAt(index));

			// ����֤����ӵ�ͼƬ
			graphics2d.drawString(letter + "", x, y);

			x += 40;

		}

		// System.out.println(str);

		// �ڴ�����Դ �ͷ�
		graphics.dispose();

		// ���浽session
		HttpSession session = req.getSession();
		session.setAttribute("idCode", str.toString());

		// ��ͼƬ����� ����� ImageIO
		// ���ڴ��ͼƬ ͨ�� ���������� д�� jpg��ʽͼƬ
		ImageIO.write(bufferedImage, "jpg", resp.getOutputStream());
	}

}
