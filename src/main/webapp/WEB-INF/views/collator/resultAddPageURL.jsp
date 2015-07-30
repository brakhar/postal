<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>List Employee</title>
</head>
<body>
	<div id="content">
		<table border="1px">
			<tr bgcolor="gray">
				<td>id</td>
				<td>Name</td>
				<td>Edit<td>
			</tr>
			
			<c:if test="${not empty stampList}">
			 <c:forEach items="${stampList}" var="stamp" >
				<tr>
					<td>${stamp.id}</td>
					<td>${stamp.name}</td>
					<td>${stamp.description}</td>
					<td>${stamp.year}</td>
					<td><img src="${pageContext.request.contextPath}/image/${stamp.stampImageId}.do"/></td>
					<td><a href="${pageContext.request.contextPath}/stamp/edit/${stamp.id}.do">Edit</a><td>
				</tr>
				</c:forEach>
			</c:if>
			<c:if test="${empty stampList}">
				<tr>
					<td colspan="5">No records found!</td>
				</tr>
			</c:if>

			<tr>
				<td align="center" colspan="5"><button
						onclick="location.href='<c:url value='/stamp/add.do' />'">Add</button></td>
			</tr>
		</table>
	</div>
</body>
</html>