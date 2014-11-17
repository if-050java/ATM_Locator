<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%--
  Created by IntelliJ IDEA.
  User: DrBAX_000
  Date: 17.11.2014
  Time: 22:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <script src="<c:url value="/resources"/>/scripts/AdminUsers.js"></script>
    <link rel="stylesheet" href="<c:url value="/resources"/>/styles/styles.css" type="text/css"/>
    <title>AdminUsers</title>
</head>
<body>
<div id="findBox">
    <form onsubmit="return FindUser()" action="" method="GET" class="findTable">
        <table>
            <tr>
                <td><input type="radio" name="findBy" value="name" checked ="true" id="byName" onchange="SelectFindType()"/> by NickName</td>
                <td><input type="text" name="findName" id="findName"/></td>
            </tr>
            <tr>
                <td><input type="radio" name="findBy" value="email" id = "byEmail" onchange="SelectFindType()"/> by E-Mail</td>
                <td><input type="text" name="findEmail" disabled = "true" id = "findEmail"/></td>
                <td><input type="button" onclick="FindUser()" value="find"/></td>
            </tr>
        </table>
    </form>
</div>
</body>
</html>
