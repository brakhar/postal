<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page session="true"%>
<html>
<head>
	<meta name="_csrf" content="${_csrf.token}"/>
	<meta name="_csrf_header" content="${_csrf.headerName}"/>

	<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/jquery.dataTables.css"/>"/>
	<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/postal.css"/>" />
	<link rel="stylesheet" type="text/css" href="<c:url value="/bootstrap/css/bootstrap-datepicker.min.css"/>" />
	<link rel="stylesheet" type="text/css" href="<c:url value="/bootstrap/css/bootstrap.min.css"/>" />
	<link rel="stylesheet" type="text/css" href="<c:url value="/bootstrap/css/fileinput.min.css"/>" />

	<script type="text/javascript" src="<c:url value="/jquery/js/jquery.min.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/bootstrap/js/moment.min.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/bootstrap/js/bootstrap.min.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/bootstrap/js/bootstrap-datepicker.min.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/bootstrap/js/fileinput.min.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/resources/js/jquery.dataTables.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/resources/js/jquery.dataTables.columnFilter.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/resources/js/stampUtils.js"/>"></script>
</head>
<body>
	<h1><a href="${pageContext.request.contextPath}/home.do">Postal</a></h1>
	<div>
		<table>
			<tr>
				<td colspan="1"><a href="${pageContext.request.contextPath}/stamp/list.do">List of stamp</a></td>
			</tr>
		</table>
	</div>

	<c:url value="/j_spring_security_logout" var="logoutUrl" />
	<form action="${logoutUrl}" method="post" id="logoutForm">
		<input type="hidden" name="${_csrf.parameterName}"
			value="${_csrf.token}" />
	</form>
	<script>
		function formSubmit() {
			document.getElementById("logoutForm").submit();
		}
	</script>

	<c:choose>
		<c:when test="${pageContext.request.userPrincipal.name != null}">
			<h2>
				Welcome : ${pageContext.request.userPrincipal.name} | <a
					href="javascript:formSubmit()"> Logout</a>
				<a href="${pageContext.request.contextPath}/stamp/userStampList.do">My stamps</a>
				<a href="${pageContext.request.contextPath}/stamp/toBuyStamp.do">To Buy</a>
				<a href="${pageContext.request.contextPath}/chart/stampChart.do">Statistics</a>
			</h2>
		</c:when>
		<c:when test="${pageContext.request.userPrincipal.name == null}">
			<h2>
				<a href="${pageContext.request.contextPath}/login.do">Login</a>
			</h2>
		</c:when>
	</c:choose>
</body>
</html>