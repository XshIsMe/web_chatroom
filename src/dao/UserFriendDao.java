package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserFriendDao {

	/**
	 * 获取好友列表
	 * 
	 * @param con
	 * @param userName
	 * @return
	 * @throws Exception
	 */
	public ResultSet selectUserFriend(Connection con, String user_id, String friend_id) throws Exception {
		// 数据库语句
		StringBuffer sql = new StringBuffer("select * from user_friend");
		if (null != user_id) {
			sql.append(" and user_id='" + user_id + "'");
		}
		if (null != friend_id) {
			sql.append(" and friend_id='" + friend_id + "'");
		}

		PreparedStatement pstmt = con.prepareStatement(sql.toString().replaceFirst("and", "where"));

		// 执行数据库语句并返回
		return pstmt.executeQuery();
	}

	/**
	 * 添加好友
	 * 
	 * @param con
	 * @param userName
	 * @param friendName
	 * @throws SQLException
	 */
	public void addFriend(Connection con, String user_id, String friend_id) throws SQLException {
		String sql = "insert into user_friend values(?,?)";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setString(1, user_id);
		pstmt.setString(2, friend_id);
		pstmt.executeUpdate();
	}

	/**
	 * 删除好友
	 * 
	 * @param con
	 * @param userName
	 * @param friendName
	 * @throws SQLException
	 */
	public void deleteFriend(Connection con, String user_id, String friend_id) throws SQLException {
		String sql = "delete from user_friend where user_id=? and friend_id=?";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setString(1, user_id);
		pstmt.setString(2, friend_id);
		pstmt.executeUpdate();
	}

}
