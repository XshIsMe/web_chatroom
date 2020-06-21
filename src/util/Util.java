package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class Util {

	// 数据库地址
	private static String Url = "jdbc:mysql://localhost:3306/webchat?useUnicode=true&characterEncoding=utf-8&useSSL=false";

	// 用户名
	private static String UserName = "root";

	// 密码
	private static String Password = "123456";

	// 驱动名称
	private static String jdbcName = "com.mysql.jdbc.Driver";

	/**
	 * 获取数据库连接
	 * 
	 * @return
	 * @throws Exception
	 */
	public Connection getCon() throws Exception {
		Class.forName(jdbcName);
		System.out.println("加载数据库驱动成功");
		Connection con = DriverManager.getConnection(Url, UserName, Password);
		System.out.println("获取数据库连接成功");
		return con;
	}

	/**
	 * 关闭数据库连接
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
		System.out.println("已断开数据库连接");
	}

	/**
	 * 关闭数据库连接
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
		System.out.println("已断开数据库连接");
	}

}
