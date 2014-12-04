<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
<head>
    <script src="<c:url value="/resources"/>/jquery/jquery.min.js"></script>
    <link rel="stylesheet" href="<c:url value="/resources"/>/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="<c:url value="/resources"/>/bootstrap/css/bootstrap-theme.min.css">
    <script src="<c:url value="/resources"/>/bootstrap/js/bootstrap.min.js"></script>
    <script src="<c:url value="/resources"/>/scripts/FormValidation.js"></script>
    <script src="<c:url value="/resources"/>/scripts/NewUserValidator.js"></script>
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
</head>
<body>
     <div class="panel-heading">
        <h3 class="panel-title">Register Page</h3>
    </div>
     <c:if test="${not empty error}">
         <div class="alert alert-warning alert-dismissible" role="alert">
             <button type="button" class="close" data-dismiss="alert"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                 ${error}
         </div>
     </c:if>
	<div class="row">
        <form class="form-horizontal" role="form" method="POST" action="<c:url value="/registering"/>">
            <div class="col-md-9">
                <div class="form-group">
                    <label for="inputLogin" class="col-sm-2 control-label">Login</label>
                    <div class="col-md-6">
                        <input type="text" class="form-control" name="inputLogin" id="inputLogin" placeholder="Login"
                               data-content="" data-placement="right"
                               data-toggle="popover"  data-original-title="">
                    </div>
                </div>

			<div class="form-group">
                <label for="inputEmail" class="col-sm-2 control-label">Email</label>

                <div class="col-md-6">
                   <input type="text" class="form-control" name="inputEmail" id="inputEmail" placeholder="Email"
                          data-content="" data-placement="right"
                          data-toggle="popover" data-original-title="">
                </div>
            </div>

            <div class="form-group">
                <label for="inputPassword" class="col-sm-2 control-label">Password</label>
                <div class="col-md-6">
                    <input type="password" class="form-control" name="inputPassword" id="inputPassword" placeholder="Password">
                </div>
            </div>

			<div class="form-group">
                <label for="confirmPassword" class="col-sm-2 control-label">Confirm your password</label>
                <div class="col-md-6">
                    <input type="password" class="form-control" id="confirmPassword" placeholder="Confirm your password">
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
        </div>

        </form>
    </div>
</body>
</html>