<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<script src="<c:url value="/resources"/>/jquery/jquery.dataTables.min.js"></script>
<script src="<c:url value="/resources"/>/scripts/adminNotices.js"></script>
<link rel="stylesheet" href="<c:url value="/resources"/>/jquery/jquery.dataTables.css">

<div>
    <div class="panel panel-default">
        <div class="panel-heading">List of notices</div>
        <%-- List of notices --%>
        <div class="panel panel-default">
            <div class="panel-body">
                <table id="noticesgrid" class="display compact">
                    <thead>
                    <tr role="row">
                        <th>Time</th>
                        <th>Type</th>
                        <th>Source</th>
                        <th>Message</th>
                    </tr>
                    </thead>
                    <tbody>
                    </tbody>
                </table>

            </div>
        </div>
        <%-- List of notices --%>

    </div>
</div>
