<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><tiles:insertAttribute name="title" /></title>
<style type="text/css">
#header {
	width: 100%;
	clear: both;
	border: solid 2px;
}

#menu {
	width: 0%;
	float: left;
	border: solid 2px;
}

#content {
	width: 100%;
	float: right;
	border: solid 2px;
}

#footer {
	width: 100%;
	clear: both;
	border: solid 2px;
}
</style>
</head>
<body>
	<div id="header">
		<tiles:insertAttribute name="header" />
	</div>
	<div id="menu">
		<tiles:insertAttribute name="menu" />
	</div>
	<div id="content">
		<tiles:insertAttribute name="content" />
	</div>
	
	<div id="footer">
		<tiles:insertAttribute name="footer" />
	</div>
</body>
</html>