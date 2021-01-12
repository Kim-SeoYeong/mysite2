package com.javaex.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.javaex.dao.UserDao;
import com.javaex.util.WebUtil;
import com.javaex.vo.UserVo;
import com.sun.javafx.scene.traversal.WeightedClosestCorner;

/**
 * Servlet implementation class UserController
 */
@WebServlet("/user")
public class UserController extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("UserController");
		
		String action = request.getParameter("action");
		System.out.println("action = " + action);
		
		if("joinForm".equals(action)) {
			//joinForm.jsp 포워드
			System.out.println("회원가입폼");
			WebUtil.forward(request, response, "/WEB-INF/views/user/joinForm.jsp");
			
		} else if("join".equals(action)) {
			System.out.println("회원가입");
			
			//dao --> insert() 저장
			//  파라미터 값 꺼내기
			//http://localhost:8088/mysite2/user?uid=jus&pw=1234&uname=정우성&gender=male&action=join
			String id = request.getParameter("uid");
			String password = request.getParameter("pw");
			String name = request.getParameter("uname");
			String gender = request.getParameter("gender");
			
			//  vo로 묶기
			UserVo userVo = new UserVo(id, password, name, gender);
			System.out.println(userVo.toString());
			
			//	dao클래스 insert(vo) 사용 --> 저장 --> 회원가입
			UserDao userDao = new UserDao();
			userDao.insert(userVo);
			
			//포워드 --> joinOk.jsp
			WebUtil.forward(request, response, "/WEB-INF/views/user/joinOk.jsp");
		} else if("loginForm".equals(action)) {
			System.out.println("로그인폼");
			
			//포워드 --> loginForm.jsp
			WebUtil.forward(request, response, "/WEB-INF/views/user/loginForm.jsp");
		} else if("login".equals(action)) {
			System.out.println("로그인");
			
			//파라미터 id, pw
			String id = request.getParameter("id");
			String pw = request.getParameter("pw");
			
			//dao --> getUser()
			UserDao userDao = new UserDao();
			UserVo authVo = userDao.getUser(id, pw);
			System.out.println(authVo);	//id pw --> no name값이 있는 authoVo
			
			if(authVo == null) {	//로그인 실패
				System.out.println("로그인 실패");
				
				//리다이렉트 --> 다시 로그인폼으로 요청
				WebUtil.redirect(request, response, "/mysite2/user?action=loginForm");
				
			} else {	//성공일때
				System.out.println("로그인 성공");
				
				//세션영역에 필요한(vo) 넣어준다.
				HttpSession session = request.getSession();
				session.setAttribute("authUser", authVo);
					
				WebUtil.redirect(request, response, "/mysite2/main");
			}

		} else if("logout".equals(action)) {
			System.out.println("로그아웃");
			
			//세션영역에 있는 vo를 삭제해야함.
			HttpSession session = request.getSession();
			session.removeAttribute("authUser");
			session.invalidate();
			
			WebUtil.redirect(request, response, "/mysite2/main");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
