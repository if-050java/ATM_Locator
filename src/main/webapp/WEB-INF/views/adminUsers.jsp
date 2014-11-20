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
    <link rel="stylesheet" href="<c:url value="/resources"/>/styles/styles.css">
    <title>AdminUsers</title>
</head>
<body>
<div class="container">
    <div class="col-md-9" role="main">
        <div class="panel panel-default">
            <div class="panel-heading">
                <h3 class="panel-title">
                    Find user
                </h3>
            </div>
            <div class="panel-body">
                <form onsubmit="return FindUser()" action="" method="GET" class="form-horizontal" role="form">
                    <div class="form-group">
                        <div class="col-md-9">
                            <div class="input-group">
                            <span class="input-group-addon">
                                <input type="radio" name="findBy" value="name" checked="true" id="byName"
                                       onchange="SelectFindType()"/>
                            </span>
                                <input type="text" name="findName" id="findName" class="form-control"
                                       placeholder="By name"/>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-md-9">
                            <div class="input-group">
                            <span class="input-group-addon">
                                <input type="radio" name="findBy" value="email" id="byEmail"
                                       onchange="SelectFindType()"/>
                            </span>
                                <input type="text" name="findEmail" disabled="true" id="findEmail" class="form-control"
                                       placeholder="By e-mail"/>
                            </div>
                        </div>
                        <button type="button" class="btn btn-default" onclick="FindUser()">Find user</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
    <div class="col-md-9" role="main" id="userData">
        <div class="panel panel-default">
            <div class="panel-heading">
                <h3 class="panel-title">
                    User profile
                </h3>
            </div>
            <div class="panel-body">
                <div class="row">
                    <div class="col-md-4">
                        <img src="/resources/images/defaultUserAvatar.jpg" class="img-responsive" alt="Responsive image" id="userAvatar">
                    </div>
                    <div class="col-md-8">
                        <form action="" method="post" class="form-horizontal" role="form">
                            <div class="form-group">
                                <label for="inputName" class="col-sm-2 control-label">NickName</label>
                                <div class="col-md-10">
                                    <input type="text" class="form-control" id="inputName" placeholder="NickName">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="inputEmail" class="col-sm-2 control-label">E-mail</label>
                                <div class="col-md-10">
                                    <input type="text" class="form-control" id="inputEmail" placeholder="Email">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="inputPassword" class="col-sm-2 control-label">Password</label>
                                <div class="col-md-10">
                                    <input type="password" class="form-control" id="inputPassword"
                                           placeholder="Password">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="inputConfirmPassword" class="col-sm-2 control-label">Confirm</label>
                                <div class="col-md-10">
                                    <input type="password" class="form-control" id="inputConfirmPassword"
                                           placeholder="Confirm password">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="enabled" class="col-sm-2 control-label">Enabled</label>
                                <div class="col-md-10">
                                    <input type="checkbox" class="checkbox" id="enabled" checked="true">
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-2"><button type="button" class="btn btn-primary col-md-12">Send e-mail</button></div>
                    <div class="col-md-2"><button type="button" onclick="deleteUser()" class="btn btn-danger col-md-12">Delete user</button></div>
                    <div class="col-md-6"><button type="button" onclick="updateUser()" class="btn btn-success col-md-12">Save</button></div>
                    <div class="col-md-2"><button type="button" onclick="cancel()" class="btn btn-warning col-md-12">Cancel</button></div>
                </div>
            </div>
        </div>
    </div>

    <!--Модальне вікно при видаленні -->
    <div class="modal fade" id="resultModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h4 class="modal-title" id="ModalLabel">Deleting user</h4>
                </div>
                <div class="modal-body" id="ModalBody">

                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                </div>
            </div>
        </div>
    </div>
</div>

</body>
</html>
