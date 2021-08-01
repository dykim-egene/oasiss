<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>	
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
    <title>admin</title>
  </head>
  <body>
    <h2>admin 전용 페이지</h2>
    ID : <span sec:authentication="name"></span> <br>
    소유 권한 : <span sec:authentication="authorities"></span> <br>

    <form id="logout" action="/logout" method="POST">
    <sec:csrfInput />
      <input type="submit" value="로그아웃"/>
    </form>

  </body>
</html>