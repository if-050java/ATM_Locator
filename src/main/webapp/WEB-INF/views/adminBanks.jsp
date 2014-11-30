<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:url value="/adminBanks" var="updateURL"/>

<div class="container">
    <div class="col-md-12" role="main" id="networksList">
        <div class="panel panel-default">
            <!-- Default panel contents -->
            <div class="panel-heading">Banks and ATM networks</div>

            <div class="panel-body">
                <%--<form>--%>
                <form:form method="get" role="form" cssClass="form-horizontal">
                <%-- Select ATM Network dropdown menu --%>
                    <div class="btn-group"> <a class="btn btn-default dropdown-toggle btn-select" data-toggle="dropdown" href="#">Select ATM network<span class="caret"></span></a>
                        <ul class="dropdown-menu" id="networks_menu">
                            <li><a href="#" id="isnet_all"><span class="glyphicon glyphicon-star"></span>All networks</a></li>
                            <li class="divider"></li>
                            <c:forEach items="${networks}" var="net">
                                <c:if test="${net.id > 0}">
                                    <li><a href="#" id="isnet${net.id}">${net.name}</a></li>
                                </c:if>
                            </c:forEach>
                            <li class="divider"></li>
                            <li><a href="#" id="isnet-1"><span class="glyphicon glyphicon-question-sign"></span> Unassigned</a></li>
                        </ul>
                    </div>
                <%-- Select ATM Network dropdown menu --%>

                <%-- Select Bank dropdown menu --%>
                    <div class="btn-group"> <a class="btn btn-default dropdown-toggle" data-toggle="dropdown" href="#">Select a Bank <span class="caret"></span></a>
                        <ul class="dropdown-menu" id="banks_menu">
                            <c:forEach items="${banks}" var="bank">
                                <li class="bankitem isnet${bank.network.id}"><a href="#" id="${bank.id}">${bank.name}</a></li>
                            </c:forEach>
                        </ul>
                    </div>
                <%-- Select Bank dropdown menu --%>

                    <div class="btn-group">
                        <button type="submit" formaction="/adminBankEdit" id="btnBankEdit" class="btn btn-primary" disabled="true">Edit</button>
                    </div>
                    <div class="btn-group">
                        <button type="submit" formaction="/adminBankCreateNew" id="btnBankCreateNew" class="btn btn-primary">Create New</button>
                    </div>
                    <%--<input type="hidden" name="network_id" id="network_id">--%>
                    <input type="hidden" name="bank_id" id="bank_id">
                </form:form>

                <div class="panel panel-default">
                    <div class="panel-body">
                        <div class="col-md-4">
                            <ul class="nav nav-pills nav-stacked" id="bankslist"></ul>
                        </div>
                        <div class="col-md-4">
                            <ul class="nav nav-pills nav-stacked" id="bankslist"><li role="presentation">Column 2</li></ul>
                        </div>
                        <div class="col-md-4">
                            <ul class="nav nav-pills nav-stacked" id="bankslist"><li role="presentation">Column 3</li></ul>
                        </div>
                    </div>
                </div>
            </div>
        </div>

    </div>
</div>

<script src="<c:url value="/resources"/>/scripts/adminBanks.js"></script>
