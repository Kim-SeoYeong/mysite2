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
			
			System.out.println("no" + Integer.parseInt(request.getParameter("no")));

			//파라미터 값 가져오기
			int no = Integer.parseInt(request.getParameter("no"));

			//Dao --> getBoard() 특정한 게시판글 조회
			BoardDao boardDao = new BoardDao();
			
			//읽을때마다 조회수 업데이트를 시켜주기 위해
			boardDao.hitUpdate(no);
			
			//System.out.println("업데이트로직 탔는지");
			BoardVo boardVo = boardDao.getBoard(no);
			
			//데이터 전달
			request.setAttribute("bvo", boardVo);
			
			//포워드 --> boarRead.jsp
			WebUtil.forward(request, response, "/WEB-INF/views/board/boardRead.jsp");
			
		} else if ("writeBoard".equals(action)) {
			System.out.println("게시판 글쓰기 폼");
			
			//포워드 --> boardWriteForm.jsp
			WebUtil.forward(request, response, "/WEB-INF/views/board/boardWriteForm.jsp");
			
		} else if ("write".equals(action)) {
			System.out.println("게시판 글 등록");
			
			//파라미터 값 가져오기
			//세션에 있는 no
			HttpSession session = request.getSession();
			UserVo authUser = (UserVo)session.getAttribute("authUser");
			
			int userNo = authUser.getNo();
			String title = request.getParameter("bTitle");
			String content = request.getParameter("bContent");
			
			//BoardVo
			BoardVo boardVo = new BoardVo(title, content, userNo);
			
			//Dao --> boardInserg() --> 게시글 작성
			BoardDao boardDao = new BoardDao();
			boardDao.boardInsert(boardVo);
			
			//redirect --> action = boardList로
			WebUtil.redirect(request, response, "/mysite2/Board?action=boardList");
			
		} else if ("boardModify".equals(action)) {
			System.out.println("게시글 수정하기 폼");
			
			//파라미터 값 가져오기
			int no = Integer.parseInt(request.getParameter("no"));
			
			//Dao --> getBoard() --> 해당 게시물 데이터 조회해오자
			BoardDao boardDao = new BoardDao();
			
			//vo
			BoardVo boardVo = boardDao.getBoard(no);
			
			//데이터 전달
			request.setAttribute("bvo", boardVo);
			
			//포워드 --> boardModifyForm.jsp
			WebUtil.forward(request, response, "/WEB-INF/views/board/boardModifyForm.jsp");
			
		} else if ("modify".equals(action)) {
			System.out.println("게시글 수정하기");
			
			//파라미터 값 가져오기
			String title = request.getParameter("bTitle");
			String content = request.getParameter("bContent");
			int no = Integer.parseInt(request.getParameter("no"));
			
			//BoardVo
			BoardVo boardVo = new BoardVo(no, title, content);
			
			//Dao --> boardUpdate() --> 게시물 수정
			BoardDao boardDao = new BoardDao();
			boardDao.boardUpdate(boardVo);
			
			//redirect	--> action = boardList
			WebUtil.redirect(request, response, "/mysite2/Board?action=boardList");
			
		} else if ("delete".equals(action)) {
			System.out.println("게시글 삭제하기");
			
			//파라미터 값 가져오기
			int no = Integer.parseInt(request.getParameter("no"));
			
			//Dao --> boardDelete() --> 게시물 삭제
			BoardDao boardDao = new BoardDao();
			boardDao.boardDelete(no);
			
			//redirect --> action = boardList
			WebUtil.redirect(request, response, "/mysite2/Board?action=boardList");
		} else if ("search".equals(action)) {
			System.out.println("게시글 검색하기");
			
			//파라미터 값 가져오기
			String str = request.getParameter("searchTxt");
			
			//Dao --> getBoardList(str) --> 검색어에 맞는 게시물 조회하기
			BoardDao boardDao = new BoardDao();
			List<BoardVo> searchList = boardDao.getBoardList(str);
			
			//데이터 전송
			request.setAttribute("sList", searchList);
			//포워드 --> boardList.jsp
			WebUtil.forward(request, response, "/WEB-INF/views/board/boardList.jsp");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
