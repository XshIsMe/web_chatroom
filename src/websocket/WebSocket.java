package websocket;

import java.net.InetSocketAddress;
import java.util.ArrayList;

import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import service.GetList;

public class WebSocket extends WebSocketServer {

	public WebSocket(InetSocketAddress address) {
		super(address);
		System.out.println("��ַ��" + address);
	}

	/**
	 * websocket����ʱ��ʾ�˿�
	 * 
	 * @param port
	 */
	public WebSocket(int port) {
		super(new InetSocketAddress(port));
		System.out.println("�˿ڣ�" + port);
	}

	/**
	 * �Ͽ�����ʱ����
	 */
	@Override
	public void onClose(org.java_websocket.WebSocket conn, int arg1, String arg2, boolean arg3) {
		System.out.println(conn + "�Ͽ�����");
		// ���û������ӳ��Ƴ�
		userLeave(conn);
	}

	/**
	 * �����쳣ʱ����
	 */
	@Override
	public void onError(org.java_websocket.WebSocket conn, Exception e) {
		System.out.println("WebSocket�쳣��" + e.toString());
	}

	/**
	 * �յ���Ϣʱ����
	 */
	@Override
	public void onMessage(org.java_websocket.WebSocket conn, String message) {
		System.out.println(conn + "������Ϣ��" + message);
		if (message.startsWith("online&")) {
			String userName = message.replaceFirst("online&", "");
			userJoin(conn, userName);
		} else if (message.startsWith("publicChat&")) {
			// ����Ϣ��ǩȥ��
			message = message.replaceFirst("publicChat&", "");
			// �����Ͷ����ǩȥ��
			message = message.substring(0, message.lastIndexOf("@"));
			message = "[������]" + message;
			WebSocketPool.sendMessageToAll(message, conn);
		} else if (message.startsWith("privateChat&")) {
			// ����Ϣ��ǩȥ��
			message = message.replaceFirst("privateChat&", "");
			// ��ȡ���Ͷ���
			String sendto = message.substring(message.lastIndexOf("@") + 1);
			// �����Ͷ����ǩȥ��
			message = message.substring(0, message.lastIndexOf("@"));
			message = "[˽��]" + message;
			WebSocketPool.sendMessageToUser(message, sendto, conn);
		} else if (message.startsWith("groupChat&")) {
			// ����Ϣ��ǩȥ��
			message = message.replaceFirst("groupChat&", "");
			// ��ȡ���Ͷ���
			String sendto = message.substring(message.lastIndexOf("@") + 1);
			// �����Ͷ����ǩȥ��
			message = message.substring(0, message.lastIndexOf("@"));
			message = "[Ⱥ�ģ�" + sendto + "]" + message;
			// ��ȡȺ��Ա
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
	 * ��������ʱ����
	 */
	@Override
	public void onOpen(org.java_websocket.WebSocket conn, ClientHandshake arg1) {
		System.out.println(conn + "���ӵ�WebSocket");
	}

	/**
	 * ���û��������ӳ�
	 * 
	 * @param conn
	 * @param userName
	 */
	public void userJoin(org.java_websocket.WebSocket conn, String userName) {
		WebSocketPool.addUser(conn, userName);
	}

	/**
	 * ���û������ӳ��Ƴ�
	 * 
	 * @param conn
	 */
	public void userLeave(org.java_websocket.WebSocket conn) {
		WebSocketPool.removeUser(conn);
	}

}
