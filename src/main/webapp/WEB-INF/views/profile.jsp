<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:url value="/user/save" var="updateURL"/>
<div class="container-fluid">
    <section class="container">
        <div class="container-page">
            <div class="col-md-3 user_img">
                <img src="http://gravatar.com/avatar/658f2039885a85cc03cc31e20919bed6?s=512"/>
                <a href="" class="">change avatar</a>
            </div>
            <div class="col-md-9">
                <h3 class="dark-grey">My profile</h3>
                <form:form method="post" action="${updateURL}" modelAttribute="user" cssClass="form">
                    <form:hidden path="id"/>
                    <div class="form-group col-lg-9">
                        <label>Nikename:</label>
                        <form:input path="login" placeholder="login" cssClass="form-control"/>
                    </div>
                    <div class="form-group col-lg-9">
                        <label>Email:</label>
                        <form:input path="email" placeholder="email" cssClass="form-control"/>
                    </div>
                    <div class="form-group col-lg-9">
                        <label>Password</label>
                        <form:password path="password" value="${user.password}"  placeholder="password" cssClass="form-control"/>
                    </div>
                    <div class="form-group col-lg-9">
                        <label>Repeat password</label>
                        <input type="password" value="${user.password}" placeholder="repeat password" class="form-control"/>
                    </div>
                    <div class="form-group col-lg-9">
                        <button type="submit" class="btn btn-success">Submit</button>
                    </div>
                </form:form>
            </div>
        </div>
</div>
</section>
</div>