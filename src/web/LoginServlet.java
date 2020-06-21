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
		// 判断是不是游客
		String action = req.getParameter("action");
		if ("visit".equals(action)) {
			// 给游客一个用户名
			String userName = "游客" + (int) (Math.random() * 1000000);
			// 将用户名保存到session
			HttpSession session = req.getSession();
			session.setAttribute("userName", userName);
			// 重定向到WebChat.html
			resp.sendRedirect("WebChat.html");
			return;
		}

		// 登录信息是否正确的标识
		boolean flag = false;

		// 把输入赋值给user对象
		User User = new User();
		User.setEmail(req.getParameter("email"));
		User.setPassword(req.getParameter("password"));

		// 登录验证
		Check ck = new Check();
		try {
			flag = ck.checkLogin(User);
		} catch (Exception e) {
			e.printStackTrace();
		}

		PrintWriter out = resp.getWriter();
		// 返回结果
		if (true == flag) {
			// 将用户名保存到session
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
