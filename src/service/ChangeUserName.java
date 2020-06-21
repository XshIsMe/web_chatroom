package service;

import java.sql.Connection;
import java.sql.ResultSet;

import dao.UserDao;
import model.User;
import util.Util;

public class ChangeUserName {

	private static Util util = new Util();

	public String changeUserName(String oldUserName, String newUserName) throws Exception {
		String result = null;
		// ��֤�û�����ʽ
		if (!newUserName.matches("[a-zA-Z0-9\\u4e00-\\u9fa5]{1,20}")) {
			result = "�û������ܰ�������,���ҳ��Ȳ��ܳ���20";
			return result;
		}
		// ��ȡ���ݿ�����
		Connection con = util.getCon();
		UserDao ud = new UserDao();
		// �����û����Ƿ����
		User toCheck = new User();
		toCheck.setUserName(newUserName);
		ResultSet rs = ud.selectUserAnd(con, toCheck);
		if (rs.next()) {
			result = "���û����ѱ�ʹ��";
		} else {
			String userId = ud.getUserId(con, oldUserName);
			ud.updateUserName(con, userId, newUserName);
			result = "�޸ĳɹ�";
		}
		return result;
	}

}
