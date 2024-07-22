<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script src="https://code.jquery.com/jquery-latest.min.js"></script>
</head>
<body>
   여기는 회원가입 화면입니다.
<form action="join.do" method="post">
   id: <input type="text" name="id" id="id"> <input type="button" value="아이디 중복체크" id="btn_id_valid"><br>
   pw: <input type="password" name="pw" id="pw"><br>
   <input type="submit" value="회원가입">
</form>
</body>

<script>

	let idCheked = false;
   
   $("#btn_id_valid").on("click", function(){
      let id = $("#id");
      if(id.val().trim() == ""){
         alert("아이디를 입력해주세요")
         return;
      }
      
      $.ajax({
         type : "post",
         url : "<c:url value='/user/idCheck.do'/>",
         data : {
            "id" : id.val()
         },
         success : function(data){
            console.log(data)
            if(data.result == "success"){
            	idCheked = true;
            }else{
            	idCheked = false;
            }
         }
         
      })
   
   })

   $("form").submit(function(){
      let id = $("#id");
      let pw = $("#pw");
      if(idCheked == false){
    	  alert("아이디 중복 체크를 해주세요");
    	  return false;
      }
      if(id.val().trim() == ""){
         alert("아이디를 입력해주세요");
         id.focus();
         return false;
      }
      
      if(pw.val().trim() == ""){
         alert("비밀번호를 입력해주세요");
         pw.focus();
         return false;
      }

      return true;
   })
</script>

</html>