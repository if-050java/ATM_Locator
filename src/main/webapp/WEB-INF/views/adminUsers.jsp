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
    <script src="<c:url value="/resources"/>/scripts/FormValidation.js"></script>
    <link rel="stylesheet" href="<c:url value="/resources"/>/styles/styles.css">
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
                            <button type="button" class="btn btn-default" onclick="FindUser()" id="findBtn" title=""
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
                        <img src="/resources/images/defaultUserAvatar.jpg" class="img-responsive" alt="Responsive image"
                             id="userAvatar">
                    </div>
                    <div class="col-md-8">
                        <form action="" method="post" class="form-horizontal" role="form">
                            <div class="form-group">
                                <label for="inputLogin" class="col-sm-2 control-label">NickName</label>

                                <div class="col-md-10">
                                    <input type="text" class="form-control" id="inputLogin" placeholder="NickName"
                                           title="" data-content="" data-placement="left" data-toggle="popover"
                                           data-original-title="" onclick="hidePopover('inputLogin')"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="inputEmail" class="col-sm-2 control-label">E-mail</label>

                                <div class="col-md-10">
                                    <input type="text" class="form-control" id="inputEmail" placeholder="E-mail"
                                           title="" data-content="E-mail isn't valid" data-placement="left"
                                           data-toggle="popover" data-original-title=""
                                           onclick="hidePopover('inputEmail')"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="inputPassword" class="col-sm-2 control-label">Password</label>

                                <div class="col-md-10">
                                    <input type="password" class="form-control" id="inputPassword"
                                           placeholder="Password" title="" data-content="" data-placement="left"
                                           data-toggle="popover" data-original-title=""
                                           onclick="hidePopover('inputPassword')"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="inputConfirmPassword" class="col-sm-2 control-label">Confirm</label>

                                <div class="col-md-10">
                                    <input type="password" class="form-control" id="inputConfirmPassword"
                                           placeholder="Confirm password" title=""
                                           data-content="Password and confirm is different" data-placement="left"
                                           data-toggle="popover" data-original-title=""
                                           onclick="hidePopover('inputConfirmPassword')"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="enabled" class="col-sm-2 control-label">Enabled</label>

                                <div class="col-md-10">
                                    <input type="checkbox" class="checkbox" id="enabled" checked="true"/>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-2">
                        <button type="button" onclick="sendEmail()" class="btn btn-primary col-md-12">Send e-mail</button>
                    </div>
                    <div class="col-md-2">
                        <button type="button" onclick="askForDeleting()" class="btn btn-danger col-md-12">Delete user
                        </button>
                    </div>
                    <div class="col-md-6">
                        <button type="button" onclick="updateUser()" class="btn btn-success col-md-12">Save</button>
                    </div>
                    <div class="col-md-2">
                        <button type="button" onclick="clearForm()" class="btn btn-warning col-md-12">Cancel</button>
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
                    <strong>You realy want to delete this user</strong>
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
