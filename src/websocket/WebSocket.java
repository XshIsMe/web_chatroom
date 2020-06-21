package websocket;

import java.net.InetSocketAddress;
import java.util.ArrayList;

import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import service.GetList;

public class WebSocket extends WebSocketServer {

	public WebSocket(InetSocketAddress address) {
		super(address);
		System.out.println("地址：" + address);
	}

	/**
	 * websocket启动时显示端口
	 * 
	 * @param port
	 */
	public WebSocket(int port) {
		super(new InetSocketAddress(port));
		System.out.println("端口：" + port);
	}

	/**
	 * 断开连接时触发
	 */
	@Override
	public void onClose(org.java_websocket.WebSocket conn, int arg1, String arg2, boolean arg3) {
		System.out.println(conn + "断开连接");
		// 将用户从连接池移除
		userLeave(conn);
	}

	/**
	 * 出现异常时触发
	 */
	@Override
	public void onError(org.java_websocket.WebSocket conn, Exception e) {
		System.out.println("WebSocket异常：" + e.toString());
	}

	/**
	 * 收到消息时触发
	 */
	@Override
	public void onMessage(org.java_websocket.WebSocket conn, String message) {
		System.out.println(conn + "发来消息：" + message);
		if (message.startsWith("online&")) {
			String userName = message.replaceFirst("online&", "");
			userJoin(conn, userName);
		} else if (message.startsWith("publicChat&")) {
			// 将消息标签去掉
			message = message.replaceFirst("publicChat&", "");
			// 将发送对象标签去掉
			message = message.substring(0, message.lastIndexOf("@"));
			message = "[聊天室]" + message;
			WebSocketPool.sendMessageToAll(message, conn);
		} else if (message.startsWith("privateChat&")) {
			// 将消息标签去掉
			message = message.replaceFirst("privateChat&", "");
			// 获取发送对象
			String sendto = message.substring(message.lastIndexOf("@") + 1);
			// 将发送对象标签去掉
			message = message.substring(0, message.lastIndexOf("@"));
			message = "[私聊]" + message;
			WebSocketPool.sendMessageToUser(message, sendto, conn);
		} else if (message.startsWith("groupChat&")) {
			// 将消息标签去掉
			message = message.replaceFirst("groupChat&", "");
			// 获取发送对象
			String sendto = message.substring(message.lastIndexOf("@") + 1);
			// 将发送对象标签去掉
			message = message.substring(0, message.lastIndexOf("@"));
			message = "[群聊：" + sendto + "]" + message;
			// 获取群成员
			GetList gl = new GetList();
			ArrayList<String> staffList = null;
			try {
				staffList = gl.getGroupStaff(sendto);
			} catch (Exception e) {
				e.printStackTrace();
			}
			for (int i = 0; i < staffList.size(); i++) {
				String staff = staffList.get(i);
				WebSocketPool.sendMessageToUser(message, staff, conn);
			}
		} else if (message.startsWith("offline&")) {
			userLeave(conn);
		}
	}

	/**
	 * 有人连接时触发
	 */
	@Override
	public void onOpen(org.java_websocket.WebSocket conn, ClientHandshake arg1) {
		System.out.println(conn + "连接到WebSocket");
	}

	/**
	 * 将用户加入连接池
	 * 
	 * @param conn
	 * @param userName
	 */
	public void userJoin(org.java_websocket.WebSocket conn, String userName) {
		WebSocketPool.addUser(conn, userName);
	}

	/**
	 * 将用户从连接池移除
	 * 
	 * @param conn
	 */
	public void userLeave(org.java_websocket.WebSocket conn) {
		WebSocketPool.removeUser(conn);
	}

}
