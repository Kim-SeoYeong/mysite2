package com.javaex.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.javaex.vo.BoardVo;

public class BoardDao {
	//0. import java.sql.*;
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
		
	private String driver = "oracle.jdbc.driver.OracleDriver";
	private String url = "jdbc:oracle:thin:@localhost:1521:xe";
	private String id = "webdb";
	private String pw = "webdb";
	
	// DB접속
	private void getConnection() {
		try {
			// 1. JDBC 드라이버 (Oracle) 로딩
			Class.forName(driver);

			// 2. Connection 얻어오기
			conn = DriverManager.getConnection(url, id, pw);

		} catch (ClassNotFoundException e) {
			System.out.println("error: 드라이버 로딩 실패 - " + e);
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
	}

	private void close() {
		// 5. 자원정리
		try {
			if (rs != null) {
				rs.close();
			}
			if (pstmt != null) {
				pstmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
	}
			
	//게시판 목록 조회
	public List<BoardVo> getBoardList() {
		List<BoardVo> boardList = new ArrayList<BoardVo>();
		
		getConnection();
	
		try {
			/*
				select  board.no,
				        board.title,
				        users.name,
				        board.content,
				        board.hit,
				        board.reg_date
				from users, board
				where users.no = board.no
				order by board.reg_date desc;
			*/
			
			String query = "";
			query += " select  board.no,         ";
			query += "         board.title,      ";
			query += "         board.content,    ";
			query += "         users.name,       ";
			query += "         board.hit,        ";
			query += "         board.reg_date    ";
			query += " from users, board         ";
			query += " where users.no = board.user_no ";
			query += " order by board.reg_date desc ";
			
			pstmt = conn.prepareStatement(query);	//쿼리로 만들기
			rs = pstmt.executeQuery();

			//결과 처리
			while(rs.next()) {	
				int no = rs.getInt("no");
				String title = rs.getString("title");
				String content = rs.getString("content");
				String name = rs.getString("name");
				int hit = rs.getInt("hit");
				String regDate = rs.getString("reg_date");
				
				BoardVo boardVo = new BoardVo(no, title, content, name, hit, regDate);
				boardList.add(boardVo);
			}
			
		} catch(SQLException e) {
			System.out.println("error:" + e);
		}
		
		close();
		
		return boardList;
	}
	
	//게시판 글 한개 조회(select)
	public BoardVo getBoard(int no) {
		
		BoardVo boardVo = null;
		
		getConnection();
		
		try {
			/*
				select  users.name,
				        board.hit,
				        board.reg_date,
				        board.title,
				        board.content
				        board.user_no
				from users, board
				where users.no = board.no
				and board.no = 1;
			*/
			String query = "";
			query += " select  board.no,        ";
			query += "         users.name,      ";
			query += "         board.hit,       ";
			query += "         board.reg_date , ";
			query += "         board.title,     ";
			query += "         board.content,   ";
			query += "         board.user_no    ";
			query += " from users, board ";
			query += " where users.no = board.user_no ";
			query += " and board.no = ? ";
			
			pstmt = conn.prepareStatement(query);	//쿼리로 만들기
			pstmt.setInt(1, no);
			
			rs = pstmt.executeQuery();
			
			//결과처리
			while(rs.next()) {
				int bookNo = rs.getInt("no");
				String name = rs.getString("name");
				int hit = rs.getInt("hit");
				String regDate = rs.getString("reg_date");
				String title = rs.getString("title");
				String content = rs.getString("content");
				int userNo = rs.getInt("user_no");

				boardVo = new BoardVo(bookNo, title, content, name, hit, regDate, userNo);
			}
			
		} catch(SQLException e) {
			System.out.println("error:" + e);
		}
		
		close();
		
		return boardVo;
	}
		
	//조회수 udate 문
	public int hitUpdate(int hit, int no) {
		int count = 0;
		//조회할때마다 조회수를 1씩 올려주기 위해서
		hit++;
		
		getConnection();
		
		try {
			/*
				update board
				set hit = 1
				where no = 1;
			*/
			String query = "";
			query += " update board ";
			query += " set hit = ?  ";
			query += " where no = ? ";
			
			pstmt = conn.prepareStatement(query);	//쿼리로 만들기
			
			pstmt.setInt(1, hit);
			pstmt.setInt(2, no);
			
			count = pstmt.executeUpdate();
			
			//결과처리
			System.out.println("조회수 업데이트 완료!!!");
			
		} catch(SQLException e) {
			System.out.println("error:" + e);
		}
		
		close();
		
		return count;
	}
	
	//게시판 글쓰기 insert문
	public int boardInsert(BoardVo boardVo) {
		int count = 0;
		
		getConnection();
		
		try {
			/*
				insert into board
				values(seq_board_no.nextval, '반가워요', '테스트내용', default, sysdate, 1);
			*/
			String query = "  ";
			query += " insert into board ";
			query += " values(seq_board_no.nextval, ?, ?, default, sysdate, ?) ";
			
			pstmt = conn.prepareStatement(query);	//쿼리 만들기
			
			pstmt.setString(1, boardVo.getTitle());
			pstmt.setString(2, boardVo.getContent());
			pstmt.setInt(3, boardVo.getUserNo());
			
			count = pstmt.executeUpdate();
			
			//결과처리
			System.out.println(count + "건 insert 완료.");
			
		} catch(SQLException e) {
			System.out.println("error:" + e);
		}
		
		close();
		
		return count;
	}
	
	//게시글 수정하기(update)
	public int boardUpdate(BoardVo boardVo) {
		int count = 0;
		getConnection();
		
		try {
			/*
			 	update board
				set title = '김서영테스트중',
				content = '내용테스트!!'
				where no = 1;
			*/
			
			String query = "";
			query += " update board   ";
			query += " set title = ?, ";
			query += " content = ?    ";
			query += " where no = ? ";
			
			pstmt = conn.prepareStatement(query);
			
			pstmt.setString(1, boardVo.getTitle());
			pstmt.setString(2, boardVo.getContent());
			pstmt.setInt(3, boardVo.getNo());
			
			count = pstmt.executeUpdate();
			
			//결과
			System.out.println(count + "건 update 완료!!");
			
		} catch(SQLException e) {
			System.out.println("error:" + e);
		}
		
		close();
		
		return count;
	}
	
	
	
	
	
	
}
