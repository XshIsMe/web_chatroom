package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserGroupDao {

	/**
	 * ��ȡȺ�б�
	 * 
	 * @param con
	 * @param userName
	 * @return
	 * @throws Exception
	 */
	public ResultSet selectUserGroup(Connection con, String userId, String groupName) throws Exception {
		// ���ݿ����
		StringBuffer sql = new StringBuffer("select * from user_group");
		if (null != userId) {
			sql.append(" and user_id='" + userId + "'");
		}
		if (null != groupName) {
			sql.append(" and groupname='" + groupName + "'");
		}

		PreparedStatement pstmt = con.prepareStatement(sql.toString().replaceFirst("and", "where"));

		// ִ�����ݿ���䲢����
		return pstmt.executeQuery();
	}

	/**
	 * ���Ⱥ
	 * 
	 * @param con
	 * @param userName
	 * @param groupName
	 * @throws SQLException
	 */
	public void addGroup(Connection con, String userId, String groupName) throws SQLException {
		String sql = "insert into user_group values(?,?)";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setString(1, userId);
		pstmt.setString(2, groupName);
		pstmt.executeUpdate();
	}

	/**
	 * ɾ��Ⱥ
	 * 
	 * @param con
	 * @param userName
	 * @param groupName
	 * @throws SQLException
	 */
	public void deleteGroup(Connection con, String userId, String groupName) throws SQLException {
		String sql = "delete from user_group where user_id=? and groupname=?";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setString(1, userId);
		pstmt.setString(2, groupName);
		pstmt.executeUpdate();
	}

}
