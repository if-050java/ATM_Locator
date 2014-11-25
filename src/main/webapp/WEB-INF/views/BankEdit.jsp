<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%--
  Created by IntelliJ IDEA.
  User: d18-antoshkiv
  Date: 25.11.2014
  Time: 10:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="col-md-12" role="main" id="userData">
  <div class="panel panel-default">
    <div class="panel-heading">
      <h2 class="panel-title">
        Edit bank:
      </h2>
    </div>
    <div class="panel-body">

        <form:form method="post" action="/BankEdit" modelAttribute="bank" role="form" enctype="multipart/form-data" cssClass="form-horizontal">
          <div class="row">
              <div class="col-md-2">
                <form:input path="logo"/>
                <div class="thumbnail">
                  <img src="/resources/images/${bank.logo}" style="height: 256px; width: 256px; display: block;" class="img-responsive" alt="Bank logo"                       id="bankLogo">

                </div>
                <div class="controls clearfix">
                      <span class="btn btn-success btn-file">
                          <i class="glyphicon glyphicon-picture"></i> <span>Load file</span>
                          <input type="file" name="imageLogo" id="imageLogo"/>
                      </span>
                </div>
              </div>

            <div class="col-md-8">
                  <form:input path="id" cssClass="form-control"/>
                  <form:input path="name" placeholder="name" cssClass="form-control"/>
                  <form:input path="mfoCode" placeholder="mfoCode" cssClass="form-control"/>
                  <form:input path="webSite" placeholder="webSite" cssClass="form-control"/>
                  <%-- <form:input path="logo" placeholder="logo" cssClass="form-control"/> --%>
                  <form:input path="iconAtm" placeholder="iconAtm" cssClass="form-control"/>
                  <form:input path="iconOffice" placeholder="iconOffice" cssClass="form-control"/>
                  <input type="text" name="network_id" id="network_id" value="${bank.network.id}">

                  <button type="submit" class="btn btn-success btn-primary">Save</button>

                  <div class="form-group">
                    <label for="networks_menu" class="col-sm-4 control-label">ATM network</label>
                    <div class="col-md-8">
                      <div class="btn-group">
                        <a class="btn btn-default dropdown-toggle btn-select" data-toggle="dropdown" href="#">${bank.network.name} <span class="caret"></span></a>
                        <ul class="dropdown-menu" id="networks_menu">
                          <c:forEach items="${networks}" var="net">
                            <li><a href="#" id="${net.id}">${net.name}</a></li>
                          </c:forEach>
                          <li class="divider"></li>
                          <li><a href="#" id="0"><span class="glyphicon glyphicon-question-sign"></span>Unknown</a></li>
                        </ul>
                      </div>
                    </div>

                  </div>

            </div>
          </div>
        </form:form>
    </div>
  </div>
</div>
</div>

<script type='text/javascript'>
  /* On select item in ATM Network dropdown
   *  set dropdown title to name of the ATM Network
   * */
  var network_id = 0;
  $("#networks_menu li a").click(function(){
    var selText = $(this).text();
    $(this).parents('.btn-group').find('.dropdown-toggle').html(selText+' <span class="caret"></span>');
    network_id = $(this).attr("id");
    document.getElementById("network_id").value = network_id;
  });

</script>
