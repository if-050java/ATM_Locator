<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<script>
    function getHomeUrl(){
        return '<c:url value="/"/>';
    }
    <c:if test="${not empty active}">
    $(document).ready(function () {
        $('.${active}').addClass('active');
    });
    </c:if>
</script>
<nav class="navbar navbar-default">
    <div class="container">
        <div class="col-md-9">
            <div class="navbar-header">
                <a class="navbar-brand" href="#">ATM locator</a>
            </div>
            <div>
                <ul class="nav navbar-nav">

                    <li class="main"><a href="<c:url value="/"/>">Home</a></li>
                    <sec:authorize access="isAnonymous()">
                    <li class="login"><a href="<c:url value="/login"/>">Login</a></li>
                    <li class="signup"><a href="<c:url value="/signup"/>">Sign up</a></li>
                    </sec:authorize>
                    <sec:authorize access="isAuthenticated()">
                        <li class="profile"><a href="<c:url value="/profile" />">Profile</a></li>
                    </sec:authorize>
                    <sec:authorize access="hasRole('ADMIN')">
                        <li class="adminBanks"><a href="<c:url value="/adminBanks" />">Banks</a></li>
                        <li class="admin"><a href="<c:url value="/admin" />">Parsers</a></li>
                        <li class="adminUsers"><a href="<c:url value="/admin/users" />">Users</a></li>
                        <li class="adminNotices"><a href="<c:url value="/adminNotices" />">Notices</a></li>
                    </sec:authorize>
                </ul>
            </div>
            <sec:authorize access="isAuthenticated()">
                <p style="margin-right: 0px" class="navbar-text navbar-right">Signed in as <a href="<c:url value="/profile" />" id="userLogin">${userName}</a>(<a href="<c:url value="/j_spring_security_logout" />">logout</a>)</p>
            </sec:authorize>

        </div>
    </div>
</nav>
