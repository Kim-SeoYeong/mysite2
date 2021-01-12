<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import = "com.javaex.vo.UserVo" %>

<%
	//기존 no와 Name만 조회해올 수 있는 authUser
	UserVo authUser = (UserVo)session.getAttribute("authUser");
	//authUser에서는 ID를 조회해올 수 없기때문에 ID가 조회가능한 uvo를 가져와서 userVo에 담아줌.
	UserVo userVo = (UserVo)session.getAttribute("uvo");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link href="/mysite2/assets/css/mysite.css" rel="stylesheet" type="text/css">
<link href="/mysite2/assets/css/user.css" rel="stylesheet" type="text/css">

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
				<li><a href="">방명록</a></li>
				<li><a href="">갤러리</a></li>
				<li><a href="">게시판</a></li>
				<li><a href="">입사지원서</a></li>
			</ul>
			<div class="clear"></div>
		</div>
		<!-- //nav -->

		<div id="aside">
			<h2>회원</h2>
			<ul>
				<li>회원정보</li>
				<li>로그인</li>
				<li>회원가입</li>
			</ul>
		</div>
		<!-- //aside -->

		<div id="content">
			
			<div id="content-head">
            	<h3>회원정보</h3>
            	<div id="location">
            		<ul>
            			<li>홈</li>
            			<li>회원</li>
            			<li class="last">회원정보</li>
            		</ul>
            	</div>
                <div class="clear"></div>
            </div>
             <!-- //content-head -->

			<div id="user">
				<div id="modifyForm">
					<form action="/mysite2/user" method="get">

						<!-- 아이디 -->
						<div class="form-group">
							<label class="form-text" for="input-uid">아이디</label> 
							<!-- userVo의 Id를 조회함. -->
							<span class="text-large bold"><%=userVo.getId() %></span>
						</div>

						<!-- 비밀번호 -->
						<div class="form-group">
							<label class="form-text" for="input-pass">패스워드</label> 
							<input type="text" id="input-pass" name="userpass" value="<%=userVo.getPassword() %>" placeholder="비밀번호를 입력하세요"	>
						</div>

						<!-- 이메일 -->
						<div class="form-group">
							<label class="form-text" for="input-name">이름</label> 
							<input type="text" id="input-name" name="username" value="<%=userVo.getName() %>" placeholder="이름을 입력하세요">
						</div>

						<!-- //나이 -->
						<div class="form-group">
							<span class="form-text">성별</span> 
							<!-- 성별을 남,여일때 구분지어서 체크해줘야하기 때문에 조건을 주자. -->
							<%if ((userVo.getGender()).equals("male")) {%>
								<label for="rdo-male">남</label>
								<input type="radio" id="rdo-male" name="usergender" value="male" checked> 
								<label for="rdo-female">여</label> 	
								<input type="radio" id="rdo-male" name="usergender" value="female"> 
							<%} else if((userVo.getGender()).equals("female")) {%>
								<label for="rdo-male">남</label>
								<input type="radio" id="rdo-male" name="usergender" value="male" > 
								<label for="rdo-female">여</label> 	
								<input type="radio" id="rdo-male" name="usergender" value="female" checked> 
							<%} %>
							
						</div>

						<!-- 버튼영역 -->
		                <div class="button-area">
		                    <button type="submit" id="btn-submit">회원정보수정</button>
		                </div>
						
						<!-- update를 하려면 조건에 no값이 필요해서 userno라는 이름으로 만들음. -->
						<input type="text" name="userno" value="<%=authUser.getNo() %>">
						
						<input type="text" name="action" value="update">
					</form>
				
				
				</div>
				<!-- //modifyForm -->
			</div>
			<!-- //user -->
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