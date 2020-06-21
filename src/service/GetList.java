package service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;

import dao.UserDao;
import dao.UserFriendDao;
import dao.UserGroupDao;
import util.Util;

public class GetList {

	private static Util util = new Util();

	/**
	 * 获取并返回好友列表
	 * 
	 * @param userName
	 * @return
	 * @throws Exception
	 */
	public ArrayList<String> getFriendList(String userName) throws Exception {
		// 获取数据库连接
		Connection con = util.getCon();
		UserFriendDao ufd = new UserFriendDao();
		UserDao ud = new UserDao();
		// 在数据库中查找好友
		ArrayList<String> friendList = new ArrayList<String>();
		// 获取用户id
		String userId = ud.getUserId(con, userName);
		if (userId == null) {
			return friendList;
		}
		ResultSet rs = ufd.selectUserFriend(con, userId, null);
		while (rs.next()) {
			String friendName = ud.getUserName(con, rs.getString("friend_id"));
			friendList.add(friendName);
		}
		con.close();
		return friendList;
	}

	/**
	 * 获取并返回群列表
	 * 
	 * @param userName
	 * @return
	 * @throws Exception
	 */
	public ArrayList<String> getGroupList(String userName) throws Exception {
		// 获取数据库连接
		Connection con = util.getCon();
		UserGroupDao ugd = new UserGroupDao();
		UserDao ud = new UserDao();
		// 在数据库中查找群
		ArrayList<String> groupList = new ArrayList<String>();
		// 获取用户id
		String userId = ud.getUserId(con, userName);
		if (userId == null) {
			return groupList;
		}
		ResultSet rs = ugd.selectUserGroup(con, userId, null);
		while (rs.next()) {
			groupList.add(rs.getString("groupname"));
		}
		con.close();
		return groupList;
	}

	/**
	 * 获取群成员
	 * 
	 * @param groupName
	 * @return
	 * @throws Exception
	 */
	public ArrayList<String> getGroupStaff(String groupName) throws Exception {
		// 获取数据库连接
		Connection con = util.getCon();
		UserGroupDao ugd = new UserGroupDao();
		UserDao ud = new UserDao();
		// 在数据库中查找群
		ArrayList<String> staffList = new ArrayList<String>();
		ResultSet rs = ugd.selectUserGroup(con, null, groupName);
		while (rs.next()) {
			String userName = ud.getUserName(con, rs.getString("user_id"));
			staffList.add(userName);
		}
		con.close();
		return staffList;
	}

}
