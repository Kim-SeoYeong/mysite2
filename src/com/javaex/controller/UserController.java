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
				WebUtil.redirect(request, response, "/mysite2/user?action=loginForm&result=fail");
				
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
		} else if("updateForm".equals(action)) {
			//강사님 소스
			System.out.println("회원정보수정  폼");
			
			//세션에 있는 no
			HttpSession session = request.getSession();
			UserVo authUser = (UserVo)session.getAttribute("authUser");
			
			//로그인 안한 상태면 getNo를 가져올 수 없기 때문에 에러남 어차피 회원정보수정은 로그인했을때만 가능하도록 만들어야함.
			int no = authUser.getNo();
			
			//회원정보 가져오기
			UserDao userDao = new UserDao();
			UserVo userVo = userDao.getUser(no);
			
			System.out.println("getUser(no)-->" + userVo);
			
			//userVo를 세션에 넣으면 메모리가 무거워지기때문에 안됨.
			//이미 우린 authUser을 만들었기때문에 굳이 userVo를 다시 세션에 넣을 필요가 없음.
			//userVo 전달 포워드
			request.setAttribute("userVo", userVo);
			
			//포워드 --> modifyForm.jsp
			WebUtil.forward(request, response, "/WEB-INF/views/user/modifyForm.jsp");
			
		} else if("update".equals(action)) {
			System.out.println("회원정보수정");
			
			//파라미터 값 가져오기
			String password = request.getParameter("userpass");
			String name = request.getParameter("username");
			String gender = request.getParameter("usergender");
			
			//세션에있는 no 가져오기
			HttpSession session = request.getSession();
			UserVo authUser = (UserVo)session.getAttribute("authUser");
			int no = authUser.getNo();
			
			//userVo로 만들기
			UserVo userVo = new UserVo(no, password, name, gender);
			//UserVo userVo = new UserVo(no, "", password, name, gender);	이렇게도 사용할 수 있음.
			
			System.out.println(userVo);
			
			//dao --> update()실행
			UserDao userDao = new UserDao();
			userDao.userUpdate(userVo);
			
			//main화면에서는 다시 authUser.getName()을 보여줘야하기 때문에
			//session의 정보도 update 해줘야함.
			//session의 name 값만 바꿔주면됨.
			authUser.setName(name);	//체크해볼것
			
			//main화면으로 redirect
			WebUtil.redirect(request, response, "/mysite2/main");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
