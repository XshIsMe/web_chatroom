package web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import service.GetList;

@WebServlet("/getList")
public class GetListServlet extends HttpServlet {

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
		// ��ȡ����
		String action = req.getParameter("action");
		// ��ȡ�û���
		String userName = req.getParameter("username");
		// ��ȡ���ѻ�Ⱥ�б�
		JSONArray jsonArray = null;
		if ("getFriendList".equals(action)) {
			jsonArray = this.getFriendList(userName);
		} else if ("getGroupList".equals(action)) {
			jsonArray = this.getGroupList(userName);
		}
		// ���json
		PrintWriter out = resp.getWriter();
		out.print(jsonArray);
		out.flush();
		out.close();
	}

	/**
	 * ��ȡ�����б�
	 * 
	 * @param userName
	 * @return
	 */
	public JSONArray getFriendList(String userName) {
		GetList gl = new GetList();
		// ��ȡ�����б�
		ArrayList<String> friendList = null;
		try {
			friendList = gl.getFriendList(userName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// �½�json����
		JSONArray jsonArray = new JSONArray();
		// ���������б�
		for (int i = 0; i < friendList.size(); i++) {
			JSONObject resultJson = new JSONObject();
			resultJson.put("friendName", friendList.get(i));
			jsonArray.add(resultJson);
		}
		return jsonArray;
	}

	/**
	 * ��ȡȺ�б�
	 * 
	 * @param userName
	 * @return
	 */
	public JSONArray getGroupList(String userName) {
		GetList gl = new GetList();
		// ��ȡȺ�б�
		ArrayList<String> groupList = null;
		try {
			groupList = gl.getGroupList(userName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// �½�json����
		JSONArray jsonArray = new JSONArray();
		// ����Ⱥ�б�
		for (int i = 0; i < groupList.size(); i++) {
			JSONObject resultJson = new JSONObject();
			resultJson.put("groupName", groupList.get(i));
			jsonArray.add(resultJson);
		}
		return jsonArray;
	}

}
