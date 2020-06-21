package service;

import java.sql.Connection;
import java.sql.ResultSet;

import dao.UserDao;
import dao.UserFriendDao;
import dao.UserGroupDao;
import model.User;
import util.Util;

public class AddOrDelete {

	private static Util util = new Util();

	/**
	 * 添加好友
	 * 
	 * @param userName
	 * @param friendName
	 * @throws Exception
	 */
	public String addFriend(String userName, String friendName) throws Exception {
		String result = null;
		User toCheck = new User();
		// 获取数据库连接
		Connection con = util.getCon();
		UserFriendDao ufd = new UserFriendDao();
		UserDao ud = new UserDao();
		// 查询该用户是否存在
		toCheck.setUserName(friendName);
		ResultSet rs1 = ud.selectUserAnd(con, toCheck);
		if (rs1.next()) {
			// 获取user_id和friend_id
			String userId = ud.getUserId(con, userName);
			String friendId = ud.getUserId(con, friendName);
			// 查询是不是自己
			if (userId.equals(friendId)) {
				result = "不能添加自己为好友";
			} else {
				// 查询是否已经添加该用户
				ResultSet rs2 = ufd.selectUserFriend(con, userId, friendId);
				if (rs2.next()) {
					result = "已添加该用户";
				} else {
					ufd.addFriend(con, userId, friendId);
					ufd.addFriend(con, friendId, userId);
					result = "添加成功";
				}
			}
		} else {
			result = "该用户不存在";
		}
		con.close();
		return result;
	}

	/**
	 * 删除好友
	 * 
	 * @param userName
	 * @param friendName
	 * @throws Exception
	 */
	public String deleteFriend(String userName, String friendName) throws Exception {
		String result = null;
		// 获取数据库连接
		Connection con = util.getCon();
		UserFriendDao ufd = new UserFriendDao();
		UserDao ud = new UserDao();
		// 获取user_id和friend_id
		String userId = ud.getUserId(con, userName);
		String friendId = ud.getUserId(con, friendName);
		// 查询是否已经添加该用户
		ResultSet rs = ufd.selectUserFriend(con, userId, friendId);
		if (rs.next()) {
			// 删除好友
			ufd.deleteFriend(con, userId, friendId);
			ufd.deleteFriend(con, friendId, userId);
			con.close();
			result = "删除成功";
		} else {
			result = "对方不是你的好友";
		}
		con.close();
		return result;
	}

	/**
	 * 添加群
	 * 
	 * @param userName
	 * @param groupName
	 * @throws Exception
	 */
	public String addGroup(String userName, String groupName) throws Exception {
		String result = null;
		// 获取数据库连接
		Connection con = util.getCon();
		UserGroupDao ugd = new UserGroupDao();
		UserDao ud = new UserDao();
		// 获取user_id
		String userId = ud.getUserId(con, userName);
		// 查询是否在群里
		ResultSet rs = ugd.selectUserGroup(con, userId, groupName);
		if (rs.next()) {
			result = "已经在群里了";
		} else {
			// 添加群
			ugd.addGroup(con, userId, groupName);
			result = "添加成功";
		}
		con.close();
		return result;
	}

	/**
	 * 删除群
	 * 
	 * @param userName
	 * @param groupName
	 * @throws Exception
	 */
	public String deleteGroup(String userName, String groupName) throws Exception {
		String result = null;
		// 获取数据库连接
		Connection con = util.getCon();
		UserGroupDao ugd = new UserGroupDao();
		UserDao ud = new UserDao();
		// 获取user_id
		String userId = ud.getUserId(con, userName);
		// 查询是否在群里
		ResultSet rs = ugd.selectUserGroup(con, userId, groupName);
		if (rs.next()) {
			// 删除群
			ugd.deleteGroup(con, userId, groupName);
			result = "删除成功";
		} else {
			result = "你不在这个群内";
		}
		con.close();
		return result;
	}

	/**
	 * 邀请好友进群
	 * 
	 * @param userName
	 * @param friendName
	 * @param groupName
	 * @return
	 * @throws Exception
	 */
	public String inviteFriend(String userName, String friendName, String groupName) throws Exception {
		String result = null;
		// 获取数据库连接
		Connection con = util.getCon();
		UserGroupDao ugd = new UserGroupDao();
		UserFriendDao ufd = new UserFriendDao();
		UserDao ud = new UserDao();
		// 获取用户id
		String userId = ud.getUserId(con, userName);
		String friendId = ud.getUserId(con, friendName);
		// 判断是否在该群内
		ResultSet rs1 = ugd.selectUserGroup(con, userId, groupName);
		if (!rs1.next()) {
			result = "你不在该群内";
		} else {
			// 判断是不是好友
			ResultSet rs2 = ufd.selectUserFriend(con, userId, friendId);
			if (!rs2.next() || friendId == null) {
				result = "对方不是你的好友";
			} else {
				// 判断好友在不在群内
				ResultSet rs3 = ugd.selectUserGroup(con, friendId, groupName);
				if (rs3.next()) {
					result = "好友已在群内";
				} else {
					// 添加群
					ugd.addGroup(con, friendId, groupName);
					result = "邀请成功";
				}
			}
		}
		con.close();
		return result;
	}

}
