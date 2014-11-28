<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--
  Created by IntelliJ IDEA.
  User: d18-antoshkiv
  Date: 25.11.2014
  Time: 10:55
--%>

<div class="col-md-12" role="main" id="userData">
  <div class="panel panel-default">
    <div class="panel-heading">
      <h2 class="panel-title">
        Edit bank:
      </h2>
    </div>
    <div class="panel-body">

        <form:form method="post"  modelAttribute="bank" role="form" enctype="multipart/form-data" cssClass="form-horizontal">
          <%--<div class="row"> --%>
              <div class="col-md-2">
                <form:hidden path="logo"/>
                <img src="/resources/images/${bank.logo}" style="height: 256px; width: 256px; display: block;" class="img-thumbnail" alt="Bank logo" id="bankLogo">
                <div class="controls clearfix">
                      <span class="btn btn-success btn-file">
                          <i class="glyphicon glyphicon-picture"></i> <span>Change logo</span>
                          <input type="file" name="imageLogo" id="imageLogo"/>
                      </span>
                </div>
              </div>

              <div class="col-md-8">

                <div class="form-group">
                    <label for="name" class="col-sm-2 control-label">Bank Name</label>
                    <div class="col-md-6">
                        <form:input path="name" placeholder="name" cssClass="form-control"/>
                    </div>
                </div>

                <div class="form-group">
                    <label for="mfoCode" class="col-sm-2 control-label">MFO Code</label>
                    <div class="col-md-2">
                        <form:input path="mfoCode" placeholder="mfoCode" cssClass="form-control"/>
                    </div>

                    <label for="id" class="col-sm-3 control-label">ID#</label>
                    <form:hidden path="id"/>
                    <div class="col-md-1">
                        <input type="text" name="id_readonly" value="${bank.id}" class="form-control" disabled="true"/>
                    </div>
                </div>

                <div class="form-group">
                    <label for="webSite" class="col-sm-2 control-label">Bank Website</label>
                    <div class="col-md-6">
                        <div class="input-group">
                            <form:input path="webSite" placeholder="webSite" cssClass="form-control"/>
                    <span class="input-group-btn">
                      <button class="btn btn-default" type="button"><i class="glyphicon glyphicon-globe"></i></button>
                    </span>
                        </div>
                    </div>
                </div>

                <div class="form-group">
                    <label for="iconAtm" class="col-sm-2 control-label">ATM Icon</label>
                    <div class="col-md-1">
                        <img src="/resources/images/${bank.iconAtm}" class="img-thumbnail">
                    </div>
                    <div class="col-md-5">
                      <div class="input-group">
                            <form:input path="iconAtm" placeholder="iconAtm" cssClass="form-control"/>
                            <span class="input-group-btn btn-file">
                              <button class="btn btn-default" type="button"><i class="glyphicon glyphicon-folder-open"></i></button>
                            </span>
                            <input type="file" name="iconAtmFile" id="iconAtmFile">
                      </div>
                    </div>
                </div>

                <div class="form-group">
                    <label for="iconOffice" class="col-sm-2 control-label">Office Icon</label>
                    <div class="col-md-1">
                        <img src="/resources/images/${bank.iconOffice}" class="img-thumbnail">
                    </div>
                    <div class="col-md-5">
                      <div class="input-group">
                            <form:input path="iconOffice" placeholder="iconOffice" cssClass="form-control"/>
                            <span class="input-group-btn btn-file">
                              <button class="btn btn-default" type="button"><i class="glyphicon glyphicon-folder-open"></i></button>
                            </span>
                            <input type="file" name="iconOfficeFile" id="iconOfficeFile"/>
                      </div>
                    </div>
                </div>

<%-- Select ATM Network dropdown menu --%>
                <div class="form-group">
                  <input type="hidden" name="network_id" id="network_id" value="${bank.network.id}" cssClass="form-control">
                  <label for="networks_menu" class="col-sm-2 control-label">ATM network</label>
                  <div class="col-md-4">
                      <div class="btn-group">
                          <a class="btn btn-default dropdown-toggle btn-select" data-toggle="dropdown" href="#">${bank.network.name} <span class="caret"></span></a>
                          <ul class="dropdown-menu" id="networks_menu">
                              <c:forEach items="${networks}" var="net">
                                  <li><a href="#" id="${net.id}">${net.name}</a></li>
                              </c:forEach>
                                  <%--
                                  <li class="divider"></li>
                                  <li><a href="#" id="-1"><span class="glyphicon glyphicon-question-sign"></span>Unknown</a></li>
                                  --%>
                          </ul>
                      </div>
                  </div>

                </div>

                <div class="form-group">
                    <div class="col-md-6 col-md-offset-1">
                        <button type="submit" formaction="/adminBankEdit" class="btn btn-success btn-lg">
                            Save
                        </button>
                        <button type="submit" formaction="/adminBankDelete" class="btn btn-danger btn-lg">
                            Delete bank
                        </button>
                        <button type="button" formaction="/adminBankAtmList" class="btn btn-primary btn-lg">
                            <span>ATMs and Office list </span><i class="glyphicon glyphicon-list"></i>
                        </button>
                    </div>
                </div>
              </div>

          <%--</div>--%>
        </form:form>
    </div>
  </div>
</div>

<script type='text/javascript'>
/* On select item in ATM Network dropdown
*  set dropdown title to name of the ATM Network
* */
var network_id = -1;
$("#networks_menu li a").click(function(){
        var selText = $(this).text();
        $(this).parents('.btn-group').find('.dropdown-toggle').html(selText+' <span class="caret"></span>');
        network_id = $(this).attr("id");
        document.getElementById("network_id").value = network_id;
});

</script>
