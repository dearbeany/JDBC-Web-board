package com.ssafy.board.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.ssafy.board.model.dto.Board;
import com.ssafy.board.util.DBUtil;

public class BoardDaoImpl implements BoardDao {
	// 만들어진 DBUtil 들고오기
	private final DBUtil util = DBUtil.getInstace();

	private static BoardDaoImpl instance = new BoardDaoImpl();

	private BoardDaoImpl() {
	}

	public static BoardDaoImpl getInstace() {
		return instance;
	}

	@Override
	public List<Board> selectAll() {
		String sql = "SELECT id, title, content, view_cnt, writer, date_format(reg_date, '%Y-%m-%d') AS reg_date FROM board ORDER BY id DESC";

		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		List<Board> list = new ArrayList<>();
		try {
			conn = util.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);

			while (rs.next()) {
				int id = rs.getInt("id");
				String title = rs.getString("title");
				String writer = rs.getString("writer");
				String content = rs.getString("content");
				String regDate = rs.getString("reg_date");
				int viewCnt = rs.getInt("view_cnt");

				Board board = new Board(id, title, writer, content, regDate, viewCnt);
				list.add(board);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			util.close(rs, stmt, conn);
		}

		return list;
	}

	@Override
	public Board selectOne(int id) {
		String sql = "SELECT * FROM board WHERE id = ?";

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		Board board = new Board();

		try {
			conn = util.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				board.setId(rs.getInt(1));
				board.setWriter(rs.getString(2));
				board.setTitle(rs.getString(3));
				board.setContent(rs.getString(4));
				board.setViewCnt(rs.getInt(5));
				board.setRegDate(rs.getString(6));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			util.close(conn, pstmt, rs);
		}

		return board;
	}

	@Override
	public void insertBoard(Board board) throws SQLException {
		// 미리 sql 문 만들어 놓고 PreparedStatement 객체를 만들어서
		// ?에 대신 값을 채우도록 하자
		String sql = "INSERT INTO board (title, writer, content) VALUES (?,?,?)";

		// DB 접속해서 사용하게끔 쓰자
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = util.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, board.getTitle()); // 첫 번째 물음표에 board가 가진 title 값을 세팅
			pstmt.setString(2, board.getWriter()); // 두 번째 물음표에 board가 가진 writer 값을 세팅
			pstmt.setString(3, board.getContent()); // 세 번째 물음표에 content 값을 세팅

			pstmt.executeUpdate(); // 데이터를 변경해야 하므로 update를 날린다
			// stmt.executeQuery(sql) 와는 다르다. 이미 sql문을 위에 다 만들어놓고 데이터 변경만 하면 되기 때문

		} finally {
			util.close(pstmt, conn);
		}
	}

	@Override
	public void deleteBoard(int id) throws SQLException {
		String sql = "DELETE FROM board WHERE id = ?";

		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = util.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id); // 첫 번째 자리에 id를 넣는다
			pstmt.executeUpdate();
		} finally {
			util.close(pstmt, conn);
		}
	}

	@Override
	public void updateBoard(Board board) throws SQLException {
		String sql = "UPDATE board SET title = ?, content = ? WHERE id = ?";
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = util.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, board.getTitle());
			pstmt.setString(2, board.getContent());
			pstmt.setInt(3, board.getId());
			pstmt.executeUpdate();
		} finally {
			util.close(pstmt, conn);
		}
	}

	@Override
	public void updateViewCnt(int id) throws SQLException {
		String sql = "UPDATE board SET view_cnt = view_cnt+1 WHERE id = ?";
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = util.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);
			pstmt.executeUpdate();
		} finally {
			util.close(pstmt, conn);
		}
	}

}
