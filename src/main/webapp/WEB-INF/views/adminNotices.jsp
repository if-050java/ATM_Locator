<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

  <div class="panel panel-default">
      <div class="panel-heading">List of notices</div>

      <div class="panel-body">

    <%--
        <form:form method="get" role="form" cssClass="form-horizontal">
          <div class="form-group">
            <div class="btn-group">
              <button type="button" class="btn btn-primary">
                Erase all Notices <i class="glyphicon glyphicon-trash"></i>
              </button>
            </div>
          </div>
        </form:form>
    --%>

        <%-- List of notices --%>
        <div class="panel panel-default">
          <div class="panel-body">
            <table class="table">
                <tr>
                    <th>Time</th>
                    <th>Type</th>
                    <th>Source</th>
                    <th>Message</th>
                </tr>
              <c:forEach items="${notices}" var="note">
                  <tr id="${note.id}">
                      <td>${note.getTimeString()}</td>
                      <td>${note.level}</td>
                      <td class="wordwrap">${note.logger}</td>
                      <td class="wordwrap">${note.message}</td>
                  </tr>
              </c:forEach>
            </table>

          </div>
        </div>
        <%-- List of notices --%>

      </div>
  </div>
