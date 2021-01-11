package com.javaex.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.javaex.vo.GuestVo;


public class GuestDao {
	
	//필드
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
			//JDBC 드라이버 (Oracle) 로딩                      
			Class.forName(driver);                           
						                                                 
			//Connection 얻어오기                            
			conn = DriverManager.getConnection(url, id, pw); 
			
		} catch (ClassNotFoundException e) {
		    System.out.println("error: 드라이버 로딩 실패 - " + e);
		} catch (SQLException e) {
		    System.out.println("error:" + e);
		}
	}
	
	private void close() {
		//자원정리
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
	
	//1명만 조회해 오기
	public GuestVo getGuest(int num) {
		GuestVo guestVo = null;
		
		getConnection();
		
		try {
			/*
			select  no,
			        name,
			        password,
			        content,
			        to_char(reg_date, 'YYYY-MM-DD hh24:mi:ss')
			from guestbook
			where no = 2;
			*/
			String query = "";
			query += " select  no, ";
			query += " 		   name, ";
			query += "         password, ";
			query += "         content, ";
			query += "         reg_date ";
			query += " from guestbook ";
			query += " where no = ?";
			
			System.out.println(query);
			
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, num);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				int no = rs.getInt("no");
				String name = rs.getString("name");
				String password = rs.getString("password");
				String content = rs.getString("content");
				String regDate = rs.getString("reg_date");
				
				guestVo = new GuestVo(no, name, password, content, regDate);
			}
		} catch(SQLException e) {
			System.out.println("error:" + e);
		}
		
		
		close();
		
		return guestVo;
	}
	
	//guest 삭제(delete)
	public int guestDelete(GuestVo guestVo) {
		getConnection();
		
		int count = 0;
		
		try {
			/*
			delete from guestbook
			where no = 3
			and password = '1111';
			*/
			String query = "";
			query += " delete from guestbook ";
			query += " where no = ? ";
			query += " and   password = ? ";
			
			System.out.println(query);
			
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, guestVo.getNo());
			pstmt.setString(2, guestVo.getPassword());
			
			count = pstmt.executeUpdate();
			
			//결과처리
			System.out.println("[ " + count + "건 삭제 ]");
			
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		close();
		return count;
	}
	
	//guest 추가(insert)
	public int guestInsert(GuestVo guestVo) {
		getConnection();
		int count = 0;
		
		try {
			/*
			insert into guestbook
			values (seq_guestbook_id.nextval, '이정재', '1234', '안녕하세요', sysdate);
			*/
			String query = "";
			query += " insert into guestbook ";
			query += " values (seq_guestbook_id.nextval, ?, ?, ?, sysdate)";
			System.out.println(query);
			pstmt = conn.prepareStatement(query);
			
			pstmt.setString(1, guestVo.getName());
			pstmt.setString(2, guestVo.getPassword());
			pstmt.setString(3, guestVo.getContent());
			
			count = pstmt.executeUpdate();
			
			//결과처리
			System.out.println("[ " + count + "건 저장 ]");
			
		} catch (SQLException e) {
	        System.out.println("error:" + e);
		}
		close();
		return count;
	}
	
	//리스트 목록 조회(select)
	public List<GuestVo> ListAllGuest() {
		List<GuestVo> guestList = new ArrayList<GuestVo>();
		
		getConnection();
		
		try {
			/*
			select  no,
			        name,
			        password,
			        content,
			        to_char(reg_date, 'YYYY-MM-DD hh24:mi:ss')
			from guestbook;
			*/
			String query = "";
			query += " select  no, ";
			query += " 		   name, ";
			query += "         password, ";
			query += "         content, ";
			query += "         reg_date ";
			query += " from guestbook ";
			
			System.out.println(query);
			pstmt = conn.prepareStatement(query);
			rs = pstmt.executeQuery();
			
			//결과처리
			while(rs.next()) {
				int no = rs.getInt("no");
				String name = rs.getString("name");
				String password = rs.getString("password");
				String content = rs.getString("content");
				String regDate = rs.getString("reg_date");
				
				GuestVo guestVo = new GuestVo(no, name, password, content, regDate);
				guestList.add(guestVo);
			}
			
		} catch(SQLException e) {
			System.out.println("error:" + e);
		}
		
		close();
		
		return guestList;
	}
	
}
