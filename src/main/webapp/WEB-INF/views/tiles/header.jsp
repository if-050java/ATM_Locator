<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<nav class="navbar navbar-default">
    <div class="container-fluid">
        <div class="navbar-header">
            <a class="navbar-brand" href="#">ATM locator</a>
        </div>
        <div>
            <ul class="nav navbar-nav">
                <li class="active"><a href="#">Home</a></li>
                <sec:authorize access="isAnonymous()">
                    <li><a href="<c:url value="/login"/>">Login</a></li>
                    <li><a href="<c:url value="/signup"/>">Sign in</a></li>
                </sec:authorize>
                <sec:authorize access="hasRole('ADMIN')">
                    <li><a href="<c:url value="/admin" />">Banks</a></li>
                    <li><a href="<c:url value="/admin" />">Parsers</a></li>
                    <li><a href="<c:url value="/admin" />">Users</a></li>
                </sec:authorize>
                <sec:authorize access="isAuthenticated()">
                    <li><a href="#">Profile</a></li>
                    <li><a href="<c:url value="/j_spring_security_logout" />">Logout</a></li>
                </sec:authorize>
            </ul>
        </div>
    </div>
</nav>