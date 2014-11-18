<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
</head>
<body>
<div class="jumbotron">
    <h1>Hello, IF050!</h1>
    <sec:authorize access="isAnonymous()">
        <p>This is page for unregistered users. You'd be better to login!</p>
    </sec:authorize>
    <sec:authorize access="isAuthenticated()">
        <p>Current user: ${pageContext.request.userPrincipal.name}</p>
    </sec:authorize>
</div>
</body>
</html>
