<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

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

		<!--  header + navi 공통으로 옮겼음 -->
		<c:import url="/WEB-INF/views/include/header.jsp"></c:import>
		
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
							<span class="text-large bold">${userVo.id}</span>	<!-- requestScope.userVo.id -->
						</div>

						<!-- 비밀번호 -->
						<div class="form-group">
							<label class="form-text" for="input-pass">패스워드</label> 	<!-- requestScope.userVo.password -->
							<input type="text" id="input-pass" name="userpass" value="${userVo.password}" placeholder="비밀번호를 입력하세요"	>
						</div>

						<!-- 이메일 -->
						<div class="form-group">
							<label class="form-text" for="input-name">이름</label> 	<!-- requestScope.userVo.name -->
							<input type="text" id="input-name" name="username" value="${userVo.name}" placeholder="이름을 입력하세요">
						</div>

						<!-- //나이 -->
						<div class="form-group">
							<span class="form-text">성별</span> 
							<!-- 성별을 남,여일때 구분지어서 체크해줘야하기 때문에 조건을 주자. -->
							<!-- requestScope.userVo.gender -->
							<c:if test="${userVo.gender == 'male'}">
								<label for="rdo-male">남</label>
								<input type="radio" id="rdo-male" name="usergender" value="male" checked> 
								<label for="rdo-female">여</label> 	
								<input type="radio" id="rdo-male" name="usergender" value="female"> 
							</c:if>
							<c:if test="${userVo.gender == 'female'}">
								<label for="rdo-male">남</label>
								<input type="radio" id="rdo-male" name="usergender" value="male" > 
								<label for="rdo-female">여</label> 	
								<input type="radio" id="rdo-male" name="usergender" value="female" checked> 
							</c:if>
							
						</div>

						<!-- 버튼영역 -->
		                <div class="button-area">
		                    <button type="submit" id="btn-submit">회원정보수정</button>
		                </div>
	
						<input type="text" name="action" value="update">
					</form>
				
				
				</div>
				<!-- //modifyForm -->
			</div>
			<!-- //user -->
		</div>
		<!-- //content  -->
		<div class="clear"></div>
		
		<!-- footer -->
		<c:import url="/WEB-INF/views/include/footer.jsp"></c:import>
		
	</div>
	<!-- //wrap -->

</body>

</html>