package web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.User;
import service.Check;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

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
		// �ж��ǲ����ο�
		String action = req.getParameter("action");
		if ("visit".equals(action)) {
			// ���ο�һ���û���
			String userName = "�ο�" + (int) (Math.random() * 1000000);
			// ���û������浽session
			HttpSession session = req.getSession();
			session.setAttribute("userName", userName);
			// �ض���WebChat.html
			resp.sendRedirect("WebChat.html");
			return;
		}

		// ��¼��Ϣ�Ƿ���ȷ�ı�ʶ
		boolean flag = false;

		// �����븳ֵ��user����
		User User = new User();
		User.setEmail(req.getParameter("email"));
		User.setPassword(req.getParameter("password"));

		// ��¼��֤
		Check ck = new Check();
		try {
			flag = ck.checkLogin(User);
		} catch (Exception e) {
			e.printStackTrace();
		}

		PrintWriter out = resp.getWriter();
		// ���ؽ��
		if (true == flag) {
			// ���û������浽session
			HttpSession session = req.getSession();
			session.setAttribute("userName", User.getUserName());
			out.print("true");
		} else {
			out.print("false");
		}
		out.flush();
		out.close();
	}

}
