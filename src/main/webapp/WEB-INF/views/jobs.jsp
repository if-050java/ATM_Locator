<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>

</head>
<body>
<div style="height:40px; width:100%;">
    <c:if test="${not empty error}">
        <div class="alert alert-warning alert-dismissible" role="alert">
            <button type="button" class="close" data-dismiss="alert">
                <span aria-hidden="true">&times;</span>
                <span class="sr-only">Close</span></button>

            <div style="text-align: center">
                    ${error}
            </div>
        </div>
    </c:if>
</div>
<div class="panel panel-primary">

    <div class="panel-heading">
    <c:if test="${not empty edit}">
      <h3 class="panel-title">Edit job</h3>
    </c:if>
    <c:if test="${not empty add}">
       <h3 class="panel-title">Add new job</h3>
    </c:if>
    </div>
    <div class="panel-body">
        <form class="form-horizontal" method="post" action="<c:url value="/admin/save"/>" >
            <div class="row">
                <div class="form-group">
                    <label for="jobName" class="col-sm-2 control-label">Job Name</label>
                    <div class="col-md-4">
                        <input type="text" class="form-control"
                               name="jobName" id="jobName" value="${job.getJobName()}">
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="form-group">
                    <label for="jobGroup" class="col-sm-2 control-label">Job Group</label>
                    <div class="col-md-4">
                        <input type="text" class="form-control"
                               name="jobGroup" id="jobGroup" value="${job.getJobGroup()}">
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="form-group">
                    <label for="triggerName" class="col-sm-2 control-label">Trigger Name</label>
                    <div class="col-md-4">
                        <input type="text" class="form-control"
                               name="triggerName" id="triggerName" value="${job.getTriggerName()}">
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="form-group">
                    <label for="triggerGroup" class="col-sm-2 control-label">Trigger Group</label>
                    <div class="col-md-4">
                        <input type="text" class="form-control"
                               name="triggerGroup" id="triggerGroup" value="${job.getTriggerGroup()}">
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="form-group">
                    <label for="jobClassName" class="col-sm-2 control-label">Job Class</label>
                    <div class="col-md-4">
                        <input type="text" class="form-control"
                               name="jobClassName" id="jobClassName" value="${job.getJobClassName()}">
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="form-group">
                    <label for="cronSched" class="col-sm-2 control-label">Job Cron</label>
                    <div class="col-md-4">
                        <input type="text" class="form-control"
                               name="cronSched" id="cronSched" value="${job.getCronSched()}">
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="form-group">
                    <label for="params" class="col-sm-2 control-label">Parameters</label>
                    <div class="col-md-4">
                        <textarea name="params" id="params"
                         class="form-control" cols="40" rows="5">${job.getParams()}</textarea>
                    </div>
                </div>
            </div>

            <c:if test="${not empty edit}">
                <input type="hidden" name="currentJobName" value="${job.getJobName()}">
            </c:if>

            <div class="row">
                <div class="form-group">
                    <div class="col-sm-offset-2 col-sm-10">
                        <button type="submit" class="btn btn-default">Save</button>
                    </div>
                </div>
            </div>

        </form>
        <div>

        </div>

    </div>
</div>
</body>
</html>
