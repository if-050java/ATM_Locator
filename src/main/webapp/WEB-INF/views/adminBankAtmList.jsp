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
<div class="container">
  <form:form method="get">
      <div class="form-group">
        <div class="col-md-6">
          <button type="submit" formaction="<c:url value="/adminBanks" />" class="btn btn-success btn-lg">
            <span class="sr-only">Close</span>
            <strong>Bank ATM and office list</strong> page is under construction
          </button>
        </div>
      </div>
  </form:form>
</div>
