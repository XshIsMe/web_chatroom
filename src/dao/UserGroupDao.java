package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserGroupDao {

	/**
	 * 获取群列表
	 * 
	 * @param con
	 * @param userName
	 * @return
	 * @throws Exception
	 */
	public ResultSet selectUserGroup(Connection con, String userId, String groupName) throws Exception {
		// 数据库语句
		StringBuffer sql = new StringBuffer("select * from user_group");
		if (null != userId) {
			sql.append(" and user_id='" + userId + "'");
		}
		if (null != groupName) {
			sql.append(" and groupname='" + groupName + "'");
		}

		PreparedStatement pstmt = con.prepareStatement(sql.toString().replaceFirst("and", "where"));

		// 执行数据库语句并返回
		return pstmt.executeQuery();
	}

	/**
	 * 添加群
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
	 * 删除群
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
