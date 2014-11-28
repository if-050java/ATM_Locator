<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--
  Created by IntelliJ IDEA.
  User: Olavin
  Date: 28.11.2014
  Time: 11:44
  To change this template use File | Settings | File Templates.
--%>
<div class="container">
            <form:form method="get">
                  <c:if test="${status=='deleted'}">

                      <div class="form-group">
                          <div class="col-md-6">
                            <button type="submit" formaction="/adminBanks" class="btn btn-success btn-lg">
                              <span class="sr-only">Close</span>
                              Bank <strong>${bank_name}</strong> deleted.
                            </button>
                          </div>
                      </div>

                  </c:if>

                  <c:if test="${status=='error'}">
                      <div class="alert alert-danger alert-dismissible" role="alert">
                          <button type="submit" formaction="/adminBanks" class="close" data-dismiss="alert"><span aria-hidden="true">&times;</span><span
                                  class="sr-only">Close</span></button>
                          <strong>Error!</strong> Bank is not deleted.
                      </div>
                  </c:if>
            </form:form>
</div>
