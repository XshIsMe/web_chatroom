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

@WebServlet("/signUp")
public class SignUpServlet extends HttpServlet {

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
		// 登录信息是否正确的标识
		boolean flag = false;

		// 把输入赋值给user对象
		User User = new User();
		User.setEmail(req.getParameter("email"));
		User.setUserName(req.getParameter("userName"));
		User.setPassword(req.getParameter("password"));
		// String password2=req.getParameter("password2");
		User.setIdcard(req.getParameter("idCard"));
		User.setName(req.getParameter("name"));

		// 注册验证
		Check ck = new Check();
		try {
			flag = ck.checkSignUp(User);
		} catch (Exception e) {
			e.printStackTrace();
		}

		PrintWriter out=resp.getWriter();
		// 返回结果
		if (true == flag) {
			out.print("true");
		} else {
			out.print("false");
		}
		out.flush();
		out.close();
	}

}
