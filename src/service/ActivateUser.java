package service;

import java.sql.Connection;

import dao.UserDao;
import util.Util;

public class ActivateUser {

	private static Util util = new Util();

	/**
	 * �����˺�
	 * 
	 * @param email
	 * @throws Exception 
	 */
	public void activateUser(String email) throws Exception {
		// ��ȡ���ݿ�����
		Connection con = util.getCon();
		UserDao ud = new UserDao();
		ud.activateUser(con, email);
		con.close();
	}

}
