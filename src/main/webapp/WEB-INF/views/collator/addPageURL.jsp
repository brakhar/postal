<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="Windows-1251" isELIgnored="false"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
	<spring:url value="/grabStamp/importStamp.do" var="action" />
	<form:form action="${action}" modelAttribute="stampCollatorForm">
		<div>
			<table>
				<tr>
					<td>Page URL</td>
					<td><form:input path="pageURL" /></td>
				</tr>
				<tr>
					<td colspan="1"><input type="submit" value="Collate stamps"></td>
				</tr>
			</table>
		</div>
	</form:form>