<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%--
http://www.codeproject.com/Articles/359750/jQuery-DataTables-in-Java-Web-Applications
--%>

<script src="<c:url value="/resources"/>/jquery/jquery.dataTables.min.js"></script>
<link rel="stylesheet" href="<c:url value="/resources"/>/jquery/jquery.dataTables.min.css">

<script type="text/javascript">
  $(document).ready(function () {
    $("#atmsgrid").dataTable({
      "serverSide": true,
      "ajax": {
        "url": "/getBankATMs",
        "data": function ( d ) {
          d.bankId = "${bank.id}";
        }
      },
      "processing": true
    });
  });
</script>

<div>
  <div class="panel panel-default">
    <div class="panel-heading">${bank.name}</div>

    <%-- List of ATMs --%>
    <div class="panel panel-default">
      <div class="panel-body">
        <table id="atmsgrid" class="display table">
          <thead>
            <th>id#</th>
            <th>Type</th>
            <th>State</th>
            <th>Address</th>
            <th>Geographic Location</th>
            <th>Last updated</th>
          </thead>
          <tbody>
          </tbody>
        </table>

      </div>
    </div>
    <%-- List of ATMs --%>

  </div>
</div>
