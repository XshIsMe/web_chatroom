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
	 * ��¼���
	 * 
	 * @param User
	 * @return
	 * @throws Exception
	 */
	public boolean checkLogin(User User) throws Exception {
		// ��ȡ���ݿ�����
		Connection con = util.getCon();
		UserDao ud = new UserDao();
		// �����ݿ����ж��˺������Ƿ���ȷ
		User toCheck = new User();
		toCheck.setEmail(User.getEmail());
		// MD5����
		toCheck.setPassword(EncryptionByMD5.MD5(User.getPassword()));
		ResultSet rs = ud.selectUserAnd(con, toCheck);
		if (rs.next()) {
			// �ж��Ƿ񼤻�
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
	 * ע����
	 * 
	 * @param User
	 * @return
	 * @throws Exception
	 */
	public boolean checkSignUp(User User) throws Exception {
		// ��֤�����ʽ
		if (!User.getEmail().matches("[a-zA-Z0-9]{1,20}+@[a-zA-Z0-9]{1,10}+.com")) {
			return false;
		}
		// ��֤�û�����ʽ
		if (!User.getUserName().matches("[a-zA-Z0-9\\u4e00-\\u9fa5]{1,20}")) {
			return false;
		}
		// ��֤�����ʽ
		if (!User.getPassword().matches("[a-zA-Z0-9]{1,20}")) {
			return false;
		}
		// ��֤���֤��ʽ
		if (!User.getIdcard().matches("[0-9]{17,17}+[0-9Xx]{1,1}")) {
			return false;
		}
		// ��֤������ʽ
		if (!User.getName().matches("[\\u4e00-\\u9fa5]{2,5}|[a-zA-Z]{1,20}")) {
			return false;
		}

		// ��ȡ���ݿ�����
		Connection con = util.getCon();
		UserDao ud = new UserDao();
		// �����ݿ��в���������û����Ƿ�ע��
		User toCheck = new User();
		toCheck.setEmail(User.getEmail());
		toCheck.setUserName(User.getUserName());
		ResultSet rs = ud.selectUserOr(con, toCheck);
		if (rs.next()) {
			con.close();
			return false;
		} else {
			// MD5����
			User.setPassword(EncryptionByMD5.MD5(User.getPassword()));
			// ���û���ӵ����ݿ�
			ud.addUser(con, User);
			// ���ͼ����ʼ�
			SendEmail se = new SendEmail();
			se.sendEmail(User.getEmail());
			con.close();
			return true;
		}
	}

	/**
	 * �һ�������
	 * 
	 * @param User
	 * @return
	 * @throws Exception
	 */
	public boolean checkForget(User User) throws Exception {
		// ��ȡ���ݿ�����
		Connection con = util.getCon();
		UserDao ud = new UserDao();
		// �����ݿ����ж���Ϣ�Ƿ���ȷ
		User toCheck = new User();
		toCheck.setEmail(User.getEmail());
		toCheck.setIdcard(User.getIdcard());
		ResultSet rs = ud.selectUserAnd(con, toCheck);
		if (rs.next()) {
			// MD5����
			User.setPassword(EncryptionByMD5.MD5(User.getPassword()));
			// �޸����ݿ��е�����
			ud.updateUser(con, User);
			con.close();
			return true;
		} else {
			con.close();
			return false;
		}
	}

}
