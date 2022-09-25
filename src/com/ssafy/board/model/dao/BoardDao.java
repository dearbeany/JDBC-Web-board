package com.ssafy.board.model.dao;

import java.sql.SQLException;
import java.util.List;

import com.ssafy.board.model.dto.Board;

public interface BoardDao {
	// 전체 게시판의 내용(게시글) 다 가져온다
	public List<Board> selectAll();

	// ID에 해당하는 게시글 하나 가져온다
	public Board selectOne(int id);

	// 게시글을 등록한다
	public void insertBoard(Board board) throws SQLException;

	// 게시글을 삭제한다2
	public void deleteBoard(int id) throws SQLException;

	// 게시글을 수정한다
	public void updateBoard(Board board) throws SQLException;

	// 조회수를 증가한다
	public void updateViewCnt(int id) throws SQLException;

}
