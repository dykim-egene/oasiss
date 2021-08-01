<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.w3.org/1999/xhtml">
  <head>
    <meta charset="UTF-8">
    <title>sign up</title>
  </head>
  <body>
    <h1>Sign Up</h1> <hr>

    <form name="signForm" action="/sign" method="POST" class="box">   
    <sec:csrfInput /> 
      email1 : <input type="text" name="email" autocomplete="off"> <br>
      password : <input type="password" name="passwd"> <br>
      <input type="radio" name="auth" value="ROLE_ADMIN,ROLE_USER"> admin
      <input type="radio" name="auth" value="ROLE_USER" checked="checked"> user <br>
      <button type="submit">Join</button>
    </form> <br>

    <a href="/login">Go to login →</a>
  </body>
</html>