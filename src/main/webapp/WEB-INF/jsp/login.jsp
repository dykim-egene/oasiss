<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@page import="java.util.*"%>
<%
	String ERRORMSG = (String)request.getAttribute("ERRORMSG");
%>	
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Index</title>
<link href="/3rd/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css">
<link href="/css/login.css" rel="stylesheet" type="text/css"/>
<link href="https://use.fontawesome.com/releases/v5.8.1/css/all.css" rel="stylesheet" type="text/css"/>

<script type="text/javascript" src="/3rd/jquery/jquery-2.2.4.min.js"></script>
<script	src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script	src="https://stackpath.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.bundle.min.js"></script>	
<script> 
//$.ajax({ type: "GET", url: "/testValue", success: (data) => { console.log(data); $('#contents').html(data); } });

$(document).ready(function(){
	let ERRORMSG = "<%=ERRORMSG%>";
	if(ERRORMSG && ERRORMSG!="null"){
		alert("<%=ERRORMSG%>");	
	}
	$("#sign").on("click", function(){
		location.href="/signup";
	});
	
    var csrfToken = "${_csrf.token}";
    console.log("csrfToken : "+csrfToken);
})

</script>
</head>
<body>
<div class="container">
    <div class="row">
        <div class="col-md-6">
            <div class="card">
                <form name="loginForm" action="/login" method="POST" class="box">
                <sec:csrfInput />
                    <h1>Login</h1>
                    <p class="text-muted"> Please enter your login and password!</p> 
                    <input type="text" id="username" name="username" autocomplete="off" placeholder="사용자ID"> 
                    <input type="password" id="password" name="password" placeholder="Password"> 
                    <p id="sign" class="forgot text-muted" style="cursor:pointer">회원가입</p> 
                    <input type="submit" id="Login" value="Login">
                    <div class="col-md-12">
                        <ul class="social-network social-circle">
                            <li><a href="#" class="icoFacebook" title="Facebook"><i class="fab fa-facebook-f"></i></a></li>
                            <li><a href="#" class="icoTwitter" title="Twitter"><i class="fab fa-twitter"></i></a></li>
                            <li><a href="#" class="icoGoogle" title="Google +"><i class="fab fa-google-plus"></i></a></li>
                        </ul>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
</body>
</html>

