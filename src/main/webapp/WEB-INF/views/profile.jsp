<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<link rel="stylesheet" href="<c:url value="/resources"/>/styles/user.css">
<script src="<c:url value="/resources"/>/scripts/profile.js"></script>
<script src="<c:url value="/resources"/>/scripts/FormValidation.js"></script>
<div class="container">
    <div class="col-md-9" role="main" id="userData" style="display: block">
        <div class="panel panel-default">
            <div class="panel-heading">
                <h3 class="panel-title">
                    <i class="glyphicon glyphicon-user"></i>
                    My profile: <sec:authorize
                        access="isAuthenticated()">[<span>${pageContext.request.userPrincipal.name}</span>]</sec:authorize>
                </h3>
            </div>
            <div class="panel-body">
                <form:form modelAttribute="user" cssClass="form-horizontal"
                           role="form" onsubmit="return validateForm()">
                    <div class="row">
                        <div class="col-md-4 avablock">
                            <img src="<c:url value="/resources"/>/images/${user.avatar}" class="img-thumbnail"
                                 alt="avatar"
                                 id="userAvatar">

                            <div class="controls clearfix">
                        <span class="btn btn-default btn-file" id="avatar" data-toggle="popover"
                              data-placement="bottom">
                            <i class="glyphicon glyphicon-camera"></i> <span>change avatar</span>
                            <input type="file" name="image" id="image"/>
                        </span>
                            </div>
                        </div>
                        <div class="col-md-8">
                            <form:hidden path="id"/>
                            <div class="form-group">
                                <label for="login" class="col-md-3 control-label">Login</label>

                                <div class="col-md-9">
                                    <form:input path="login" placeholder="login" cssClass="form-control"
                                                data-toggle="popover" readonly="true"
                                                data-placement="right" onclick="hidePopover('login')"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="name" class="col-md-3 control-label">NickName</label>

                                <div class="col-md-9">
                                    <form:input path="name" placeholder="name" cssClass="form-control"
                                                data-toggle="popover"
                                                data-placement="right" onclick="hidePopover('name')"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="email" class="col-md-3 control-label">E-mail</label>

                                <div class="col-md-9">
                                    <form:input path="email" placeholder="email" cssClass="form-control"
                                                data-toggle="popover"
                                                data-placement="right" onclick="hidePopover('email')"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="password" class="col-md-3 control-label">Password</label>

                                <div class="col-md-9">
                                    <input type="password" id="password" name="password"
                                           placeholder="&#149;&#149;&#149;&#149;&#149;&#149;"
                                           class="form-control" data-toggle="popover"
                                           data-placement="right" onclick="hidePopover('password')"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="confirmPassword" class="col-md-3 control-label">Confirm</label>

                                <div class="col-md-9">
                                    <input type="password" id="confirmPassword" name="confirmPassword"
                                           placeholder="&#149;&#149;&#149;&#149;&#149;&#149;" class="form-control"
                                           data-toggle="popover" data-placement="right"
                                           onclick="hidePopover('confirmPassword')"/>
                                </div>
                            </div>
                            <div class="form-group last">
                                <div class="col-md-3"></div>
                                <div class="col-md-9">
                                    <button type="button" class="btn btn-success" disabled id="save"
                                            style="width: 200px;"><i class="glyphicon glyphicon-floppy-disk"></i>&nbsp;Save</button>
                                </div>
                            </div>
                        </div>
                    </div>
                </form:form>
            </div>
        </div>
    </div>
</div>
<!-- Small modal window-->
<div class="container">
    <div class="col-md-9">
        <div class="alert" role="alert" style="display: none; z-index: 1000;">
            <a class="close" onclick="$('.alert').fadeOut('slow');">&times;</a>
        </div>
    </div>
</div>


