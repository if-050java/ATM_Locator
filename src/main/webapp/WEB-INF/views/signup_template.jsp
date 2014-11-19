<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
</head>
<body>
<div class="jumbotron">
    <h1>Please enter data about you</h1>
    <sec:authorize access="isAnonymous()">
        <form name="signin" action="signup_do" method="post">
            <input  type = "text" name = "login" value="Enter your login"/>
            <br/>
            <br/>
            <input  type = "text" name = "email" value="Enter your email"/>
            <br/>
            <br/>
            <input  type = "text" name = "password" value="Enter your password"/>
            <br/>
            <br/>
            <input type="submit" value="Register"/>
        </form>


    </sec:authorize>

</div>
</body>
</html>