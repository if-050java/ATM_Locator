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
    <!-- custom scripts and css  -->
    <script src="<c:url value="/resources"/>/scripts/AdminUsers.js"></script>
    <script src="<c:url value="/resources"/>/scripts/FormValidation.js"></script>
    <!-- Checkbox like iPhone -->
    <link href="https://gitcdn.github.io/bootstrap-toggle/2.0.0/css/bootstrap-toggle.min.css" rel="stylesheet">
    <script src="https://gitcdn.github.io/bootstrap-toggle/2.0.0/js/bootstrap-toggle.min.js"></script>
    <title>AdminUsers</title>
</head>
<body>
<div class="container">
    <!-- Find -->
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
                                           placeholder="By name" onclick="hidePopover('findBtn')"/>
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
                                        <input type="text" name="findEmail" disabled="true" id="findEmail"
                                                class="form-control"
                                                placeholder="By e-mail" onclick="hidePopover('findBtn')"/>
                                </div>
                            </div>
                            <button type="button" class="btn btn-default col-md-2" onclick="FindUser()" id="findBtn" title=""
                                    data-content="" data-placement="left"
                                    data-toggle="popover" data-original-title="">Find user
                            </button>
                        </div>

                </form>
            </div>
        </div>
    </div>
    <!--Result of operation -->
    <div class="col-md-9">
        <div class="alert alert-success alert-dismissible" role="alert" id="message" style="display: none">
            <div type="button" class="close" onclick="hideAlert()"><span aria-hidden="true">&times;</span><span
                    class="sr-only">Close</span></div>
            <label id="resultDefinition"></label>
        </div>
    </div>
    <!-- User profile-->
    <div class="col-md-9" role="main" id="userData" style="display: none">
        <div class="panel panel-default">
            <div class="panel-heading">
                <div type="button" class="close" onclick="clearForm()"><span aria-hidden="true">&times;</span><span
                        class="sr-only">Close</span></div>
                <h3 class="panel-title">
                    User profile
                </h3>
            </div>
            <div class="panel-body">
                <div class="row">
                    <div class="col-md-4">
                        <img src="" class="img-thumbnail"  id="userAvatar">
                    </div>
                    <div class="col-md-8">
                        <form action="" method="post" class="form-horizontal" role="form">
                            <div class="form-group">
                                <label for="inputLogin" class="col-md-2 control-label">NickName</label>

                                <div class="col-md-10">
                                    <input type="text" class="form-control" id="inputLogin" placeholder="NickName"
                                           title="" data-content="" data-placement="left" data-toggle="popover"
                                           data-original-title="" onclick="hidePopover('inputLogin')"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="inputEmail" class="col-md-2 control-label">E-mail</label>

                                <div class="col-md-10">
                                    <input type="text" class="form-control" id="inputEmail" placeholder="E-mail"
                                           title="" data-content="E-mail isn't valid" data-placement="left"
                                           data-toggle="popover" data-original-title=""
                                           onclick="hidePopover('inputEmail')"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="inputPassword" class="col-md-2 control-label">Password</label>

                                <div class="col-md-10">
                                    <input type="password" class="form-control" id="inputPassword"
                                           placeholder="Password" title="" data-content="" data-placement="left"
                                           data-toggle="popover" data-original-title=""
                                           onclick="hidePopover('inputPassword')"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="inputConfirmPassword" class="col-md-2 control-label">Confirm</label>

                                <div class="col-md-10">
                                    <input type="password" class="form-control" id="inputConfirmPassword"
                                           placeholder="Confirm password" title=""
                                           data-content="Password and confirm is different" data-placement="left"
                                           data-toggle="popover" data-original-title=""
                                           onclick="hidePopover('inputConfirmPassword')"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="enabled" class="col-md-2 control-label">Log in</label>
                                <div class="col-md-3">
                                    <style>
                                        .toggle.btn {
                                            min-width: 100px;
                                        }
                                    </style>
                                    <input id="enabled" data-style="width" data-toggle="toggle" checked data-on="allowed" data-off="prohibited" data-onstyle="success" data-offstyle="danger" type="checkbox">
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-2 col-lg-offset-8">
                        <button type="button" onclick="askForDeleting()" class="btn btn-warning col-md-12">Delete
                        </button>
                    </div>
                    <div class="col-md-2">
                        <button type="button" onclick="updateUser()" class="btn btn-primary col-md-12">Save</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!-- modal for request on deleting -->
    <div class="modal fade bs-example-modal-sm" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel" aria-hidden="true" id="questionModal">
        <div class="modal-dialog modal-sm">
            <div class="modal-content">
                <div class="modal-header">
                    <strong>Do You realy want to delete this user</strong>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">No</button>
                    <button type="button" class="btn btn-primary" onclick="deleteUser()">Yes</button>
                </div>
            </div>
        </div>
    </div>
</div>

</body>
</html>
