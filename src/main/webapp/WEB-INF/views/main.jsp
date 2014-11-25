<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
    <script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyA4YR8loJtUaiviLc-WxnBsSH9Znt9TNEY"></script>
    <script src="<c:url value="/resources"/>/scripts/map.js"></script>
</head>
<body>
<div class="container">
    <div class="row">
        <div class="col-md-9">
            <div class="input-group">
                <span class="input-group-btn">
                    <button class="btn btn-default" type="button"><span class="glyphicon glyphicon-globe"></span></button>
                </span>
                <input type="text" class="form-control">
                <span class="input-group-btn">
                    <button class="btn btn-default" type="button"><span class="glyphicon glyphicon-search" title="Find"></span></button>
                </span>
            </div>
            <div id="map_container" style="height: 500px" class="media">
                <!-- Map is here  -->
            </div>
        </div>
        <div class="col-md-3">
            <div class="panel panel-default">
                <div class="page-header">
                    <div class="panel-title">Filter</div>
                </div>
                <div class="panel-body">

                </div>
            </div>
        </div>
    </div>
</div>

<%--<div class="jumbotron">
    <h1>Hello, IF050!</h1>
    <sec:authorize access="isAnonymous()">
        <p>This is page for unregistered users. You'd be better to login!</p>
    </sec:authorize>
    <sec:authorize access="isAuthenticated()">
        <p>Current user: ${pageContext.request.userPrincipal.name}</p>
    </sec:authorize>
</div>--%>
</body>
</html>
