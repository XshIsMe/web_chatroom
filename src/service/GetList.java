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
	 * ��ȡ�����غ����б�
	 * 
	 * @param userName
	 * @return
	 * @throws Exception
	 */
	public ArrayList<String> getFriendList(String userName) throws Exception {
		// ��ȡ���ݿ�����
		Connection con = util.getCon();
		UserFriendDao ufd = new UserFriendDao();
		UserDao ud = new UserDao();
		// �����ݿ��в��Һ���
		ArrayList<String> friendList = new ArrayList<String>();
		// ��ȡ�û�id
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
	 * ��ȡ������Ⱥ�б�
	 * 
	 * @param userName
	 * @return
	 * @throws Exception
	 */
	public ArrayList<String> getGroupList(String userName) throws Exception {
		// ��ȡ���ݿ�����
		Connection con = util.getCon();
		UserGroupDao ugd = new UserGroupDao();
		UserDao ud = new UserDao();
		// �����ݿ��в���Ⱥ
		ArrayList<String> groupList = new ArrayList<String>();
		// ��ȡ�û�id
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
	 * ��ȡȺ��Ա
	 * 
	 * @param groupName
	 * @return
	 * @throws Exception
	 */
	public ArrayList<String> getGroupStaff(String groupName) throws Exception {
		// ��ȡ���ݿ�����
		Connection con = util.getCon();
		UserGroupDao ugd = new UserGroupDao();
		UserDao ud = new UserDao();
		// �����ݿ��в���Ⱥ
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
