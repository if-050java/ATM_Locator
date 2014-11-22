<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
<head>
    <script src="<c:url value="/resources"/>/jquery/jquery.min.js"></script>
    <link rel="stylesheet" href="<c:url value="/resources"/>/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="<c:url value="/resources"/>/bootstrap/css/bootstrap-theme.min.css">
    <script src="<c:url value="/resources"/>/bootstrap/js/bootstrap.min.js"></script>
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
</head>
<body>
     <div class="panel-heading">
        <h3 class="panel-title">Register Page</h3>
    </div>
	<div class="container">
        <form class="form-horizontal" role="form" method="POST" action="<c:url value="/registering"/>">
            <div class="form-group">
                <label for="inputLogin" class="col-sm-2 control-label">Login</label>
                <div class="col-sm-10">
                    <input type="text" class="form-control" name="inputLogin" id="inputLogin" placeholder="Login">
                </div>
            </div>
			<div class="form-group">
                <label for="inputEmail" class="col-sm-2 control-label">Email</label>
                <div class="col-sm-10">
                    <input type="text" class="form-control" name="inputEmail" id="inputEmail" placeholder="Email">
                </div>
            </div>
            <div class="form-group">
                <label for="inputPassword" class="col-sm-2 control-label">Password</label>
                <div class="col-sm-10">
                    <input type="text" class="form-control" name="inputPassword" id="inputPassword" placeholder="Password">
                </div>
            </div>
			<div class="form-group">
                <label for="confirmPassword" class="col-sm-2 control-label">Confirm your password</label>
                <div class="col-sm-10">
                    <input type="text" class="form-control" id="confirmPassword" placeholder="Confirm your password">
                </div>
            </div>
            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <div class="checkbox">
                        <label>
                            <input type="checkbox" checked="true" name="signMe" value="signMe"> Sign me in after registration
                        </label>
                    </div>
                </div>
            </div>
            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <button type="submit" class="btn btn-default">Register me</button>
                </div>
            </div>
        </form>
    </div>
	<div class="panel-heading">
        <h3 class="panel-title"><c:out value="${login}"/></h3>
		<br/>
		<h3 class="panel-title"><c:out value="${email}"/></h3>
		<br/>
		<h3 class="panel-title"><c:out value="${password}"/></h3>
		<br/>
		<h3 class="panel-title"><c:out value="${signMe}"/></h3>
        <br/>
        <h3 class="panel-title"><c:out value="${role.name}"/></h3>
        <br/>
        <h3 class="panel-title"><c:out value="${role.description}"/></h3>
    </div>
</body>
</html>