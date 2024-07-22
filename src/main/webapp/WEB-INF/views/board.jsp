<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<c:choose>
		<c:when test="${empty sessionScope}">
			<a href="<c:url value='/user/login.do'/>">로그인</a>
		</c:when>
		<c:otherwise>
			<div>${sessionScope.user.id}님 환영합니다.</div>
			<a href="<c:url value='user/logout.do'/>">로그아웃</a>
		</c:otherwise>
	</c:choose>
	<br>
	
	<form action="<c:url value="/board/board.do"/>" method="get">
		<select name="searchType">
			<option value="title" ${param.searchType == 'title' ? 'selected' : ''}>제목</option>
			<option value="body" ${param.searchType == 'body' ? 'selected' : ''}>본문</option>		
			<option value="writer" ${param.searchType == 'writer' ? 'selected' : ''}>작성자</option>
		</select>
		<input type="text" name="keyword" value="${param.keyword}">
		<input type="submit" value="검색">
	</form>
	
	<div>${sessionScope.user.id}</div>
	<div>${sessionScope.user.pw}</div>
	<div>${sessionScope.user.userType}</div>
	<table border="1">
		<tr>
			<th>글 번호</th>
			<th>글 제목</th>
			<th>글 작성자</th>
			<th>글 작성일</th>
		</tr>
		<c:forEach items="${vo}" var="bvo">
			<tr>
				<td>${bvo.sno}</td>
				<td><a href='<c:url value="/board/post.do?sno=${bvo.sno}"></c:url>'>${bvo.title}</a></td>
				<td>${bvo.writer}</td>
				<td>${bvo.rdate}</td>
			</tr>
		</c:forEach>
	</table>
	
	
	<f:parseNumber integerOnly="true" var="pageGroup" value="${(currentPage - 1) / 10}"></f:parseNumber>
	<c:set var="startPage" value="${pageGroup * 10 + 1}"></c:set>
	<c:set var="endPage" value="${startPage + (10 - 1)}"></c:set>
	
	
	<c:if test="${currentPage > 1}">
		<a href="<c:url value="/board/board.do?page=1&searchType=${param.searchType}&keyword=${param.keyword}" />">첫 페이지</a>	
		<a href="<c:url value="/board/board.do?page=${currentPage - 1}&searchType=${param.searchType}&keyword=${param.keyword}" />">이전페이지</a>	
	</c:if>
	<c:forEach begin="${startPage}" end="${endPage > totalPage ? totalPage : endPage}" var="pageNum">
		<c:choose>
			<c:when test="${currentPage == pageNum}">
				<span>${pageNum}</span>
			</c:when>
			<c:otherwise>
				<a href="<c:url value="/board/board.do?page=${pageNum}&searchType=${param.searchType}&keyword=${param.keyword}"/>">${pageNum}</a>
			</c:otherwise>
		</c:choose>
	</c:forEach>
	<c:if test="${currentPage < totalPage}">	
		<a href="<c:url value="/board/board.do?page=${currentPage + 1}&searchType=${param.searchType}&keyword=${param.keyword}" />">다음페이지</a>
		<a href="<c:url value="/board/board.do?page=${totalPage}&searchType=${param.searchType}&keyword=${param.keyword}" />">끝 페이지</a>
	</c:if>
	
	
	<a href='<c:url value="/board/write.do"/>'>글작성</a>
</body>
</html>