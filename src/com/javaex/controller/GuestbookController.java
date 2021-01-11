package com.javaex.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.javaex.dao.GuestDao;
import com.javaex.util.WebUtil;
import com.javaex.vo.GuestVo;

/**
 * Servlet implementation class GuestbookController
 */
@WebServlet("/guestbook")
public class GuestbookController extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("GuestbookController");
		
		String action = request.getParameter("action");
		
		if("guestList".equals(action)) {
			System.out.println("방명록리스트 폼");
			
			//dao 클래스 ListAllGuest() 사용 --> 리스트 조회
			GuestDao guestDao = new GuestDao();
			List<GuestVo> guestList = guestDao.ListAllGuest();
			
			//데이터전달
			request.setAttribute("gList", guestList);
			
			//addList.jsp 포워드
			WebUtil.forward(request, response, "/WEB-INF/views/guestbook/addList.jsp");
			
		} else if("add".equals(action)) {
			System.out.println("방명록 작성");
			
			//guestDao --> guestInsert()
			//파라미터값 꺼내기
			//http://localhost:8088/mysite2/guestbook?name=김서영&pass=1234&content=안녕하세요&action=add
			String name = request.getParameter("name");
			String password = request.getParameter("pass");
			String content = request.getParameter("content");
			
			//GuestVo
			GuestVo guestVo = new GuestVo(name, password, content);
			
			//dao클래스 guestInsert(guestvo) 사용 --> 등록
			GuestDao guestDao = new GuestDao();
			guestDao.guestInsert(guestVo);
			
			//redirect
			WebUtil.redirect(request, response, "/mysite2/guestbook?action=guestList");
		} else if("guestDelete".equals(action)) {
			System.out.println("방명록 삭제 폼");
			
			//delegeForm.jsp 포워드
			WebUtil.forward(request, response, "/WEB-INF/views/guestbook/deleteForm.jsp");
		} else if("delete".equals(action)) {
			System.out.println("방명록 삭제");
			
			//guestDao --> guestDelete()
			//파라미터값 꺼내기
			int no = Integer.parseInt(request.getParameter("no"));
			String password = request.getParameter("pass");
			
			//new guestVo
			GuestVo guestVo = new GuestVo(no, password);
			
			//dao 클래스 guestDelete(guestvo) 사용-->삭제
			GuestDao guestDao = new GuestDao();
			
			int count = guestDao.guestDelete(guestVo);
			
			if (count == 1) {	//성공
				//redirect
				WebUtil.redirect(request, response, "/mysite2/guestbook?action=guestList");
			} else {	//실패
				//deleteform 화면에 비밀번호 틀린문구를 보여주는 조건을 위해 count값을 보내준다.
				request.setAttribute("result", count);
				//deleteForm.jsp 포워드
				WebUtil.forward(request, response, "/WEB-INF/views/guestbook/deleteForm.jsp");
			}

		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
