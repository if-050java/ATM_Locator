<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<link rel="stylesheet" href="<c:url value="/resources"/>/styles/alert.css">
<script src="<c:url value="/resources"/>/scripts/showAlert.js"></script>

<div class="container">
    <div class="col-md-12" role="main" id="networksList">
        <div class="panel panel-default">
            <!-- Default panel contents -->
            <div class="panel-heading">Banks and ATM networks</div>

            <div class="panel-body">
                <%--<form>--%>
                <form:form method="get" role="form" cssClass="form-horizontal">

                    <div class="form-group" style="margin-left: 0">
                        <%-- Select ATM Network dropdown menu --%>
                        <div class="btn-group"> <a class="btn btn-default dropdown-toggle btn-select" style="width: 200px" data-toggle="dropdown" href="#">Filter by ATM network<span class="caret"></span></a>
                            <ul class="dropdown-menu" id="networks_menu">
                                <%-- List of Networks generated dynamically in Javascript function loadNetworks() --%>
                            </ul>
                        </div>
                        <%-- Select ATM Network dropdown menu --%>

                        <div class="btn-group">
                            <button type="submit" formaction="<c:url value="/adminBankCreateNew" />" id="btnBankCreateNew" class="btn btn-primary">
                                Add new bank <i class="glyphicon glyphicon-plus"></i>
                            </button>
                        </div>
                                        <%--<input type="hidden" name="network_id" id="network_id">--%>
                        <input type="hidden" name="bank_id" id="bank_id">

                        <div class="btn-group">
                            <button type="button" id="btnAddNetwork" class="btn btn-primary" data-toggle="collapse" data-target="#network_edit">
                                Manage ATM Networks <i class="glyphicon glyphicon-edit"></i>
                            </button>
                        </div>

                        <%-- update all banks from nbu--%>
                        <div class="btn-group">
                            <button id="singlebutton" formaction="<c:url value="/updateBanksFromNbu" />" name="singlebutton" class="btn btn-default">
                                Update banks list from NBU <i class="glyphicon glyphicon-import"></i>
                            </button>
                        </div>

                        <div class="btn-group">
                            <button formaction="<c:url value="/testU" />" name="testu" class="btn btn-default">
                                Update ATM list from UBanks.com.ua <i class="glyphicon glyphicon-import"></i>
                            </button>
                        </div>

                    </div>

<%--
                    <div class="alert" role="alert" style="display: none">
                        <a class="close" onclick="$('.alert').hide()">&times;</a>
                    </div>
--%>
                    <div id="network_edit" class="form-group collapse">
                        <label for="net_name" class="control-label col-sm-2">Network name:</label>
                        <div class="col-md-3">
                            <input type="text" name="net_name" id="net_name" value="" class="form-control"/>
                        </div>
                        <div class="col-md-2">
                            <button class="btn btn-default btn-block" id="btn_save_network" type="button">Update name</button>
                        </div>
                        <div class="col-md-2">
                            <button class="btn btn-default btn-block" id="btn_new_network" type="button">Save as new</button>
                        </div>
                        <div class="col-md-2">
                            <button class="btn btn-default btn-block" id="btn_del_network" type="button">Delete</button>
                        </div>
                    </div>
                </form:form>

                <%-- List of Banks --%>
                <div class="panel panel-default">
                    <div class="panel-body">
                        <div class="col-md-4">
                            <ul class="nav nav-pills nav-stacked" id="bankslist1">
                                <%-- generated dynamically in Javascript function showBanks(network_id) --%>
                            </ul>
                        </div>
                        <div class="col-md-4">
                            <ul class="nav nav-pills nav-stacked" id="bankslist2">
                                <%-- generated dynamically in Javascript function showBanks(network_id) --%>
                            </ul>
                        </div>
                        <div class="col-md-4">
                            <ul class="nav nav-pills nav-stacked" id="bankslist3">
                                <%-- generated dynamically in Javascript function showBanks(network_id) --%>
                            </ul>
                        </div>
                    </div>
                </div>
                <%-- List of Banks --%>

            </div>
        </div>

    </div>
</div>

<script src="<c:url value="/resources"/>/scripts/adminBanks.js"></script>
