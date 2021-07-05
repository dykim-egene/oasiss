<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Test Error Page!</title>
</head>
<body>
	<div class="errorPage">

<title>403</title>

<link type="text/css" rel="stylesheet" href="/css/error/style.css" />

<meta name="robots" content="noindex, follow">
</head>
<body>
<div id="notfound">
<div class="notfound">
<div class="notfound-404">
<h1>403</h1>
</div>
<h2>Oops! This Page Could Not Be Found</h2>
<p>Sorry but the page you are looking for does not exist, have been removed. name changed or is temporarily unavailable</p>
<a href="#">Go To Homepage</a>
</div>
</div>

<%-- 	
		<span class="errorHead">Error!</span><br />
		<p>
			request_uri :
			<c:out value="${requestScope['javax.servlet.error.request_uri']}" />
		</p>
		<p>
			status_code :
			<c:out value="${requestScope['javax.servlet.error.status_code']}" />
		</p>
		<p>
			servlet_name :
			<c:out value="${requestScope['javax.servlet.error.servlet_name']}" />
		</p>
		<p>
			exception :
			<c:out value="${requestScope['javax.servlet.error.exception']}" />
		</p>
		<p>
			servlet_name :
			<c:out value="${requestScope['javax.servlet.error.servlet_name']}" />
		</p>
		<p>
			message :
			<c:out value="${requestScope['javax.servlet.error.message']}" />
		</p>
 --%>		
		
	</div>
</body>
</html>

