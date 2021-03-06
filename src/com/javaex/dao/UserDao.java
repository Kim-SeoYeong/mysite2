package com.javaex.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.javaex.vo.UserVo;

public class UserDao {
	//필드
	//0. import java.sql.*;
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	
	private String driver = "oracle.jdbc.driver.OracleDriver";
	private String url = "jdbc:oracle:thin:@localhost:1521:xe";
	private String id = "webdb";
	private String pw = "webdb";
	
	//생성자
	//메소드-g/s
	//메소드-일반
	
	//DB접속
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
	
	public int insert(UserVo userVo) {
		int count = 0;
		
		getConnection();
		try {
			// 3. SQL문 준비 / 바인딩 / 실행      
			/*
				insert into users
				values (seq_users_id.nextval, 'aaa', '1234', '김서영', 'female'); 
			*/
			String query = "";
			query += " insert into users ";
			query += " values (seq_users_id.nextval, ?, ?, ?, ?) ";
			
			System.out.println(query);
			
			pstmt = conn.prepareStatement(query);	//쿼리로 만들기
			
			pstmt.setString(1, userVo.getId());	//?(물음표) 중 1번째, 순서중요
			pstmt.setString(2, userVo.getPassword());  //?(물음표) 중2번째, 순서중요
			pstmt.setString(3, userVo.getName());	//?(물음표) 중 3번째, 순서중요
			pstmt.setString(4, userVo.getGender());	//?(물음표) 중 4번쨰, 순서중요
			
			count = pstmt.executeUpdate();	//쿼리문 실행
			
			// 4. 결과처리 
			System.out.println("[ " + count + " 건 등록되었습니다. ]");
			
		} catch (SQLException e) {
		    System.out.println("error:" + e);
		}
		
		close();
		
		return count;
	}
	
	//로그인할때 세션 정보용
	public UserVo getUser(String id, String pw) {
		UserVo userVo = null;
		
		getConnection();
		
		try {
			/*
				select  no,
				        id,
				        password,
				        name,
				        gender
				from users
				where id = 'aaa'
				and password = '1234';
			*/
			String query = "";
			query += " select  no,       ";
			query += "         name      ";
			query += " from users        ";
			query += " where id = ?      ";
			query += " and password = ?  ";
			
			pstmt = conn.prepareStatement(query);	//쿼리로 만들기
			
			pstmt.setString(1, id);
			pstmt.setString(2, pw);
			
			rs = pstmt.executeQuery();	//쿼리문 실행
			
			while(rs.next()) {
				int no = rs.getInt("no"); 
				String name = rs.getString("name");
				
				userVo = new UserVo(no, name);
			}
			
		} catch (SQLException e) {
		    System.out.println("error:" + e);
		}
		
		close();
		
		return userVo;
	}
	
	//메소드 오버로딩
	//no에 맞는 한 사람의 데이터를 조회해오기 위해
	public UserVo getUser(int no) {
		UserVo userVo = null;
		
		getConnection();
		
		try {
			/*
				select  no,
				        id,
				        password,
				        name,
				        gender
				from users
				where no = 1;
			*/
			String query = "";
			query += " select  no,       ";
			query += "         id,       ";
			query += "         password, ";
			query += "         name,     ";
			query += "         gender    ";
			query += " from users        ";
			query += " where no = ?      ";
			
			pstmt = conn.prepareStatement(query);	//쿼리로 만들기
			
			pstmt.setInt(1, no);
			
			rs = pstmt.executeQuery();	//쿼리문 실행
			
			while(rs.next()) {
				int userno = rs.getInt("no"); 
				String id = rs.getString("id");
				String pw = rs.getString("password");
				String name = rs.getString("name");
				String gender = rs.getString("gender");
				
				userVo = new UserVo(userno, id, pw, name, gender);
			}
			
		} catch (SQLException e) {
		    System.out.println("error:" + e);
		}
		
		close();
		
		return userVo;
	}
	
	//회원정보 수정 update
	public int userUpdate(UserVo uvo) {
		int count = 0;
		
		getConnection();
		
		try {
			/*
				update users
				set password = '1111',
				    name = '김서영이당',
				    gender = 'male'
				where no = 1;
			*/
			String query = "";
			query += " update users      ";
			query += " set password = ?, ";
			query += "     name = ?,     ";
			query += "     gender = ?    ";
			query += " where no = ?      ";
			
			pstmt = conn.prepareStatement(query);	//쿼리로 만들기
			
			pstmt.setString(1, uvo.getPassword());
			pstmt.setString(2, uvo.getName());
			pstmt.setString(3, uvo.getGender());
			pstmt.setInt(4, uvo.getNo());
			
			//결과
			count = pstmt.executeUpdate();
			System.out.println("[ " + count + " 건 수정됨.]");
			
		} catch (SQLException e) {
		    System.out.println("error:" + e);
		}
		
		close();
		
		return count;
	}
	
	
}
