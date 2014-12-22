<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<body>
<div class="container">
  <div class="col-md-12" role="main">
    <div class="panel panel-default">
      <div class="panel-heading">List of notices</div>

      <div class="panel-body">
        <%--<form>--%>
        <form:form method="get" role="form" cssClass="form-horizontal">

          <div class="form-group">

            <div class="btn-group">
              <button type="button" class="btn btn-primary">
                Erase all Notices <i class="glyphicon glyphicon-trash"></i>
              </button>
            </div>

          </div>

        </form:form>

        <%-- List of notices --%>
        <div class="panel panel-default">
          <div class="panel-body">

            <ul class="nav nav-pills nav-stacked" id="noticeslist">
              <c:forEach items="${notices}" var="note">
                  <li id="${note.id}"><em>${note.getTimeString()}</em> ${note.message}</li>
              </c:forEach>
            </ul>

          </div>
        </div>
        <%-- List of notices --%>

      </div>
    </div>

  </div>


</div>

</body>
</html>
