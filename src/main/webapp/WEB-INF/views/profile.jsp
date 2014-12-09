<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<link rel="stylesheet" href="<c:url value="/resources"/>/styles/user.css">
<c:url value="/user/save" var="updateURL"/>
<script src="<c:url value="/resources"/>/scripts/profile.js"></script>
<script src="<c:url value="/resources"/>/scripts/FormValidation.js"></script>
<div class="container">
    <!-- Small modal window-->
    <div class="alert" role="alert" style="display: none">
        <a class="close" onclick="$('.alert').hide()">&times;</a>
    </div>
    <div class="col-md-16" role="main" id="userData">
        <div class="panel panel-default">
            <div class="panel-heading">
                <h3 class="panel-title">
                    My profile <sec:authorize
                        access="isAuthenticated()">[${pageContext.request.userPrincipal.name}]</sec:authorize>
                </h3>
            </div>
            <div class="panel-body">
                <form:form method="post" action="${updateURL}" modelAttribute="user" cssClass="form-horizontal"
                           role="form" enctype="multipart/form-data" onsubmit="return validateForm()">
                    <div class="row">
                        <div class="col-md-2 avablock">
                            <img src="<c:url value="/resources"/>/images/${user.avatar}" class="img-thumbnail"
                                 alt="avatar"
                                 id="userAvatar">

                            <div class="controls clearfix">
                        <span class="btn btn-success btn-file" id="avatar" data-toggle="popover"
                              data-placement="bottom" onshow="hidePopoveDelay('avatar')">
                            <i class="glyphicon glyphicon-camera"></i> <span>change avatar</span>
                            <input type="file" name="image" id="image"/>
                        </span>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <form:hidden path="id"/>
                            <div class="form-group">
                                <label for="login" class="col-sm-2 control-label">NickName</label>

                                <div class="col-md-10">
                                    <form:input path="login" placeholder="login" cssClass="form-control"
                                                data-toggle="popover"
                                                data-placement="right" onclick="hidePopover('login')"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="email" class="col-sm-2 control-label">E-mail</label>

                                <div class="col-md-10">
                                    <form:input path="email" placeholder="email" cssClass="form-control"
                                                data-toggle="popover"
                                                data-placement="right" onclick="hidePopover('email')"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="password" class="col-sm-2 control-label">Password</label>

                                <div class="col-md-10">
                                    <input type="password" id="password" name="password"
                                           placeholder="&#149;&#149;&#149;&#149;&#149;&#149;"
                                           class="form-control" data-toggle="popover"
                                           data-placement="right" onclick="hidePopover('password')"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="confirmPassword" class="col-sm-2 control-label">Confirm</label>

                                <div class="col-md-10">
                                    <input type="password" id="confirmPassword" name="confirmPassword"
                                           placeholder="&#149;&#149;&#149;&#149;&#149;&#149;" class="form-control"
                                           data-toggle="popover" data-placement="right"
                                           onclick="hidePopover('confirmPassword')"/>
                                </div>
                            </div>
                            <div class="form-group last">
                                <button type="button" class="btn btn-success" id="save">Save</button>
                            </div>
                        </div>
                    </div>
                </form:form>
            </div>
        </div>
    </div>
</div>
