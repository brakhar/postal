<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page session="true"%>
<html>
<head>
	<link rel="stylesheet" type="text/css" href="//cdn.datatables.net/1.10.0/css/jquery.dataTables.css"/>
	<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/postal.css"/>" />
	<script type="text/javascript" src="//code.jquery.com/jquery-1.10.2.min.js"></script>
	<script type="text/javascript" src="//code.jquery.com/ui/1.11.4/jquery-ui.js"></script>
	<script type="text/javascript" src="//cdn.datatables.net/1.10.0/js/jquery.dataTables.js"></script>
	<script type="text/javascript" src="<c:url value="/resources/js/stampUtils.js"/>"></script>
	<link rel="stylesheet" href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css"/>
</head>
<body>
	<h1><a href="${pageContext.request.contextPath}/home.do">Postal</a></h1>
	<div>
		<table>
			<tr>
				<td colspan="1"><a href="${pageContext.request.contextPath}/stamp/add.do">Add stamp</a></td>&nbsp;&nbsp;
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