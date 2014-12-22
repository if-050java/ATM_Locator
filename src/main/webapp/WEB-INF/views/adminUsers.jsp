<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <!-- custom scripts and css  -->
    <script src="<c:url value="/resources"/>/scripts/adminUsers.js"></script>
    <script src="<c:url value="/resources"/>/scripts/FormValidation.js"></script>
    <script src="<c:url value="/resources"/>/jquery/jquery.autocomplete.min.js"></script>
    <link href="<c:url value="/resources"/>/styles/adminUsers.css" rel="stylesheet">
    <!-- Checkbox like iPhone -->
    <link href="https://gitcdn.github.io/bootstrap-toggle/2.0.0/css/bootstrap-toggle.min.css" rel="stylesheet">
    <script src="https://gitcdn.github.io/bootstrap-toggle/2.0.0/js/bootstrap-toggle.min.js"></script>
    <title>AdminUsers</title>
</head>
<body>
<div class="container">
    <!-- Find -->
    <div class="col-md-9" role="main">
        <form onsubmit="return FindUser()" action="" method="GET" class="form-horizontal" role="form">
            <div class="form-group">
                <div class="col-md-12">
                    <div class="input-group">
                        <input type="text" name="findName" id="findName" class="form-control"
                               placeholder="Enter login or e-mail" onclick="hidePopover('findName')"
                               onload="onload()" title="" autocomplete="off"
                               data-content="" data-placement="bottom"
                               data-toggle="popover" data-original-title=""/>
                        <span class="input-group-btn">
                            <button type="button" class="btn btn-default" onclick="FindUser()" id="findBtn">
                                Find user
                            </button>
                        </span>
                    </div>
                </div>
            </div>
        </form>
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
                        <img src="" class="img-thumbnail" id="avatar">
                    </div>
                    <div class="col-md-8">
                        <form action="" method="post" class="form-horizontal" role="form">
                            <div class="form-group">
                                <label for="login" class="col-md-3 control-label">Login</label>

                                <div class="col-md-9">
                                    <input type="text" class="form-control" id="login" placeholder="login"
                                           title="" data-content="" data-placement="left" data-toggle="popover"
                                           data-original-title="" onclick="hidePopover('inputLogin')"
                                           onkeyup="setModified()"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="login" class="col-md-3 control-label">NickName</label>

                                <div class="col-md-9">
                                    <input type="text" class="form-control" id="name" placeholder="NickName"
                                           title="" data-content="" data-placement="left" data-toggle="popover"
                                           data-original-title="" onclick="hidePopover('name')"
                                           onkeyup="setModified()"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="email" class="col-md-3 control-label">E-mail</label>

                                <div class="col-md-9">
                                    <input type="text" class="form-control" id="email" placeholder="E-mail"
                                           title="" data-content="E-mail isn't valid" data-placement="left"
                                           data-toggle="popover" data-original-title=""
                                           onclick="hidePopover('email')" onkeyup="setModified()"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="enabled" class="col-md-3 control-label">Log in</label>
                                <div class="col-md-3">
                                    <!-- custom style to set width of switch -->
                                    <style>
                                        .toggle.btn {
                                            min-width: 100px;
                                        }
                                    </style>
                                    <input id="enabled" data-style="width" data-toggle="toggle" unchecked
                                           data-on="enable" data-off="disable" data-onstyle="success"
                                           data-offstyle="danger" type="checkbox">
                                </div>
                                <div class="col-md-6" style="color: #606060">User will not login if disable</div>
                            </div>
                            <div class="form-group">
                                <label for="genPassword" class="col-md-3 control-label">Reset password</label>

                                <div class="col-md-3">
                                    <input id="genPassword" data-style="width" data-toggle="toggle" unchecked
                                           data-on="on" data-off="off" data-onstyle="success"
                                           data-offstyle="danger" type="checkbox">
                                </div>
                                <div class="col-md-6" style="color: #606060">Generate password on server if on</div>
                            </div>
                        </form>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-2 col-lg-offset-8">
                        <button id="delete" type="button" onclick="askForDeleting()" class="btn btn-warning col-md-12">Delete
                        </button>
                    </div>
                    <div class="col-md-2">
                        <button type="button" onclick="updateUser()" class="btn btn-success col-md-12" id="save"
                                disabled="true">Save
                        </button>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!-- modal for request on deleting -->
    <div class="modal fade bs-example-modal-sm" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel"
         aria-hidden="true" id="questionModal">
        <div class="modal-dialog modal-sm">
            <div class="modal-content">
                <div class="modal-header">
                    <strong>Do You realy want to delete this user</strong>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">No</button>
                    <button type="button" class="btn btn-danger" onclick="deleteUser()">Yes</button>
                </div>
            </div>
        </div>
    </div>
    <!--Result of operation -->
    <div class="container" style=" z-index: 1000;position: fixed; bottom:0%; padding-left: 0px;">
        <div class="col-md-9">
            <div class="" role="alert" id="message">
                <div type="button" class="close" onclick="hideAlert()"><span aria-hidden="true">&times;</span><span
                        class="sr-only">Close</span></div>
                <label id="resultDefinition"></label>
            </div>
        </div>
    </div>
</div>


</body>
</html>
