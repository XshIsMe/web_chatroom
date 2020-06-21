package service;

import java.sql.Connection;
import java.sql.ResultSet;

import dao.UserDao;
import model.User;
import util.EncryptionByMD5;
import util.SendEmail;
import util.Util;

public class Check {

	private static Util util = new Util();

	/**
	 * 登录检查
	 * 
	 * @param User
	 * @return
	 * @throws Exception
	 */
	public boolean checkLogin(User User) throws Exception {
		// 获取数据库连接
		Connection con = util.getCon();
		UserDao ud = new UserDao();
		// 在数据库中判断账号密码是否正确
		User toCheck = new User();
		toCheck.setEmail(User.getEmail());
		// MD5加密
		toCheck.setPassword(EncryptionByMD5.MD5(User.getPassword()));
		ResultSet rs = ud.selectUserAnd(con, toCheck);
		if (rs.next()) {
			// 判断是否激活
			if ("1".equals(rs.getString("activation"))) {
				User.setUserName(rs.getString("username"));
				con.close();
				return true;
			} else {
				return false;
			}
		} else {
			con.close();
			return false;
		}
	}

	/**
	 * 注册检查
	 * 
	 * @param User
	 * @return
	 * @throws Exception
	 */
	public boolean checkSignUp(User User) throws Exception {
		// 验证邮箱格式
		if (!User.getEmail().matches("[a-zA-Z0-9]{1,20}+@[a-zA-Z0-9]{1,10}+.com")) {
			return false;
		}
		// 验证用户名格式
		if (!User.getUserName().matches("[a-zA-Z0-9\\u4e00-\\u9fa5]{1,20}")) {
			return false;
		}
		// 验证密码格式
		if (!User.getPassword().matches("[a-zA-Z0-9]{1,20}")) {
			return false;
		}
		// 验证身份证格式
		if (!User.getIdcard().matches("[0-9]{17,17}+[0-9Xx]{1,1}")) {
			return false;
		}
		// 验证姓名格式
		if (!User.getName().matches("[\\u4e00-\\u9fa5]{2,5}|[a-zA-Z]{1,20}")) {
			return false;
		}

		// 获取数据库连接
		Connection con = util.getCon();
		UserDao ud = new UserDao();
		// 在数据库中查找邮箱和用户名是否被注册
		User toCheck = new User();
		toCheck.setEmail(User.getEmail());
		toCheck.setUserName(User.getUserName());
		ResultSet rs = ud.selectUserOr(con, toCheck);
		if (rs.next()) {
			con.close();
			return false;
		} else {
			// MD5加密
			User.setPassword(EncryptionByMD5.MD5(User.getPassword()));
			// 将用户添加到数据库
			ud.addUser(con, User);
			// 发送激活邮件
			SendEmail se = new SendEmail();
			se.sendEmail(User.getEmail());
			con.close();
			return true;
		}
	}

	/**
	 * 找回密码检查
	 * 
	 * @param User
	 * @return
	 * @throws Exception
	 */
	public boolean checkForget(User User) throws Exception {
		// 获取数据库连接
		Connection con = util.getCon();
		UserDao ud = new UserDao();
		// 在数据库中判断信息是否正确
		User toCheck = new User();
		toCheck.setEmail(User.getEmail());
		toCheck.setIdcard(User.getIdcard());
		ResultSet rs = ud.selectUserAnd(con, toCheck);
		if (rs.next()) {
			// MD5加密
			User.setPassword(EncryptionByMD5.MD5(User.getPassword()));
			// 修改数据库中的密码
			ud.updateUser(con, User);
			con.close();
			return true;
		} else {
			con.close();
			return false;
		}
	}

}
