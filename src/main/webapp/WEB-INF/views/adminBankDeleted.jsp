<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="container">
            <form:form method="get">
                  <c:if test="${status=='deleted'}">
                      <div class="form-group">
                          <div class="col-md-6">
                            <button type="submit" formaction="<c:url value="/adminBanks" />" class="btn btn-success btn-lg">
                              <span class="sr-only">Close</span>
                              Bank <strong>${bank_name}</strong> deleted.
                            </button>
                          </div>
                      </div>
                  </c:if>

                  <c:if test="${status=='error'}">
                      <div class="form-group">
                          <div class="col-md-6">
                              <button type="submit" formaction="<c:url value="/adminBanks" />" class="btn btn-danger btn-lg">
                                  <span class="sr-only">Close</span>
                                  Error: bank <strong>${bank_name}</strong> is not deleted.
                              </button>
                          </div>
                      </div>
                  </c:if>
            </form:form>
</div>
