<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	
	<h2>${vo.title}</h2>
	<div>${vo.writer}</div><br>
	<div>${vo.body}</div><br>
	<div>${vo.rdate}</div><br>
	
	
	<c:forEach items="${vo.files}" var="item">
		<div>${item.fileNo}</div>
		<div>${item.sno}</div>
		<div>${item.fileName}</div>
		<div>${item.filePath}</div>
		<div>${item.fileSize}</div>
		<div>${item.fileType}</div>					
	</c:forEach>
	
	<form action="delete.do" method="post">
		<input type="hidden" value="${vo.sno}" name="sno">
		<input type="submit" value="삭제">
	</form>
</body>
</html>