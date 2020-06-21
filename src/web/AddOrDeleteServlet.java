package web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.AddOrDelete;

@WebServlet("/addOrDelete")
public class AddOrDeleteServlet extends HttpServlet {

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
		String result = null;
		String action = req.getParameter("action");
		AddOrDelete aod = new AddOrDelete();
		try {
			if ("addFriend".equals(action)) {
				result = aod.addFriend(req.getParameter("userName"), req.getParameter("friendName"));
			} else if ("deleteFriend".equals(action)) {
				result = aod.deleteFriend(req.getParameter("userName"), req.getParameter("friendName"));
			} else if ("addGroup".equals(action)) {
				result = aod.addGroup(req.getParameter("userName"), req.getParameter("groupName"));
			} else if ("deleteGroup".equals(action)) {
				result = aod.deleteGroup(req.getParameter("userName"), req.getParameter("groupName"));
			} else if ("inviteFriend".equals(action)) {
				result = aod.inviteFriend(req.getParameter("userName"), req.getParameter("friendName"),
						req.getParameter("groupName"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		PrintWriter out = resp.getWriter();
		out.print(result);
		out.flush();
		out.close();
	}

}
