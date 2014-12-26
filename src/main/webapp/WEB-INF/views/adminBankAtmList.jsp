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

        <nav>
            <ul class="pagination">
                <%--For displaying Previous Page --%>
                <c:choose>
                    <c:when test="${page == 1}">
                        <li>
                            <a href="#" aria-label="Previous">
                                <span aria-hidden="true">&laquo;</span>
                            </a>
                        </li>
                    </c:when>
                    <c:otherwise>
                        <li>
                            <a href="<c:url value="/adminBankAtmList"><c:param name="id" value="${bank.id}"/><c:param name="page" value="${page - 1}"/></c:url>" aria-label="Previous" id="${page - 1}">
                                <span aria-hidden="true">&laquo;</span>
                            </a>
                        </li>
                    </c:otherwise>
                </c:choose>
                <%--For displaying list of Pages --%>
                <c:forEach begin="1" end="${page_count}" var="i">
                    <li <c:if test="${page == i}">class="active"</c:if> >
                        <a href="<c:url value="/adminBankAtmList"><c:param name="id" value="${bank.id}"/><c:param name="page" value="${i}"/></c:url>" ><c:out value="${i}"/></a>
                    </li>
                </c:forEach>
                <%--For displaying Next Page --%>
                <c:choose>
                    <c:when test="${page == page_count}">
                        <li class="disabled">
                            <a href="#" aria-label="Next">
                                <span aria-hidden="true">&raquo;</span>
                            </a>
                        </li>
                    </c:when>
                    <c:otherwise>
                        <li>
                            <a href="<c:url value="/adminBankAtmList"><c:param name="id" value="${bank.id}"/><c:param name="page" value="${page + 1}"/></c:url>" aria-label="Next" id="${page + 1}">
                                <span aria-hidden="true">&raquo;</span>
                            </a>
                        </li>
                    </c:otherwise>
                </c:choose>
            </ul>
        </nav>

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
