<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

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
                            <li><a href="#" id="isnet0"><span class="glyphicon glyphicon-star"></span>All networks</a></li>
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

<%--
Select Bank dropdown menu
                    <div class="btn-group"> <a class="btn btn-default dropdown-toggle" data-toggle="dropdown" href="#">Select a Bank <span class="caret"></span></a>
                        <ul class="dropdown-menu" id="banks_menu">
                            <c:forEach items="${banks}" var="bank">
                                <li class="bankitem isnet${bank.network.id}"><a href="#" id="${bank.id}">${bank.name}</a></li>
                            </c:forEach>
                        </ul>
                    </div>
Select Bank dropdown menu

Go to Edit Bank page
                    <div class="btn-group">
                        <button type="submit" formaction="<c:url value="./adminBankEdit" />" id="btnBankEdit" class="btn btn-primary" disabled="true">Edit</button>
                    </div>
Go to Edit Bank page
--%>

                    <div class="btn-group">
                        <button type="submit" formaction="<c:url value="/adminBankCreateNew" />" id="btnBankCreateNew" class="btn btn-primary">Add new bank</button>
                    </div>
                                    <%--<input type="hidden" name="network_id" id="network_id">--%>
                    <input type="hidden" name="bank_id" id="bank_id">

                    <%-- update all banks from nbu--%>
                    <div class="btn-group">
                        <button id="singlebutton" formaction="<c:url value="/updateBanksFromNbu" />" name="singlebutton" class="btn btn-primary">Update banks list from NBU</button>
                    </div>

                </form:form>

                <%-- List of Banks generated dynamically in Javascript function showBanks(network_id) --%>
                <div class="panel panel-default">
                    <div class="panel-body">
                        <div class="col-md-4">
                            <ul class="nav nav-pills nav-stacked" id="bankslist1"></ul>
                        </div>
                        <div class="col-md-4">
                            <ul class="nav nav-pills nav-stacked" id="bankslist2"></ul>
                        </div>
                        <div class="col-md-4">
                            <ul class="nav nav-pills nav-stacked" id="bankslist3"></ul>
                        </div>
                    </div>
                </div>
                <%-- List of Banks --%>

            </div>
        </div>

    </div>
</div>

<script src="<c:url value="/resources"/>/scripts/adminBanks.js"></script>
