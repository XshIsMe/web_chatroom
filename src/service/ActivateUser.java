package service;

import java.sql.Connection;

import dao.UserDao;
import util.Util;

public class ActivateUser {

	private static Util util = new Util();

	/**
	 * 激活账号
	 * 
	 * @param email
	 * @throws Exception 
	 */
	public void activateUser(String email) throws Exception {
		// 获取数据库连接
		Connection con = util.getCon();
		UserDao ud = new UserDao();
		ud.activateUser(con, email);
		con.close();
	}

}
