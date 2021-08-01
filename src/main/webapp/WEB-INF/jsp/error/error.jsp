<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Insert title here</title>
</head>
<body>
<h2>${EXCEPTION_HANDLER.errorCode} - ${EXCEPTION_HANDLER.errorCodeString}</h2>
<h3>${EXCEPTION_HANDLER.errorClass}</h3>
<p>${EXCEPTION_HANDLER.errorMessage}</p>
</body>
</html>
