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
		// 验证用户名格式
		if (!newUserName.matches("[a-zA-Z0-9\\u4e00-\\u9fa5]{1,20}")) {
			result = "用户名不能包含符号,并且长度不能超过20";
			return result;
		}
		// 获取数据库连接
		Connection con = util.getCon();
		UserDao ud = new UserDao();
		// 检查该用户名是否存在
		User toCheck = new User();
		toCheck.setUserName(newUserName);
		ResultSet rs = ud.selectUserAnd(con, toCheck);
		if (rs.next()) {
			result = "该用户名已被使用";
		} else {
			String userId = ud.getUserId(con, oldUserName);
			ud.updateUserName(con, userId, newUserName);
			result = "修改成功";
		}
		return result;
	}

}
