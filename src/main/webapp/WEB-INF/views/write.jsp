<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<form action="write.do" method="post" enctype="multipart/form-data">
		제목 : <input type="text" name="title"><br>
		작성자 : <input type="text" name="writer"><br>
		본문 : <input type="text" name="body"><br>
		<input type="file" name="file" multiple="multiple">
		<button type="submit">글작성</button>
	</form>
</body>
</html>