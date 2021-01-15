package com.javaex.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.javaex.dao.BoardDao;
import com.javaex.util.WebUtil;
import com.javaex.vo.BoardVo;
import com.javaex.vo.UserVo;

/**
 * Servlet implementation class BoardController
 */
@WebServlet("/Board")
public class BoardController extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("BoardController");
		
		String action = request.getParameter("action");
		
		if("boardList".equals(action)) {
			System.out.println("게시판 목록");
			
			//Dao --> getBoardList() 게시판 리스트 조회
			BoardDao boardDao = new BoardDao();
			List<BoardVo> boardList = boardDao.getBoardList();
			
			//데이터 전달
			request.setAttribute("bList", boardList);
			
			//포워드 --> boardList.jsp
			WebUtil.forward(request, response, "/WEB-INF/views/board/boardList.jsp");
		} else if ("readBoard".equals(action)) {
			System.out.println("게시판글 읽기");
			
			//파라미터 값 가져오기
			int no = Integer.parseInt(request.getParameter("no"));
			int hit = Integer.parseInt(request.getParameter("hit"));
			
			//Dao --> getBoard() 특정한 게시판글 조회
			BoardDao boardDao = new BoardDao();
			
			//읽을때마다 조회수 업데이트를 시켜주기 위해
			boardDao.hitUpdate(hit, no);
			//System.out.println("업데이트로직 탔는지");
			BoardVo boardVo = boardDao.getBoard(no);
			//데이터 전달
			request.setAttribute("bvo", boardVo);
			//session.setAttribute(name, value);
			
			//포워드 --> boarRead.jsp
			WebUtil.forward(request, response, "/WEB-INF/views/board/boardRead.jsp");
		} else if ("writeBoard".equals(action)) {
			System.out.println("게시판 글쓰기 폼");
			
			//포워드 --> boardWriteForm.jsp
			WebUtil.forward(request, response, "/WEB-INF/views/board/boardWriteForm.jsp");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
