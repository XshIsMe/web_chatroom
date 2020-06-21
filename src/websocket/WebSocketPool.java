package websocket;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class WebSocketPool {

	/**
	 * 用来储存已登录的用户
	 */
	private static final Map<org.java_websocket.WebSocket, String> userPool = new HashMap<org.java_websocket.WebSocket, String>();

	/**
	 * 通过用户名获取conn
	 * 
	 * @param userName
	 * @return
	 */
	public static org.java_websocket.WebSocket getConnByUserName(String userName) {
		// 获取所有在线用户的conn
		Set<org.java_websocket.WebSocket> keySet = userPool.keySet();
		// 保证线程安全
		synchronized (keySet) {
			for (org.java_websocket.WebSocket conn : keySet) {
				String cuser = userPool.get(conn);
				// 找到并返回用户名匹配的conn
				if (cuser.equals(userName)) {
					return conn;
				}
			}
		}
		return null;
	}

	/**
	 * 将用户加入连接池
	 * 
	 * @param conn
	 * @param userName
	 */
	public static void addUser(org.java_websocket.WebSocket conn, String userName) {
		userPool.put(conn, userName);
		System.out.println(userName + "已上线");
		WebSocketPool.sendMessageToAll("已上线", conn);
	}

	/**
	 * 将用户从连接池移除
	 * 
	 * @param conn
	 */
	public static void removeUser(org.java_websocket.WebSocket conn) {
		if (userPool.containsKey(conn)) {
			System.out.println(userPool.get(conn) + "已下线");
			WebSocketPool.sendMessageToAll("已下线", conn);
			userPool.remove(conn);
		} else {
			System.out.println("Error");
		}
	}

	/**
	 * 将消息发送给除了自己以外的所有人
	 * 
	 * @param message
	 * @param from
	 */
	public static void sendMessageToAll(String message, org.java_websocket.WebSocket self) {
		// 获取发送者的用户名
		String from = userPool.get(self);
		// 给消息加上发送者用户名
		message = "[" + from + "]" + message;
		// 获取所有在线用户的conn
		Set<org.java_websocket.WebSocket> keySet = userPool.keySet();
		// 保证线程安全
		synchronized (keySet) {
			for (org.java_websocket.WebSocket conn : keySet) {
				String user = userPool.get(conn);
				// 将消息发送给除了自己以外的所有人
				if (user != null && !user.equals(from)) {
					conn.send(message);
				}
			}
		}
	}

	/**
	 * 向指定用户发送消息
	 * 
	 * @param message
	 * @param userName
	 */
	public static void sendMessageToUser(String message, String to, org.java_websocket.WebSocket self) {
		// 获取发送者的用户名
		String from = userPool.get(self);
		// 给消息加上发送者用户名
		message = "[" + from + "]" + message;
		// 获取发送对象的conn
		org.java_websocket.WebSocket conn = WebSocketPool.getConnByUserName(to);
		if (conn != null) {
			conn.send(message);
		}
	}

}
