<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="container">
  <div class="col-md-12" role="main" id="networksList">
    <div class="panel panel-default">
      <!-- Default panel contents -->
      <div class="panel-heading">Edit bank:</div>
    </div>

  </div>
  <div class="col-md-12" role="main" id="userData">
    <div class="panel panel-default">
      <div class="panel-heading">
        <h2 class="panel-title">
          Edit bank:
        </h2>
      </div>
      <div class="panel-body">
        <form:form method="post" action="${updateURL}" modelAttribute="bank" cssClass="form-horizontal"
                   role="form" enctype="multipart/form-data">
          <div class="row">
            <div class="col-md-2">
              <img src="/resources/images/${bank.logo}" class="img-responsive" alt="Responsive image"
                   id="bankLogo">

              <div class="controls clearfix">
                        <span class="btn btn-success btn-file">
                            <i class="glyphicon glyphicon-camera"></i> <span>Change bank logo</span>
                            <input type="file" name="image" id="image"/>
                        </span>
              </div>
            </div>
            <div class="col-md-6">

              <form:hidden path="id"/>

              <div class="form-group">
                <label for="name" class="col-sm-2 control-label">Bank Name</label>

                <div class="col-md-10">
                  <form:input path="name" placeholder="name" cssClass="form-control"/>
                </div>
              </div>

              <div class="form-group">
                <label for="webSite" class="col-sm-2 control-label">Bank Website URL</label>

                <div class="col-md-10">
                  <form:input path="webSite" placeholder="webSite" cssClass="form-control"/>
                </div>
              </div>

              <div class="form-group last">
                <button type="submit" class="btn btn-success">Save</button>
              </div>

            </div>
          </div>
        </form:form>
      </div>
    </div>
  </div>
</div>
