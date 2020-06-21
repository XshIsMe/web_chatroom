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

		// 图片长宽
		int hight = 60;
		int width = 160;

		// 创建图片
		BufferedImage bufferedImage = new BufferedImage(width, hight, BufferedImage.TYPE_INT_RGB);

		// 设置背景色
		Graphics graphics = bufferedImage.getGraphics();
		graphics.setColor(Color.WHITE);
		graphics.fillRect(0, 0, width, hight);

		// 设置字体颜色
		Graphics2D graphics2d = (Graphics2D) bufferedImage.getGraphics();
		graphics2d.setColor(Color.GRAY);

		// 设置字体
		graphics2d.setFont(new Font("黑体", Font.PLAIN, 30));
		String content = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890";

		// 内容四个字 --- 随机从content中抽取四个字
		Random random = new Random();
		int x = 15;
		int y = 45;

		// 用来存放验证码
		StringBuffer str = new StringBuffer();

		// 循环四次
		for (int i = 0; i < 4; i++) {

			int index = random.nextInt(content.length());

			// letter 验证码内容
			char letter = content.charAt(index);

			// 将验证码内容存到str中
			str.append(content.charAt(index));

			// 将验证码添加到图片
			graphics2d.drawString(letter + "", x, y);

			x += 40;

		}

		// System.out.println(str);

		// 内存中资源 释放
		graphics.dispose();

		// 保存到session
		HttpSession session = req.getSession();
		session.setAttribute("idCode", str.toString());

		// 将图片输出到 浏览器 ImageIO
		// 将内存的图片 通过 浏览器输出流 写成 jpg格式图片
		ImageIO.write(bufferedImage, "jpg", resp.getOutputStream());
	}

}
