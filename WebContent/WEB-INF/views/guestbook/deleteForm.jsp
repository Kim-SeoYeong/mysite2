<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

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

		<!--  header + navi 공통으로 옮겼음 -->
		<c:import url="/WEB-INF/views/include/header.jsp"></c:import>

		<!-- aside 공통으로 옮김 -->
		<c:import url="/WEB-INF/views/include/aside.jsp"></c:import>

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
							<td><a href="/mysite2/guestbook?action=guestList">[메인으로 돌아가기]</a></td>
						</tr>
					</table>
					<c:if test="${requestScope.result == 0}">
						<p>비밀번호를 잘못 입력하셨습니다. 다시 입력해주세요!</p><br>
					</c:if>
					<input type='hidden' name="action" value="delete">
					<input type='hidden' name="no" value="${param.no}">
				</form>
				
			</div>
			<!-- //guestbook -->
		</div>
		<!-- //content  -->
		<div class="clear"></div>
		
		<!-- footer -->
		<c:import url="/WEB-INF/views/include/footer.jsp"></c:import>

	</div>
	<!-- //wrap -->

</body>

</html>
