package web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.User;
import service.Check;

@WebServlet("/forget")
public class ForgetServlet extends HttpServlet {

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
		// �޸�������Ϣ�Ƿ���ȷ�ı�ʶ
		boolean flag = false;

		// �����븳ֵ��user����
		User User = new User();
		User.setEmail(req.getParameter("email"));
		User.setIdcard(req.getParameter("idCard"));
		User.setPassword(req.getParameter("password"));

		// �޸�������֤
		Check ck = new Check();
		try {
			flag = ck.checkForget(User);
		} catch (Exception e) {
			e.printStackTrace();
		}

		PrintWriter out = resp.getWriter();
		// ���ؽ��
		if (true == flag) {
			out.print("true");
		} else {
			out.print("false");
		}
		out.flush();
		out.close();
	}

}
