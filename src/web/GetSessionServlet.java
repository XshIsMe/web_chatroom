package web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/getSession")
public class GetSessionServlet extends HttpServlet {

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
		String action = req.getParameter("action");
		HttpSession session = req.getSession();
		PrintWriter out = resp.getWriter();
		String info = null;
		if ("getUserName".equals(action)) {
			// 获取用户名
			info = (String) session.getAttribute("userName");
		} else if ("getIdCode".equals(action)) {
			// 获取验证码
			info = (String) session.getAttribute("idCode");
		}
		out.print(info);
		out.flush();
		out.close();
	}

}
