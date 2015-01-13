<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<script src="<c:url value="/resources"/>/jquery/jquery.dataTables.min.js"></script>
<script src="<c:url value="/resources"/>/scripts/adminBankAtmList.js"></script>
<link rel="stylesheet" href="<c:url value="/resources"/>/jquery/jquery.dataTables.css">

<div>
  <div class="panel panel-default">
    <div class="panel-heading">${bank.name}</div>
    <input type="hidden" id="bankid" name="bankid" value="${bank.id}">
    <%-- List of ATMs --%>
    <div class="panel panel-default">
      <div class="panel-body">
        <table id="atmsgrid" class="display compact">
          <thead>
            <tr role="row">
              <th>Select</th>
              <th>id#</th>
              <th>Type</th>
              <th>State</th>
              <th>Address</th>
              <th>Geographic Location</th>
              <th>Last updated</th>
            </tr>
          </thead>
          <tbody>
          </tbody>
        </table>

      </div>
    </div>
    <%-- List of ATMs --%>

  </div>
</div>
