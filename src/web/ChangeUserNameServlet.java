package web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import service.ChangeUserName;

@WebServlet("/changeUserName")
public class ChangeUserNameServlet extends HttpServlet {

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
		String oldUserName = req.getParameter("oldUserName");
		String newUserName = req.getParameter("newUserName");
		ChangeUserName cun = new ChangeUserName();
		String info = null;
		try {
			info = cun.changeUserName(oldUserName, newUserName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if ("修改成功".equals(info)) {
			// 将用户名保存到session
			HttpSession session = req.getSession();
			session.setAttribute("userName", newUserName);
		}
		PrintWriter out = resp.getWriter();
		out.print(info);
		out.flush();
		out.close();
	}

}
