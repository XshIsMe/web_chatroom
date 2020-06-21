package websocket;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class WebSocketPool {

	/**
	 * ���������ѵ�¼���û�
	 */
	private static final Map<org.java_websocket.WebSocket, String> userPool = new HashMap<org.java_websocket.WebSocket, String>();

	/**
	 * ͨ���û�����ȡconn
	 * 
	 * @param userName
	 * @return
	 */
	public static org.java_websocket.WebSocket getConnByUserName(String userName) {
		// ��ȡ���������û���conn
		Set<org.java_websocket.WebSocket> keySet = userPool.keySet();
		// ��֤�̰߳�ȫ
		synchronized (keySet) {
			for (org.java_websocket.WebSocket conn : keySet) {
				String cuser = userPool.get(conn);
				// �ҵ��������û���ƥ���conn
				if (cuser.equals(userName)) {
					return conn;
				}
			}
		}
		return null;
	}

	/**
	 * ���û��������ӳ�
	 * 
	 * @param conn
	 * @param userName
	 */
	public static void addUser(org.java_websocket.WebSocket conn, String userName) {
		userPool.put(conn, userName);
		System.out.println(userName + "������");
		WebSocketPool.sendMessageToAll("������", conn);
	}

	/**
	 * ���û������ӳ��Ƴ�
	 * 
	 * @param conn
	 */
	public static void removeUser(org.java_websocket.WebSocket conn) {
		if (userPool.containsKey(conn)) {
			System.out.println(userPool.get(conn) + "������");
			WebSocketPool.sendMessageToAll("������", conn);
			userPool.remove(conn);
		} else {
			System.out.println("Error");
		}
	}

	/**
	 * ����Ϣ���͸������Լ������������
	 * 
	 * @param message
	 * @param from
	 */
	public static void sendMessageToAll(String message, org.java_websocket.WebSocket self) {
		// ��ȡ�����ߵ��û���
		String from = userPool.get(self);
		// ����Ϣ���Ϸ������û���
		message = "[" + from + "]" + message;
		// ��ȡ���������û���conn
		Set<org.java_websocket.WebSocket> keySet = userPool.keySet();
		// ��֤�̰߳�ȫ
		synchronized (keySet) {
			for (org.java_websocket.WebSocket conn : keySet) {
				String user = userPool.get(conn);
				// ����Ϣ���͸������Լ������������
				if (user != null && !user.equals(from)) {
					conn.send(message);
				}
			}
		}
	}

	/**
	 * ��ָ���û�������Ϣ
	 * 
	 * @param message
	 * @param userName
	 */
	public static void sendMessageToUser(String message, String to, org.java_websocket.WebSocket self) {
		// ��ȡ�����ߵ��û���
		String from = userPool.get(self);
		// ����Ϣ���Ϸ������û���
		message = "[" + from + "]" + message;
		// ��ȡ���Ͷ����conn
		org.java_websocket.WebSocket conn = WebSocketPool.getConnByUserName(to);
		if (conn != null) {
			conn.send(message);
		}
	}

}
