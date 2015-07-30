<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="//cdn.datatables.net/1.10.0/css/jquery.dataTables.css"/>
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/postal.css"/>" />
    <script type="text/javascript" src="//code.jquery.com/jquery-1.10.2.min.js"></script>
    <script type="text/javascript" src="//code.jquery.com/ui/1.11.4/jquery-ui.js"></script>
    <script type="text/javascript" src="//cdn.datatables.net/1.10.0/js/jquery.dataTables.js"></script>
    <script type="text/javascript" src="<c:url value="/resources/js/stampUtils.js"/>"></script>
    <link rel="stylesheet" href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css"/>

    <script type="text/javascript">

        $(document).ready(function() {

            $("#publishDate").datepicker(
                    {  dateFormat: "dd.mm.yy"}
            );
        });
    </script>
</head>
<body>
<spring:url var="action" value="/stamp/save.do" />
<form:form method="post" action="${action}?${_csrf.parameterName}=${_csrf.token}" modelAttribute="stamp" enctype="multipart/form-data">
    <form:hidden path="id" />
    <form:hidden path="fullListImageId" />
    <form:hidden path="stampImageId" />
    <table border="0" align="left">
        <tr>
            <td>Stamp catalogNumber:*</td>
            <td><form:input path="catalogNumber" /></td>
            <td><form:errors path="catalogNumber" cssClass="error"/></td>
        </tr>
        <tr>
            <td>Circulation</td>
            <td><form:input path="circulation" /></td>
        </tr>
        <tr>
            <td>Title</td>
            <td><form:input path="title" /></td>
        </tr>
        <tr>
            <td>Design</td>
            <td><form:input path="design" /></td>
        </tr>
        <tr>
            <td>format</td>
            <td><form:input path="format" /></td>
        </tr>
        <tr>
            <td>Perforation</td>
            <td><form:input path="perforation" /></td>
        </tr>
        <tr>
            <td>Denomination stamp</td>
            <td><form:input path="denomination" /></td>
        </tr>
        <tr>
            <td>Number stamp in piece paper</td>
            <td><form:input path="numberStampInPiecePaper" /></td>
        </tr>
        <tr>
            <td>Origin publish</td>
            <td><form:input path="originOfPublish" /></td>
        </tr>
        <tr>
            <td>Publish date</td>
            <td><form:input path="publishDate" id="publishDate"/></td>
            <td><form:errors path="publishDate" cssClass="error"/></td>
        </tr>
        <tr>
            <td>Security</td>
            <td><form:input path="security" /></td>
        </tr>
        <tr>
            <td>Special notes</td>
            <td><form:input path="specialNotes" /></td>
        </tr>
        <tr>
            <td>Type publish</td>
            <td><form:input path="typePublish" /></td>
        </tr>
        <tr>
            <td>Bar code</td>
            <td><form:input path="barCode" /></td>
        </tr>
        <tr>
            <td>block</td>
            <td><form:checkbox path="block" /></td>
        </tr>
        <tr>
            <td>Small paper</td>
            <td><form:checkbox path="smallPaper" /></td>
        </tr>
        <tr>
            <td>Block number</td>
            <td><form:input path="blockNumber" /></td>
        </tr>
        <tr>
            <td colspan="2" align="center"><input type="Submit" value="Save" /></td>
        </tr>

        <tr><td colspan="2" class="error"> Note: Fields which has '*' are required fields. </td></tr>
    </table>
    <table align="right">
        <c:choose>
            <c:when test="${not empty actionType && actionType == 'add'}">
                <tr>
                    <td>Stamp image</td>
                    <td><input id="stampImage" name="stampImage" type="file"/></td>
                </tr>
            </c:when>
            <c:when test="${not empty actionType && actionType == 'edit'}">
                <tr>
                    <td>Stamp image</td>
                    <td><img src="${pageContext.request.contextPath}/image/${stamp.stampImageId}.do"></td>
                </tr>
                <tr>
                    <td>Change stamp image</td>
                    <td><input id="stampImage" name="stampImage" type="file"/></td>
                </tr>
            </c:when>
        </c:choose>
        <c:choose>
            <c:when test="${not empty actionType && actionType == 'add'}">
                <tr>
                    <td>Full list of stamp images</td>
                    <td><input name="fullListImage" type="file"/></td>
                </tr>
            </c:when>
            <c:when test="${not empty actionType && actionType == 'edit'}">
                <tr>
                    <td>Full list of stamp images</td>
                    <td><img src="${pageContext.request.contextPath}/image/${stamp.fullListImageId}.do"/></td>
                </tr>
                <tr>
                    <td>Change Full list of stamp image</td>
                    <td><input name="fullListImage" type="file"/></td>
                </tr>
            </c:when>
        </c:choose>
    </table>
</form:form>
</body>
</html>