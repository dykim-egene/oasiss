<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>	
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
    <title>main</title>
  </head>
  <body>
    <h2>회원 전용 페이지</h2>
    ID : <span sec:authentication="name"></span> <br>
    소유 권한 : <span sec:authentication="authorities"></span> <br>

    <form id="logout" action="/logout" method="POST">
    <sec:csrfInput />
      <input type="submit" value="로그아웃"/>
    </form>
<button onclick="ajTest();">AJAX Error Test</button>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.4/jquery.min.js"></script> 
<script>
    var contextPath = "${pageContext.request.contextPath}";
    
    var csrfHeaderName = "${_csrf.headerName}";
    var csrfToken = "${_csrf.token}";
    console.log("contextPath : "+contextPath);
    function ajTest(){
        $.get({
            url: contextPath + "/ajaxTest",
            cache: false,
            success: function(data, status, xhr){
                alert(data);
                console.log(status, xhr);
            },
            error: function(xhr, status, error){
                if(xhr.status == 401){
                    if(confirm("로그인이 필요합니다. 지금 바로 로그인하시겠습니까?")){
                        window.open(contextPath + "/login", "_self");
                    }
                }
                else{
                    alert(xhr.status + ": " + error);
                }
                console.log(xhr, status, error);
            }
        });
    }
</script>


  </body>
</html>