<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
<head>
    <script src="<c:url value="/resources"/>/jquery/jquery.min.js"></script>
    <link rel="stylesheet" href="<c:url value="/resources"/>/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="<c:url value="/resources"/>/bootstrap/css/bootstrap-theme.min.css">
    <script src="<c:url value="/resources"/>/bootstrap/js/bootstrap.min.js"></script>
</head>
<body>
<div class="jumbotron">
    <h1>Hello, IF050!</h1>
    <sec:authorize access="isAnonymous()">
        <p>This is page for unregistered users. You'd be better to login!</p>
        <a class="btn btn-primary btn-lg" href="<c:url value="/login"/>" role="button">Login</a>
    </sec:authorize>
    <sec:authorize access="isAuthenticated()">
        <p>Current user: ${pageContext.request.userPrincipal.name}</p>
        <a class="btn btn-primary btn-lg" href="<c:url value="/j_spring_security_logout" />" role="button">Logout</a>
    </sec:authorize>
    <sec:authorize access="hasRole('ADMIN')">
        <h1>You can go to admin page:</h1>
        <a class="btn btn-primary btn-lg" href="<c:url value="/admin"/>" role="button">go to admin page</a>
    </sec:authorize>
</div>
</body>
</html>
