<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import = "com.javaex.vo.UserVo" %>

<%
	UserVo authUser = (UserVo)session.getAttribute("authUser");
%>
<% 
	int no = Integer.parseInt(request.getParameter("no"));

	//delete 결과가 성공인지 실패인지 받아줄 변수
	int count;

	try {
		count = (int)request.getAttribute("result");	//object 형식이기때문에 int로 형변환 시켜줌
	} catch(NullPointerException e) {
		count = 1;
	}
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link href="/mysite2/assets/css/mysite.css" rel="stylesheet" type="text/css">
<link href="/mysite2/assets/css/guestbook.css" rel="stylesheet" type="text/css">

</head>

<body>
	<div id="wrap">

		<div id="header">
			<h1>
				<a href="">MySite</a>
			</h1>

			<!-- 로그인이 안되었을 때 -->
			<%if(authUser==null) { %>
				<ul>
					<li><a href="/mysite2/user?action=loginForm">로그인</a></li>
					<li><a href="/mysite2/user?action=joinForm">회원가입</a></li>
				</ul>
			<%} else { %>
				<!-- 로그인 성공했을 때 (= session 영역에 authUser이라는 값이 있으면) -->
				<ul>
					<li><%=authUser.getName() %> 님 안녕하세요^^</li>
					<li><a href="/mysite2/user?action=logout">로그아웃</a></li>
					<li><a href="/mysite2/user?action=updateForm">회원정보수정</a></li>
				</ul>
			<%} %>
		</div>
		<!-- //header -->

		<div id="nav">
			<ul>
				<li><a href="/mysite2/guestbook?action=guestList">방명록</a></li>
				<li><a href="">갤러리</a></li>
				<li><a href="">게시판</a></li>
				<li><a href="">입사지원서</a></li>
			</ul>
			<div class="clear"></div>
		</div>
		<!-- //nav -->

		<div id="aside">
			<h2>방명록</h2>
			<ul>
				<li>일반방명록</li>
				<li>ajax방명록</li>
			</ul>
		</div>
		<!-- //aside -->

		<div id="content">
			
			<div id="content-head">
            	<h3>일반방명록</h3>
            	<div id="location">
            		<ul>
            			<li>홈</li>
            			<li>방명록</li>
            			<li class="last">일반방명록</li>
            		</ul>
            	</div>
                <div class="clear"></div>
            </div>
            <!-- //content-head -->

			<div id="guestbook">
				<form action="/mysite2/guestbook" method="get">
					<table id="guestDelete">
						<colgroup>
							<col style="width: 10%;">
							<col style="width: 40%;">
							<col style="width: 25%;">
							<col style="width: 25%;">
						</colgroup>
						<tr>
							<td>비밀번호</td>
							<td><input type="password" name="pass"></td>
							<td class="text-left"><button type="submit">삭제</button></td>
							<td><a href="/mysite2/guestbook/action=guestList">[메인으로 돌아가기]</a></td>
						</tr>
					</table>
					<%if (count == 0) { %>
						<h2>비밀번호를 잘못 입력하셨습니다. 다시 입력해주세요!</h2><br>
					<%} %>
					<input type='hidden' name="action" value="delete">
					<input type='hidden' name="no" value="<%=no%>">
				</form>
				
			</div>
			<!-- //guestbook -->
		</div>
		<!-- //content  -->
		<div class="clear"></div>
		
		<div id="footer">
			Copyright ⓒ 2020 황일영. All right reserved
		</div>
		<!-- //footer -->

	</div>
	<!-- //wrap -->

</body>

</html>
