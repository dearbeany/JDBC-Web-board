package com.ssafy.board.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {

	// MySql 드라이버 클래스 이름
	private final String driverName = "com.mysql.cj.jdbc.Driver";
	// DB와 연결하기 위해 필요한 URL
	private final String url = "jdbc:mysql://localhost:3306/dearbeany_board?serverTimezone=UTC";
	// USER 정보
	private final String username = "root";
	private final String password = "ssafy";

	private static DBUtil instance = new DBUtil();

	private DBUtil() {
		// JDBC 드라이버를 로딩
		try {
			Class.forName(driverName);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static DBUtil getInstace() {
		return instance;
	}

	/**
	 * DriverManager를 통해 내 userid, password를 이용해 Connection을 반환하는 메서드
	 * 
	 * @return Connection
	 * @throws SQLException
	 */
	public Connection getConnection() throws SQLException {
		return DriverManager.getConnection(url, username, password);
	}

	// ...가변인자를 사용하여 값이 몇개가 넘어오든 알아서 묶어서 반복할 수 있게끔 알아서 처리해줌
	public void close(AutoCloseable... autoCloseables) {
		for (AutoCloseable ac : autoCloseables) {
			if (ac != null) {
				try {
					ac.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	// 오버로딩
	// 오버로딩 하지 않고도 AutoClosealbe... 가변인자를 사용하여 메소드 정의 1번에 모두 처리하는 방법도 가능하다
//	public static void close(Connection conn, PreparedStatement pstmt) {
//		try {
//			if (pstmt != null) {
//				pstmt.close();
//			}
//		} catch (Exception e) {
//			e.getStackTrace();
//		}
//		try {
//			if (conn != null) {
//				conn.close();
//			}
//		} catch (Exception e) {
//			e.getStackTrace();
//		}
//	}
//
//	public static void close(Connection conn, PreparedStatement pstmt, ResultSet rs) {
//		try {
//			if (rs != null) {
//				rs.close();
//			}
//		} catch (Exception e) {
//			e.getStackTrace();
//		}
//		try {
//			if (pstmt != null) {
//				pstmt.close();
//			}
//		} catch (Exception e) {
//			e.getStackTrace();
//		}
//		try {
//			if (conn != null) {
//				conn.close();
//			}
//		} catch (Exception e) {
//			e.getStackTrace();
//		}
//	}

}
