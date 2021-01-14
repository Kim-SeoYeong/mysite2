<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import = "com.javaex.vo.UserVo" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link href="/mysite2/assets/css/mysite.css" rel="stylesheet" type="text/css">
<link href="/mysite2/assets/css/main.css" rel="stylesheet" type="text/css">

</head>

<body>
	<div id="wrap">
		<div id="header">
			<h1><a href="">MySite</a></h1>
			
			<!-- 로그인이 안되었을 때 -->
			<c:choose>
				<c:when test="${empty sessionScope.authUser}">
					<ul>
						<li><a href="/mysite2/user?action=loginForm">로그인</a></li>
						<li><a href="/mysite2/user?action=joinForm">회원가입</a></li>
					</ul>
				</c:when>
				<c:otherwise>
					<!-- 로그인 성공했을 때 (= session 영역에 authUser이라는 값이 있으면) -->
					<ul>
						<li>${sessionScope.authUser.name}님 안녕하세요^^</li> <!-- authUser.name도 가능! -->
						<li><a href="/mysite2/user?action=logout">로그아웃</a></li>
						<li><a href="/mysite2/user?action=updateForm">회원정보수정</a></li>
					</ul>
				</c:otherwise>
			</c:choose>
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
	</div>
	<!-- //wrap -->
</body>

</html>