<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--
  Created by IntelliJ IDEA.
  User: Olavin
  Date: 30.11.2014
  Time: 22:30
  To change this template use File | Settings | File Templates.
--%>
<div>
    <div class="panel panel-default">
        <div class="panel-heading">${bank.name}</div>

        <%-- List of ATMs --%>
        <div class="panel panel-default">
          <div class="panel-body">
            <table class="table">
              <tr>
                <th>id#</th>
                <th>Type</th>
                <th>Address</th>
                <th>Geographic Location</th>
                <th>Last updated</th>
              </tr>
              <c:forEach items="${atms}" var="atm">
                <tr id="${atm.id}">
                  <td>${atm.id}</td>
                  <td>${atm.getTypeString()}</td>
                  <td>${atm.address}</td>
                  <td>${atm.geoPosition.toString()}</td>
                  <td>${atm.getTimeString()}</td>
                </tr>
              </c:forEach>
            </table>

          </div>
        </div>
        <%-- List of ATMs --%>


    </div>
</div>
