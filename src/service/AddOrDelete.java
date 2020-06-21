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
	 * ��Ӻ���
	 * 
	 * @param userName
	 * @param friendName
	 * @throws Exception
	 */
	public String addFriend(String userName, String friendName) throws Exception {
		String result = null;
		User toCheck = new User();
		// ��ȡ���ݿ�����
		Connection con = util.getCon();
		UserFriendDao ufd = new UserFriendDao();
		UserDao ud = new UserDao();
		// ��ѯ���û��Ƿ����
		toCheck.setUserName(friendName);
		ResultSet rs1 = ud.selectUserAnd(con, toCheck);
		if (rs1.next()) {
			// ��ȡuser_id��friend_id
			String userId = ud.getUserId(con, userName);
			String friendId = ud.getUserId(con, friendName);
			// ��ѯ�ǲ����Լ�
			if (userId.equals(friendId)) {
				result = "��������Լ�Ϊ����";
			} else {
				// ��ѯ�Ƿ��Ѿ���Ӹ��û�
				ResultSet rs2 = ufd.selectUserFriend(con, userId, friendId);
				if (rs2.next()) {
					result = "����Ӹ��û�";
				} else {
					ufd.addFriend(con, userId, friendId);
					ufd.addFriend(con, friendId, userId);
					result = "��ӳɹ�";
				}
			}
		} else {
			result = "���û�������";
		}
		con.close();
		return result;
	}

	/**
	 * ɾ������
	 * 
	 * @param userName
	 * @param friendName
	 * @throws Exception
	 */
	public String deleteFriend(String userName, String friendName) throws Exception {
		String result = null;
		// ��ȡ���ݿ�����
		Connection con = util.getCon();
		UserFriendDao ufd = new UserFriendDao();
		UserDao ud = new UserDao();
		// ��ȡuser_id��friend_id
		String userId = ud.getUserId(con, userName);
		String friendId = ud.getUserId(con, friendName);
		// ��ѯ�Ƿ��Ѿ���Ӹ��û�
		ResultSet rs = ufd.selectUserFriend(con, userId, friendId);
		if (rs.next()) {
			// ɾ������
			ufd.deleteFriend(con, userId, friendId);
			ufd.deleteFriend(con, friendId, userId);
			con.close();
			result = "ɾ���ɹ�";
		} else {
			result = "�Է�������ĺ���";
		}
		con.close();
		return result;
	}

	/**
	 * ���Ⱥ
	 * 
	 * @param userName
	 * @param groupName
	 * @throws Exception
	 */
	public String addGroup(String userName, String groupName) throws Exception {
		String result = null;
		// ��ȡ���ݿ�����
		Connection con = util.getCon();
		UserGroupDao ugd = new UserGroupDao();
		UserDao ud = new UserDao();
		// ��ȡuser_id
		String userId = ud.getUserId(con, userName);
		// ��ѯ�Ƿ���Ⱥ��
		ResultSet rs = ugd.selectUserGroup(con, userId, groupName);
		if (rs.next()) {
			result = "�Ѿ���Ⱥ����";
		} else {
			// ���Ⱥ
			ugd.addGroup(con, userId, groupName);
			result = "��ӳɹ�";
		}
		con.close();
		return result;
	}

	/**
	 * ɾ��Ⱥ
	 * 
	 * @param userName
	 * @param groupName
	 * @throws Exception
	 */
	public String deleteGroup(String userName, String groupName) throws Exception {
		String result = null;
		// ��ȡ���ݿ�����
		Connection con = util.getCon();
		UserGroupDao ugd = new UserGroupDao();
		UserDao ud = new UserDao();
		// ��ȡuser_id
		String userId = ud.getUserId(con, userName);
		// ��ѯ�Ƿ���Ⱥ��
		ResultSet rs = ugd.selectUserGroup(con, userId, groupName);
		if (rs.next()) {
			// ɾ��Ⱥ
			ugd.deleteGroup(con, userId, groupName);
			result = "ɾ���ɹ�";
		} else {
			result = "�㲻�����Ⱥ��";
		}
		con.close();
		return result;
	}

	/**
	 * ������ѽ�Ⱥ
	 * 
	 * @param userName
	 * @param friendName
	 * @param groupName
	 * @return
	 * @throws Exception
	 */
	public String inviteFriend(String userName, String friendName, String groupName) throws Exception {
		String result = null;
		// ��ȡ���ݿ�����
		Connection con = util.getCon();
		UserGroupDao ugd = new UserGroupDao();
		UserFriendDao ufd = new UserFriendDao();
		UserDao ud = new UserDao();
		// ��ȡ�û�id
		String userId = ud.getUserId(con, userName);
		String friendId = ud.getUserId(con, friendName);
		// �ж��Ƿ��ڸ�Ⱥ��
		ResultSet rs1 = ugd.selectUserGroup(con, userId, groupName);
		if (!rs1.next()) {
			result = "�㲻�ڸ�Ⱥ��";
		} else {
			// �ж��ǲ��Ǻ���
			ResultSet rs2 = ufd.selectUserFriend(con, userId, friendId);
			if (!rs2.next() || friendId == null) {
				result = "�Է�������ĺ���";
			} else {
				// �жϺ����ڲ���Ⱥ��
				ResultSet rs3 = ugd.selectUserGroup(con, friendId, groupName);
				if (rs3.next()) {
					result = "��������Ⱥ��";
				} else {
					// ���Ⱥ
					ugd.addGroup(con, friendId, groupName);
					result = "����ɹ�";
				}
			}
		}
		con.close();
		return result;
	}

}
