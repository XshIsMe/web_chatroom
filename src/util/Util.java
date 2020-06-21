package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class Util {

	// ���ݿ��ַ
	private static String Url = "jdbc:mysql://localhost:3306/webchat?useUnicode=true&characterEncoding=utf-8&useSSL=false";

	// �û���
	private static String UserName = "root";

	// ����
	private static String Password = "123456";

	// ��������
	private static String jdbcName = "com.mysql.jdbc.Driver";

	/**
	 * ��ȡ���ݿ�����
	 * 
	 * @return
	 * @throws Exception
	 */
	public Connection getCon() throws Exception {
		Class.forName(jdbcName);
		System.out.println("�������ݿ������ɹ�");
		Connection con = DriverManager.getConnection(Url, UserName, Password);
		System.out.println("��ȡ���ݿ����ӳɹ�");
		return con;
	}

	/**
	 * �ر����ݿ�����
	 * 
	 * @param con
	 * @param pstmt
	 * @throws Exception
	 */
	public void close(Connection con, PreparedStatement pstmt) throws Exception {
		if (null != pstmt) {
			pstmt.close();
			if (null != con) {
				con.close();
			}
		}
		System.out.println("�ѶϿ����ݿ�����");
	}

	/**
	 * �ر����ݿ�����
	 * 
	 * @param con
	 * @param pstmt1
	 * @param pstmt2
	 * @throws Exception
	 */
	public void close(Connection con, PreparedStatement pstmt1, PreparedStatement pstmt2) throws Exception {
		if (null != pstmt1) {
			pstmt1.close();
			if (null != pstmt2) {
				pstmt2.close();
				if (null != con) {
					con.close();
				}
			}
		}
		System.out.println("�ѶϿ����ݿ�����");
	}

}
