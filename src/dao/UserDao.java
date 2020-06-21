package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.User;

public class UserDao {

	/**
	 * ��ȡ�û���
	 * 
	 * @param con
	 * @param userId
	 * @return
	 * @throws SQLException
	 */
	public String getUserName(Connection con, String userId) throws SQLException {
		String userName = null;
		String sql = "select username from user where user_id=?";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setString(1, userId);
		ResultSet rs = pstmt.executeQuery();
		while (rs.next()) {
			userName = rs.getString("username");
		}
		return userName;
	}

	/**
	 * ��ȡ�û�id
	 * 
	 * @param con
	 * @param userName
	 * @return
	 * @throws SQLException
	 */
	public String getUserId(Connection con, String userName) throws SQLException {
		String userId = null;
		String sql = "select user_id from user where username=?";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setString(1, userName);
		ResultSet rs = pstmt.executeQuery();
		while (rs.next()) {
			userId = rs.getString("user_id");
		}
		return userId;
	}

	/**
	 * ���ݿ�Ͳ�ѯ
	 * 
	 * @param con
	 * @param User
	 * @return
	 * @throws Exception
	 */
	public ResultSet selectUserAnd(Connection con, User User) throws Exception {
		// ���ݿ����
		StringBuffer sql = new StringBuffer("select * from user");
		if (null != User.getEmail()) {
			sql.append(" and email='" + User.getEmail() + "'");
		}
		if (null != User.getUserName()) {
			sql.append(" and username='" + User.getUserName() + "'");
		}
		if (null != User.getPassword()) {
			sql.append(" and password='" + User.getPassword() + "'");
		}
		if (null != User.getIdcard()) {
			sql.append(" and idcard='" + User.getIdcard() + "'");
		}
		if (null != User.getName()) {
			sql.append(" and name='" + User.getName() + "'");
		}

		PreparedStatement pstmt = con.prepareStatement(sql.toString().replaceFirst("and", "where"));

		// ִ�����ݿ���䲢����
		return pstmt.executeQuery();
	}

	/**
	 * ���ݿ���ѯ
	 * 
	 * @param con
	 * @param User
	 * @return
	 * @throws Exception
	 */
	public ResultSet selectUserOr(Connection con, User User) throws Exception {
		// ���ݿ����
		StringBuffer sql = new StringBuffer("select * from user");
		if (null != User.getEmail()) {
			sql.append(" or email='" + User.getEmail() + "'");
		}
		if (null != User.getUserName()) {
			sql.append(" or username='" + User.getUserName() + "'");
		}
		if (null != User.getPassword()) {
			sql.append(" or password='" + User.getPassword() + "'");
		}
		if (null != User.getIdcard()) {
			sql.append(" or idcard='" + User.getIdcard() + "'");
		}
		if (null != User.getName()) {
			sql.append(" or name='" + User.getName() + "'");
		}

		PreparedStatement pstmt = con.prepareStatement(sql.toString().replaceFirst("or", "where"));

		// ִ�����ݿ���䲢����
		return pstmt.executeQuery();
	}

	/**
	 * ��user��������û�
	 * 
	 * @param con
	 * @param User
	 * @throws SQLException
	 */
	public void addUser(Connection con, User User) throws SQLException {
		String sql = "insert into user values(null,?,?,?,?,?,0)";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setString(1, User.getEmail());
		pstmt.setString(2, User.getUserName());
		pstmt.setString(3, User.getPassword());
		pstmt.setString(4, User.getIdcard());
		pstmt.setString(5, User.getName());
		pstmt.executeUpdate();
	}

	/**
	 * ��ȡ�û�������Ϣ
	 * 
	 * @param con
	 * @param email
	 * @throws SQLException
	 */
	public void activateUser(Connection con, String email) throws SQLException {
		String sql = "update user set activation=1 where email=?";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setString(1, email);
		pstmt.executeUpdate();
	}

	/**
	 * �޸�����
	 * 
	 * @param con
	 * @param User
	 * @throws Exception
	 */
	public void updateUser(Connection con, User User) throws Exception {
		String sql = "update user set password=? where email=?";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setString(1, User.getPassword());
		pstmt.setString(2, User.getEmail());
		pstmt.executeUpdate();
	}

	/**
	 * �޸��û���
	 * 
	 * @param con
	 * @param userId
	 * @param userName
	 * @throws Exception
	 */
	public void updateUserName(Connection con, String userId, String userName) throws Exception {
		String sql = "update user set username=? where user_id=?";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setString(1, userName);
		pstmt.setString(2, userId);
		pstmt.executeUpdate();
	}

}
