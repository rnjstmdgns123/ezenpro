<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<form action="modify.do" method="post">
		<input type="hidden" name="sno" value="${vo.sno}">
		제목 : <input type="text" name="title" value="${vo.title}"><br>
		작성자 : <input type="text" name="writer" value="${vo.writer}"><br>
		본문 : <input type="text" name="body" value="${vo.body}"><br>
		<button type="submit">글수정</button>
	</form>
</body>
</html>