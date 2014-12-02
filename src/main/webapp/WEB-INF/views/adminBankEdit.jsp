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

<%--
<style type="text/css">
    .btn-file { position: relative; overflow: hidden; margin-right: 4px; }
    .btn-file input { position: absolute; top: 0; right: 0; margin: 0; opacity: 0; filter: alpha(opacity=0); }
</style>
--%>
<link rel="stylesheet" href="<c:url value="/resources"/>/styles/bankedit.css">
<script src="<c:url value="/resources"/>/scripts/adminBankEdit.js"></script>

<div class="col-md-12" role="main" id="bankData">
  <div class="container">
      <div class="panel panel-default">
        <div class="panel-heading">
          <h2 class="panel-title">
            Edit bank:
          </h2>
        </div>

        <form:form method="post"  modelAttribute="bank" role="form" enctype="multipart/form-data" cssClass="form-horizontal">
           <div class="panel-body">
              <%--<div class="row"> --%>
                  <div class="col-md-4">
                        <form:hidden path="logo"/>
                        <img src="<c:url value="/resources/images/${bank.logo}" />" class="img-thumbnail" alt="Bank logo" id="bankLogo">
                        <div class="controls clearfix">
                              <span class="btn btn-default btn-file">
                                  <i class="glyphicon glyphicon-picture"></i> <span>Change logo</span>
                                  <input type="file" name="imageLogo" id="imageLogo"/>
                              </span>
                        </div>
                  </div>

                  <div class="col-md-8">

                    <div class="form-group">
                        <label for="name" class="col-sm-3 control-label">Bank Name</label>
                        <div class="col-md-9">
                            <form:input path="name" placeholder="name" cssClass="form-control"/>
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="mfoCode" class="col-sm-3 control-label">MFO Code</label>
                        <div class="col-md-3">
                            <form:input path="mfoCode" placeholder="mfoCode" cssClass="form-control"/>
                        </div>

                        <label for="id" class="col-sm-1 col-md-offset-3 control-label">ID#</label>
                        <form:hidden path="id"/>
                        <div class="col-md-2">
                            <input type="text" name="id_readonly" value="${bank.id}" class="form-control" disabled="true"/>
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="webSite" class="col-sm-3 control-label">Bank Website</label>
                        <div class="col-md-9">
                            <div class="input-group">
                                <form:input path="webSite" placeholder="webSite" cssClass="form-control"/>
                        <span class="input-group-btn">
                          <button class="btn btn-default" type="button"><i class="glyphicon glyphicon-globe"></i></button>
                        </span>
                            </div>
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="iconAtm" class="col-sm-3 control-label">ATM Icon</label>
                        <div class="col-md-2">
                            <img src="<c:url value="/resources/images/${bank.iconAtm}" />" class="img-thumbnail"  id="bankAtmFile">
                        </div>
                        <div class="col-md-7">
                          <div class="input-group">
                                <form:input path="iconAtm" placeholder="iconAtm" cssClass="form-control"/>
                                <span class="input-group-btn btn-file">
                                  <button class="btn btn-default" type="button"><i class="glyphicon glyphicon-folder-open"></i></button>
                                  <input type="file" name="iconAtmFile" id="iconAtmFile">
                                </span>
                          </div>
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="iconOffice" class="col-sm-3 control-label">Office Icon</label>
                        <div class="col-md-2">
                            <img src="<c:url value="/resources/images/${bank.iconOffice}" />" class="img-thumbnail" id="bankOffice">
                        </div>
                        <div class="col-md-7">
                          <div class="input-group">
                                <form:input path="iconOffice" placeholder="iconOffice" cssClass="form-control"/>
                                <span class="input-group-btn btn-file">
                                      <button class="btn btn-default" type="button"><i class="glyphicon glyphicon-folder-open"></i></button>
                                      <input type="file" name="iconOfficeFile" id="iconOfficeFile"/>
                                </span>
                          </div>
                        </div>
                    </div>

    <%-- Select ATM Network dropdown menu --%>
                    <div class="form-group">
                      <input type="hidden" name="network_id" id="network_id" value="${bank.network.id}" cssClass="form-control">
                      <label for="networks_menu" class="col-sm-3 control-label">ATM network</label>
                      <div class="col-md-4">
                          <div class="btn-group">
                              <a class="btn btn-default dropdown-toggle btn-select" data-toggle="dropdown" href="#">${bank.network.name} <span class="caret"></span></a>
                              <ul class="dropdown-menu" id="networks_menu">
                                  <c:forEach items="${networks}" var="net">
                                      <li><a href="#" id="${net.id}">${net.name}</a></li>
                                  </c:forEach>
                              </ul>
                          </div>
                      </div>

                    </div>
                  </div>
           </div>
           <input type="hidden" name="bank_id" id="bank_id" value="${bank_id}" cssClass="form-control">

            <div class="alert" role="alert">
                <a class="close" onclick="$('.alert').hide()">&times;</a>
            </div>

    <%-- Submit buttons --%>
           <div class="panel-footer">
                    <div class="form-group">
                        <div class="col-md-12">
                            <button type="submit" formaction="<c:url value="/adminBankEdit" />" class="btn btn-success btn-lg col-md-2 col-md-offset-1">
                                Save
                            </button>
                            <button type="submit" formaction="<c:url value="/adminBankDelete" />" class="btn btn-danger btn-lg col-md-3 col-md-offset-1">
                                Delete bank
                            </button>
                            <button type="submit" formaction="<c:url value="/adminBankAtmList" />" class="btn btn-primary btn-lg col-md-4 col-md-offset-1">
                                <span>ATMs and Office list </span><i class="glyphicon glyphicon-list"></i>
                            </button>
                        </div>
                    </div>
           </div>

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
