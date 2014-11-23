<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!Doctype html>
<html>
<head>
    <script src="<c:url value="/resources"/>/jquery/jquery.min.js"></script>
    <link rel="stylesheet" href="<c:url value="/resources"/>/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="<c:url value="/resources"/>/bootstrap/css/bootstrap-theme.min.css">
    <script src="<c:url value="/resources"/>/bootstrap/js/bootstrap.min.js"></script>
    <link rel="stylesheet" href="<c:url value="/resources"/>/css/user.css">
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
</head>
<body>
<tiles:insertAttribute name="header"/>
<tiles:insertAttribute name="content"/>
<tiles:insertAttribute name="footer"/>
</body>
</html>