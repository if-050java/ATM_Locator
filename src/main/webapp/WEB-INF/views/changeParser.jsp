<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--
  Created by IntelliJ IDEA.
  User: maks
  Date: 02.12.2014
  Time: 11:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>change parser page</title>
</head>
<body>

<div class="col-md-12" role="main" id="bankData">
    <div class="container">
        <%--asdf <c:out value="${parsers.size}"/>--%>
        <div class="panel panel-default">
            <div class="panel-heading">
                <h3 class="panel-title">${bank.name}</h3>
            </div>
            <div class="panel-body">
                <c:forEach items="${parsers}" var="parser" varStatus="status">
                    <form class="form-horizontal" method="get">
                        <fieldset>
                        <!-- Form Name -->
                            <%--<c:out value="${status}"/> ${status}--%>
                        <input name="bankId" type="hidden" value="${bank.id}"/>
                        <input name="parserId" type="hidden" value="${parser.id}"/>
                        <legend>Parsers ${parser.name} </legend>

                        <c:forEach items="${parser.paramSet}" var="parameter">
                            <div class="form-group">
                                <label class="col-md-4 control-label" for="paramvalue">${parameter.parameter}</label>

                                <div class="col-md-5">
                                    <input id="paramvalue" name="parametrValue" type="text" placeholder="${parameter.value}" value="${parameter.value}"
                                           class="form-control input-md">
                                </div>
                                <input name="parserParamId" type="hidden" value="${parameter.id}"/>

                            </div>
                        </c:forEach>
                        <!-- Multiple Checkboxes -->
                        <div class="form-group">

                            <label class="col-md-4 control-label" for="checkboxes1">enable/disable</label>

                            <div class="col-md-4">
                                <div class="checkbox">
                                    <label for="checkboxes-0">
                                        <c:choose>
                                            <c:when test="${parser.state=='1'}">p
                                                <input type="checkbox" name="state" id="checkboxes-0" value="2" ><br>
                                            </c:when>

                                            <c:otherwise>
                                                <input type="checkbox" name="state" id="checkboxes-0" value="2" checked> <br />
                                            </c:otherwise>
                                        </c:choose>

                                       <%-- <c:if test="${parser.state=='2'}"/>
                                        <input type="checkbox" name="checkbox" id="checkboxes-0" value="2" >--%>

                                    </label>
                                </div>
                            </div>
                        </div>

                        <!-- Button -->
                        <div class="form-group">
                            <label class="col-md-4 control-label" for="singlebutton">Single Button</label>

                            <div class="col-md-4">
                                <button id="singlebutton" name="singlebutton" formaction="<c:url value="/saveChanges" />"  class="btn btn-warning">change</button>
                            </div>
                        </div>

                        </fieldset>
                    </form>
                </c:forEach>
            </div>
        </div>
    </div>
</div>
</div>
</body>
</html>
